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

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

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
}
