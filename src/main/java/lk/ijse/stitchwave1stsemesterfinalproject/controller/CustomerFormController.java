package lk.ijse.stitchwave1stsemesterfinalproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.stitchwave1stsemesterfinalproject.dto.CustomerDTO;
import lk.ijse.stitchwave1stsemesterfinalproject.dto.tm.CustomerTM;
import lk.ijse.stitchwave1stsemesterfinalproject.model.CustomerModel;
import lk.ijse.stitchwave1stsemesterfinalproject.db.DBConnection;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class CustomerFormController implements Initializable {

    @FXML
    private TableColumn<CustomerTM, String> contactclmn;

    @FXML
    private Label contactlbl;

    @FXML
    private TextField contacttxt;

    @FXML
    private Label cusidlbl;

    @FXML
    private Button reportbtn;

    @FXML
    private TableView<CustomerTM> custable;

    @FXML
    private AnchorPane customerap;

    @FXML
    private Button dltbtn;

    @FXML
    private ImageView iconimg;

    @FXML
    private TableColumn<CustomerTM, String> idclmn;

    @FXML
    private Label lblid;

    @FXML
    private TableColumn<CustomerTM, String> nameclmn;

    @FXML
    private Label namelbl;

    @FXML
    private TextField nametxt;

    @FXML
    private Button resetbtn;

    @FXML
    private Button savebtn;

    @FXML
    private Button updatebtn;

    @FXML
    private Label lb;

    CustomerModel customerModel = new CustomerModel();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        idclmn.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        nameclmn.setCellValueFactory(new PropertyValueFactory<>("name"));
        contactclmn.setCellValueFactory(new PropertyValueFactory<>("contact"));

        try {
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();

        String nextCustomerID = customerModel.getNextCustomerId();
        lblid.setText(nextCustomerID);

        nametxt.setText("");
        contacttxt.setText("");

        savebtn.setDisable(false);

        dltbtn.setDisable(true);
        updatebtn.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<CustomerDTO> customerDTOS = customerModel.getAllCustomers();
        ObservableList<CustomerTM> customerTMS = FXCollections.observableArrayList();

        for (CustomerDTO customerDTO : customerDTOS) {
            CustomerTM customerTM = new CustomerTM(
                    customerDTO.getCustomer_id(),
                    customerDTO.getName(),
                    customerDTO.getContact()
            );
            customerTMS.add(customerTM);
        }
        custable.setItems(customerTMS);
    }

    @FXML
    void dltbtnOnAction(ActionEvent event) throws SQLException {
        String customer_id = lblid.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this customer?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = customerModel.deleteCustomer(customer_id);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Customer deleted...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete customer...!").show();
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        CustomerTM selectedItem = custable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblid.setText(selectedItem.getCustomer_id());
            nametxt.setText(selectedItem.getName());
            contacttxt.setText(selectedItem.getContact());

            savebtn.setDisable(true);

            dltbtn.setDisable(false);
            updatebtn.setDisable(false);
        }
    }

    @FXML
    void resetbtnOnAction(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void savebtnOnAction(ActionEvent event) throws SQLException {
        String customer_id = lblid.getText();
        String name = nametxt.getText();
        String contact = contacttxt.getText();

        // Define regex patterns for validation
        String namePattern = "^[A-Za-z ]+$";
        String contactPattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

//        Validate each field using regex patterns
        boolean isValidName = name.matches(namePattern);
        boolean isValidContact = contact.matches(contactPattern);

        // Reset input field styles
        nametxt.setStyle(nametxt.getStyle() + ";-fx-border-color:  #091057;");
        contacttxt.setStyle(contacttxt.getStyle() + ";-fx-border-color:  #091057;");

        // Highlight invalid fields in red

        if (!isValidName) {
            nametxt.setStyle(nametxt.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidContact) {
            contacttxt.setStyle(contacttxt.getStyle() + ";-fx-border-color: red;");
        }

        // Save customer if all fields are valid
        if (isValidName && isValidContact) {
            CustomerDTO customerDTO = new CustomerDTO(customer_id, name, contact);

            boolean isSaved = customerModel.saveCustomer(customerDTO);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Customer saved...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save customer...!").show();
            }
        }
    }

    @FXML
    void updatebtnOnAction(ActionEvent event) throws SQLException {
        String customer_id = lblid.getText();
        String name = nametxt.getText();
        String contact = contacttxt.getText();

        String namePattern = "^[A-Za-z ]+$";
        String contactPattern = "^(\\d+)||((\\d+\\.)(\\d){2})$";

        boolean isValidName = name.matches(namePattern);
        boolean isValidContact = contact.matches(contactPattern);

        nametxt.setStyle(nametxt.getStyle() + ";-fx-border-color:  #091057;");
        contacttxt.setStyle(contacttxt.getStyle() + ";-fx-border-color:  #091057;");

        if (!isValidName) {
            nametxt.setStyle(nametxt.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidContact) {
            contacttxt.setStyle(contacttxt.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidName && isValidContact) {

            CustomerDTO customerDTO = new CustomerDTO(customer_id, name, contact);

            boolean isUpdate = customerModel.updateCustomer(customerDTO);

            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "Customer updated...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update customer...!").show();
            }
        }
    }


    @FXML
    void reportbtnOnAction(ActionEvent event) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();

//            Map<String, Object> parameters = new HashMap<>();
//            today - 2024 - 02 - 02
//            TODAY -

//            parameters.put("today",LocalDate.now().toString());
//            <key , value>
//            Initialize a map to hold the report parameters
//            These parameters can be used inside the report (like displaying today's date)

            // Initialize a map to hold the report parameters
            // These parameters can be used inside the report (like displaying today's date)
            Map<String, Object> parameters = new HashMap<>();

            // Put the current date into the map with two different keys ("today" and "TODAY")
            // You can refer to these keys in the Jasper report if needed
            parameters.put("today", LocalDate.now().toString());
            parameters.put("TODAY", LocalDate.now().toString());

            // Compile the Jasper report from a JRXML file (report template)
            // The report template is located in the "resources/report" folder of the project
            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/Customer.jrxml"));

            // Fill the report with the compiled report object, parameters, and a database connection
            // This prepares the report with real data from the database
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    connection
            );

            // Display the report in a viewer (this is a built-in JasperReports viewer)
            // 'false' indicates that the window should not close the entire application when closed
            JasperViewer.viewReport(jasperPrint, false);
        } catch (JRException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load report..!");
            e.printStackTrace();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Data empty..!");
            e.printStackTrace();
        }
    }
}
