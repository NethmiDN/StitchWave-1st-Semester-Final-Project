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
import lk.ijse.stitchwave1stsemesterfinalproject.dto.*;
import lk.ijse.stitchwave1stsemesterfinalproject.dto.tm.CustomerTM;
import lk.ijse.stitchwave1stsemesterfinalproject.dto.tm.OrdersTM;
import lk.ijse.stitchwave1stsemesterfinalproject.dto.tm.PaymentTM;
import lk.ijse.stitchwave1stsemesterfinalproject.model.CustomerModel;
import lk.ijse.stitchwave1stsemesterfinalproject.model.OrdersModel;
import lk.ijse.stitchwave1stsemesterfinalproject.model.PaymentModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class OrdersFormController implements Initializable {

    @FXML
    private ComboBox<String> cmbcusid;

    @FXML
    private ComboBox<String> cmbpayid;

    @FXML
    private TableColumn<CustomerTM, String> cusidclmn;

    @FXML
    private Label cusidlbl;

    @FXML
    private Label cusnamelbl;

    @FXML
    private TableColumn<OrdersTM, LocalDate> dateclmn;

    @FXML
    private Label datel;

    @FXML
    private Label datelbl;

    @FXML
    private Button dltbtn;

    @FXML
    private ImageView iconimg;

    @FXML
    private Label idlbl;

    @FXML
    private Label lb;

    @FXML
    private TableColumn<OrdersTM, String> orderidclmn;

    @FXML
    private Label orderidlbl;

    @FXML
    private AnchorPane ordersap;

    @FXML
    private TableView<OrdersTM> orderstable;

    @FXML
    private TableColumn<PaymentTM, String> paymentidclmn;

    @FXML
    private Label paymentidlbl;

    @FXML
    private TableColumn<OrdersTM, Integer> qtyclmn;

    @FXML
    private Label qtylbl;

    @FXML
    private TextField qtytxt;

    @FXML
    private Button resetbtn;

    @FXML
    private Button savebtn;

    @FXML
    private Button updatebtn;

    OrdersModel ordersModel = new OrdersModel();

    CustomerModel customerModel = new CustomerModel();

    PaymentModel paymentModel = new PaymentModel();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderidclmn.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        dateclmn.setCellValueFactory(new PropertyValueFactory<>("date"));
        qtyclmn.setCellValueFactory(new PropertyValueFactory<>("qty"));
        cusidclmn.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        paymentidclmn.setCellValueFactory(new PropertyValueFactory<>("payment_id"));

        try {
            loadCustomerIds();
            loadPaymentIds();
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();

        String nextOrderID = ordersModel.getNextOrderId();
        idlbl.setText(nextOrderID);

        datelbl.setText(LocalDate.now().toString());
        qtytxt.setText("");

        savebtn.setDisable(false);
        dltbtn.setDisable(true);
        updatebtn.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<OrdersDTO> ordersDTOS = ordersModel.getAllOrders();
        ObservableList<OrdersTM> ordersTMS = FXCollections.observableArrayList();

        for (OrdersDTO ordersDTO : ordersDTOS) {
            OrdersTM ordersTM = new OrdersTM(
                    ordersDTO.getOrder_id(),
                    ordersDTO.getDate(),
                    ordersDTO.getQty(),
                    ordersDTO.getCustomer_id(),
                    ordersDTO.getPayment_id()
            );
            ordersTMS.add(ordersTM);
        }
        orderstable.setItems(ordersTMS);
    }

    @FXML
    void dltbtnOnAction(ActionEvent event) throws SQLException {
        String order_id = idlbl.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Order?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = ordersModel.deleteOrder(order_id);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Order deleted...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Order...!").show();
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        OrdersTM selectedItem = orderstable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            idlbl.setText(selectedItem.getOrder_id());
            datelbl.setText(String.valueOf(selectedItem.getDate()));
            qtytxt.setText(String.valueOf(selectedItem.getQty()));
            cmbcusid.setValue(selectedItem.getCustomer_id());
            cmbpayid.setValue(selectedItem.getPayment_id());

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
        String order_id = idlbl.getText();
        Integer qty = Integer.valueOf(qtytxt.getText());
        LocalDate date = LocalDate.parse(datelbl.getText());
        String customer_id = String.valueOf(cmbcusid.getValue());
        String payment_id = String.valueOf(cmbpayid.getValue());

        // Define regex patterns for validation
        String quantityPattern = "^([1-9]\\d{0,4}|0)$";

//        (1)
//        Pattern compile = Pattern.compile(namePattern);
//        boolean isValidName = compile.matcher(name).matches();

//        (2)
//        Validate each field using regex patterns
        boolean isValidQty = String.valueOf(qty).matches(String.valueOf(quantityPattern));

        // Reset input field styles
        qtytxt.setStyle(qtytxt.getStyle() + ";-fx-border-color:  #091057;");

        // Highlight invalid fields in red

        if (!isValidQty) {
            qtytxt.setStyle(qtytxt.getStyle() + ";-fx-border-color: red;");
        }

        // Save customer if all fields are valid
        if (isValidQty) {
            OrdersDTO ordersDTO = new OrdersDTO(order_id, date, qty, customer_id, payment_id);

            boolean isSaved = ordersModel.saveOrder(ordersDTO);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Order saved...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save order...!").show();
            }
        }
    }

    @FXML
    void updatebtnOnAction(ActionEvent event) throws SQLException {
        String order_id = idlbl.getText();
        LocalDate date = LocalDate.parse(datelbl.getText());
        Integer qty = Integer.valueOf(qtytxt.getText());
        String customer_id = String.valueOf(cmbcusid.getValue());
        String payment_id = String.valueOf(cmbpayid.getValue());

        String quantityPattern = "^([1-9]\\d{0,4}|0)$";

//        Pattern compile = Pattern.compile(namePattern);
//        System.out.println(compile.matcher(name).matches());
        boolean isValidQty = String.valueOf(qty).matches(String.valueOf(quantityPattern));


        qtytxt.setStyle(qtytxt.getStyle() + ";-fx-border-color:  #091057;");

        if (!isValidQty) {
            qtytxt.setStyle(qtytxt.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidQty) {

            OrdersDTO ordersDTO = new OrdersDTO(order_id, date, qty, customer_id, payment_id);

            boolean isUpdate = ordersModel.updateOrder(ordersDTO);

            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "Order updated...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update order...!").show();
            }
        }
    }

    private void loadCustomerIds() throws SQLException {
        ArrayList<String> customerIds = customerModel.getAllCustomerIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(customerIds);
        cmbcusid.setItems(observableList);
    }

    @FXML
    void cmbcusidOnAction(ActionEvent event) throws SQLException {
        String selectedCusId = cmbcusid.getSelectionModel().getSelectedItem();
        if (selectedCusId != null) {
            CustomerDTO customerDTO = customerModel.findById(selectedCusId);
            if (customerDTO != null) {
                cusnamelbl.setText(customerDTO.getName());
            }
        }
    }


    private void loadPaymentIds() throws SQLException {
        ArrayList<String> paymentIds = paymentModel.getAllPaymentIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(paymentIds);
        cmbpayid.setItems(observableList);
    }

    @FXML
    void cmbpayidOnAction(ActionEvent event) throws SQLException {
        String selectedPayId = cmbpayid.getSelectionModel().getSelectedItem();
        if (selectedPayId != null) {
            PaymentDTO paymentDTO = paymentModel.findById(selectedPayId);
        }
    }
}
