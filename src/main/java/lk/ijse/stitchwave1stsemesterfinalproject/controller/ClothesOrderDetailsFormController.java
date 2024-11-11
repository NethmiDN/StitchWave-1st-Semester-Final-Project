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
import lk.ijse.stitchwave1stsemesterfinalproject.dto.ClothesOrderDetailDTO;
import lk.ijse.stitchwave1stsemesterfinalproject.dto.OrdersDTO;
import lk.ijse.stitchwave1stsemesterfinalproject.dto.SewnClothesStockDTO;
import lk.ijse.stitchwave1stsemesterfinalproject.dto.tm.ClothesOrderDetailTM;
import lk.ijse.stitchwave1stsemesterfinalproject.model.ClothesOrderDetailModel;
import lk.ijse.stitchwave1stsemesterfinalproject.model.OrdersModel;
import lk.ijse.stitchwave1stsemesterfinalproject.model.SewnClothesStockModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ClothesOrderDetailsFormController implements Initializable {

    @FXML
    private AnchorPane clothesorderdetailap;

    @FXML
    private ComboBox<String> cmborderid;

    @FXML
    private ComboBox<String> cmbstockid;

    @FXML
    private Button dltbtn;

    @FXML
    private ImageView iconimg;

    @FXML
    private Label lb;

    @FXML
    private TableColumn<ClothesOrderDetailTM, String> orderidclmn;

    @FXML
    private Label orderidlbl;

    @FXML
    private TableColumn<ClothesOrderDetailTM, Integer> orderqtyclmn;

    @FXML
    private Label orderqtylbl;

    @FXML
    private Button resetbtn;

    @FXML
    private Button savebtn;

    @FXML
    private TableColumn<ClothesOrderDetailTM, String> stockidclmn;

    @FXML
    private Label stockidlbl;

    @FXML
    private TableColumn<ClothesOrderDetailTM, Integer> stockqtyclmn;

    @FXML
    private Label stockqtylbl;

    @FXML
    private TableView<ClothesOrderDetailTM> clothesordertable;

    @FXML
    private Button updatebtn;

    private SewnClothesStockModel stockModel = new SewnClothesStockModel();
    private ClothesOrderDetailModel clothesOrderDetailModel = new ClothesOrderDetailModel();
    private OrdersModel orderModel = new OrdersModel();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        orderidclmn.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        orderqtyclmn.setCellValueFactory(new PropertyValueFactory<>("order_qty"));
        stockidclmn.setCellValueFactory(new PropertyValueFactory<>("stock_id"));
        stockqtyclmn.setCellValueFactory(new PropertyValueFactory<>("stock_qty"));

        try {
            loadStockIds();
            loadOrderIds();
            refreshPage();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();

        cmborderid.getItems().clear();
        cmbstockid.getItems().clear();

        loadStockIds();
        loadOrderIds();

        savebtn.setDisable(false);
        dltbtn.setDisable(true);
        updatebtn.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<ClothesOrderDetailDTO> clothesOrderDetailDTOS = clothesOrderDetailModel.getAllOrders();
        ObservableList<ClothesOrderDetailTM> clothesOrderDetailTMS = FXCollections.observableArrayList();

        for (ClothesOrderDetailDTO clothesOrderDetailDTO : clothesOrderDetailDTOS) {
            ClothesOrderDetailTM clothesOrderDetailTM = new ClothesOrderDetailTM(
                    clothesOrderDetailDTO.getOrder_id(),
                    orderModel.findById(clothesOrderDetailDTO.getOrder_id()).getQty(),
                    clothesOrderDetailDTO.getStock_id(),
                    stockModel.findById(clothesOrderDetailDTO.getStock_id()).getQty()
            );
            clothesOrderDetailTMS.add(clothesOrderDetailTM);
        }
        clothesordertable.setItems(clothesOrderDetailTMS);
    }

    private void loadStockIds() throws SQLException {
        ArrayList<String> stockIds = stockModel.getAllStockIds();
        ObservableList<String> observableList = FXCollections.observableArrayList(stockIds);
        cmbstockid.setItems(observableList);
    }

    private void loadOrderIds() throws SQLException {
        ArrayList<String> orderIDs = orderModel.getAllOrderIds();
        ObservableList<String> observableList = FXCollections.observableArrayList(orderIDs);
        cmborderid.setItems(observableList);
    }

    @FXML
    void cmborderidOnAction(ActionEvent event) throws SQLException {
        String selectedOrderId = cmborderid.getSelectionModel().getSelectedItem();
        if (selectedOrderId != null) {
            OrdersDTO ordersDTO = orderModel.findById(selectedOrderId);
            if (ordersDTO != null) {
                orderqtylbl.setText(String.valueOf(ordersDTO.getQty()));
            }
        }
    }

    @FXML
    void cmbstockidOnAction(ActionEvent event) throws SQLException {
        String selectedStockId = cmbstockid.getSelectionModel().getSelectedItem();
        if (selectedStockId != null) {
            SewnClothesStockDTO sewnClothesStockDTO = stockModel.findById(selectedStockId);
            if (sewnClothesStockDTO != null) {
                stockqtylbl.setText(String.valueOf(sewnClothesStockDTO.getQty()));
            }
        }
    }

    @FXML
    void dltbtnOnAction(ActionEvent event) {}

    @FXML
    void onClickTable(MouseEvent event) {

    }

    @FXML
    void resetbtnOnAction(ActionEvent event) {}

    @FXML
    void savebtnOnAction(ActionEvent event) throws SQLException {
        String order_id = cmborderid.getValue();
        String stock_id = cmbstockid.getValue();
        if (orderqtylbl.getText().isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please select order id").show();
        } else {
            if (stockqtylbl.getText().isEmpty()) {
                new Alert(Alert.AlertType.ERROR, "Please select stock id").show();
            } else {
                boolean isSaved = clothesOrderDetailModel.saveOrderWithStockReduction(new ClothesOrderDetailDTO(stock_id, order_id), Integer.parseInt(orderqtylbl.getText()));

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
    void updatebtnOnAction(ActionEvent event) {}
}