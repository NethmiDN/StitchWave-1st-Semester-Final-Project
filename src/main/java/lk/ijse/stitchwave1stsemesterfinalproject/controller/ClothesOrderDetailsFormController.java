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
import lk.ijse.stitchwave1stsemesterfinalproject.dto.tm.*;
import lk.ijse.stitchwave1stsemesterfinalproject.model.*;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class ClothesOrderDetailsFormController implements Initializable {

    @FXML
    private ComboBox<String> cmbcusid;

    @FXML
    private ComboBox<String> cmbpayid;

    @FXML
    private ComboBox<String> cmbstockid;

    @FXML
    private TableColumn<CustomerTM, String> cusidclmn;

    @FXML
    private Label cusidlbl;

    @FXML
    private Label cusnamelbl;

    @FXML
    private TableColumn<ClothesOrderDetailTM, LocalDate> dateclmn;

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
    private TableView<ClothesOrderDetailTM> orderstable;

    @FXML
    private TableColumn<PaymentTM, String> paymentidclmn;

    @FXML
    private Label paymentidlbl;

    @FXML
    private Label qtlb;

    @FXML
    private TableColumn<ClothesOrderDetailTM, Integer> qtyclmn;

    @FXML
    private Label qtylbl;

    @FXML
    private TextField qtytxt;

    @FXML
    private Button resetbtn;

    @FXML
    private Button savebtn;

    @FXML
    private Label stidlbl;

    @FXML
    private TableColumn<SewnClothesStockTM, String> stockidclmn;

    @FXML
    private Label stqtlb;

    @FXML
    private Button updatebtn;

     SewnClothesStockModel stockModel = new SewnClothesStockModel();
     ClothesOrderDetailModel clothesOrderDetailModel = new ClothesOrderDetailModel();
     OrdersModel ordersModel = new OrdersModel();
     CustomerModel customerModel = new CustomerModel();

     PaymentModel paymentModel = new PaymentModel();

    public void initialize(URL location, ResourceBundle resources) {
        orderidclmn.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        stockidclmn.setCellValueFactory(new PropertyValueFactory<>("stock_id"));
        dateclmn.setCellValueFactory(new PropertyValueFactory<>("date"));
        qtyclmn.setCellValueFactory(new PropertyValueFactory<>("qty"));
        cusidclmn.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        paymentidclmn.setCellValueFactory(new PropertyValueFactory<>("payment_id"));

        try {
            refreshPage();
            loadStockIds();
            loadCustomerIds();
            loadPaymentIds();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();

        String nextOrderID = ordersModel.getNextOrderId();
        idlbl.setText(nextOrderID);
        datelbl.setText(LocalDate.now().toString());
        qtytxt.setText("");
        qtlb.setText("");

        cmbstockid.getItems().clear();
        cmbcusid.getItems().clear();
        cmbpayid.getItems().clear();

        loadStockIds();
        loadCustomerIds();
        loadPaymentIds();

        savebtn.setDisable(false);
        dltbtn.setDisable(true);
        updatebtn.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<ClothesOrderDetailTM> clothesOrderDetailDTOS = clothesOrderDetailModel.getAllDetails();
        ObservableList<ClothesOrderDetailTM> clothesOrderDetailTMS = FXCollections.observableArrayList();


        for (ClothesOrderDetailTM clothesOrderDetailDTO : clothesOrderDetailDTOS) {
            ClothesOrderDetailTM clothesOrderDetailTM = new ClothesOrderDetailTM(
                    clothesOrderDetailDTO.getOrder_id(),
                    clothesOrderDetailDTO.getStock_id(),
                    clothesOrderDetailDTO.getDate(),
                    clothesOrderDetailDTO.getQty(),
                    clothesOrderDetailDTO.getCustomer_id(),
                    clothesOrderDetailDTO.getPayment_id()
            );
            clothesOrderDetailTMS.add(clothesOrderDetailTM);
        }
        orderstable.setItems(clothesOrderDetailTMS);
    }

    private void loadStockIds() throws SQLException {
        ArrayList<String> stockId = stockModel.getAllStockIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(stockId);
        cmbstockid.setItems(observableList);
    }

    private void loadCustomerIds() throws SQLException {
        ArrayList<String> customerIds = customerModel.getAllCustomerIds();
        ObservableList<String> observableList = FXCollections.observableArrayList(customerIds);
        cmbcusid.setItems(observableList);
    }

    private void loadPaymentIds() throws SQLException {
        ArrayList<String> paymentIds = paymentModel.getAllPaymentIds();
        ObservableList<String> observableList = FXCollections.observableArrayList(paymentIds);
        cmbpayid.setItems(observableList);
    }

    @FXML
    void cmbcusidOnAction(ActionEvent event) throws SQLException {
        String selectedCusId = String.valueOf(cmbcusid.getSelectionModel().getSelectedItem());
        if (selectedCusId != null) {
            CustomerDTO customerDTO = customerModel.findById(selectedCusId);
            if (customerDTO != null) {
                cusnamelbl.setText(String.valueOf(customerDTO.getName()));
            }
        }
    }

    @FXML
    void cmbpayidOnAction(ActionEvent event) throws SQLException {
        String selectedPayId = String.valueOf(cmbpayid.getSelectionModel().getSelectedItem());
        if (selectedPayId != null) {
            PaymentDTO paymentDTO = paymentModel.findById(selectedPayId);
        }
    }

    @FXML
    void cmbstockidOnAction(ActionEvent event) throws SQLException {
        String selectedStockId = cmbstockid.getSelectionModel().getSelectedItem();
        if (selectedStockId != null) {
            SewnClothesStockDTO sewnClothesStockDTO = stockModel.findById(selectedStockId);
            if (sewnClothesStockDTO != null) {
                qtlb.setText(String.valueOf(sewnClothesStockDTO.getQty()));
            }
        }
    }

    @FXML
    void dltbtnOnAction(ActionEvent event) throws SQLException {
        String order_id = idlbl.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Clothes Order?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = clothesOrderDetailModel.deleteOrder(order_id);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Clothes Order deleted...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete clothes order...!").show();
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent event) throws SQLException {
        ClothesOrderDetailTM selectedItem = orderstable.getSelectionModel().getSelectedItem();

        ArrayList<ClothesOrderDetailTM> selectedItemDetails = clothesOrderDetailModel.getAllDetails();

        if (selectedItem != null) {
            for (int i = 0; i < selectedItemDetails.size(); i++) {
                if (selectedItemDetails.get(i).getOrder_id().equals(selectedItem.getOrder_id())) {
                    idlbl.setText(selectedItem.getOrder_id());
                    datelbl.setText(String.valueOf(selectedItem.getDate()));
                    qtytxt.setText(String.valueOf(selectedItemDetails.get(i).getQty()));
                    cmbstockid.setValue(selectedItem.getStock_id());
                    cmbcusid.setValue(selectedItem.getCustomer_id());
                    cmbpayid.setValue(selectedItem.getPayment_id());
                    cusnamelbl.setText(customerModel.findById(selectedItem.getCustomer_id()).getName());
                    qtlb.setText(String.valueOf(stockModel.findById(selectedItem.getStock_id()).getQty()));
                }
            }

            savebtn.setDisable(true);
            dltbtn.setDisable(false);
            updatebtn.setDisable(false);
        }
    }

    @FXML
    void resetbtnOnAction(ActionEvent event) throws SQLException {
        cmbstockid.setValue(null);
        cmbstockid.setPromptText("Select stock Id");

        cmbcusid.setValue(null);
        cmbcusid.setPromptText("Select customer Id");

        cmbpayid.setValue(null);
        cmbpayid.setPromptText("Select payment Id");

        qtlb.setText("");
        qtytxt.setText("");
        cusnamelbl.setText("");
        refreshPage();
    }

    @FXML
    void savebtnOnAction(ActionEvent event) throws SQLException {
        String order_id = idlbl.getText();
        String stock_id = cmbstockid.getValue();
        int qtylblText = Integer.parseInt(qtlb.getText());
        LocalDate dateNow = LocalDate.parse(datelbl.getText());
        String customerId = cmbcusid.getValue();
        String paymentId = cmbpayid.getValue();
        int qtyTyped = Integer.parseInt(qtytxt.getText());

        if (cusnamelbl.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select name").show();
        } else {
            if (qtylbl.getText().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Please select stock id").show();
            } else {
                boolean isSaved = clothesOrderDetailModel.saveOrderWithStockReduction(new ClothesOrderDetailTM(
                        order_id,
                        stock_id,
                        dateNow,
                        qtyTyped,
                        customerId,
                        paymentId
                ), (qtyTyped));
                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Clothes Order saved...!").show();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Fail to save clothes order...!").show();
                }
            }
        }
    }

    @FXML
    void updatebtnOnAction(ActionEvent event) throws SQLException {

    }
}