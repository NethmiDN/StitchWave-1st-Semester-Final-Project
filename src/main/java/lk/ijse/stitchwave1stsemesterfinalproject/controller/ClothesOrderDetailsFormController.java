package lk.ijse.stitchwave1stsemesterfinalproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.stitchwave1stsemesterfinalproject.dto.SupplierOrderDTO;
import lk.ijse.stitchwave1stsemesterfinalproject.dto.tm.ClothesOrderDetailTM;
import lk.ijse.stitchwave1stsemesterfinalproject.dto.tm.OrdersTM;
import lk.ijse.stitchwave1stsemesterfinalproject.dto.tm.SewnClothesStockTM;
import lk.ijse.stitchwave1stsemesterfinalproject.dto.tm.SupplierOrderTM;
import lk.ijse.stitchwave1stsemesterfinalproject.model.OrdersModel;
import lk.ijse.stitchwave1stsemesterfinalproject.model.SewnClothesStockModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ClothesOrderDetailsFormController{

    @FXML
    private AnchorPane clothesorderdetailap;

    @FXML
    private ComboBox<OrdersTM> cmborderid;

    @FXML
    private ComboBox<SewnClothesStockTM> cmbstockid;

    @FXML
    private Button dltbtn;

    @FXML
    private ImageView iconimg;

    @FXML
    private Label lb;

    @FXML
    private TableColumn<OrdersTM, String> orderidclmn;

    @FXML
    private Label orderidlbl;

    @FXML
    private TableColumn<OrdersTM, Integer> orderqtyclmn;

    @FXML
    private Label orderqtylbl;

    @FXML
    private Button resetbtn;

    @FXML
    private Button savebtn;

    @FXML
    private TableColumn<SewnClothesStockTM, String> stockidclmn;

    @FXML
    private Label stockidlbl;

    @FXML
    private TableColumn<SewnClothesStockTM, Integer> stockqtyclmn;

    @FXML
    private Label stockqtylbl;

    @FXML
    private TableView<ClothesOrderDetailTM> clothesordertable;

    @FXML
    private Button updatebtn;

    SewnClothesStockModel sewnClothesStockModel = new SewnClothesStockModel();

    OrdersModel ordersModel = new OrdersModel();

   /* public void initialize(URL url, ResourceBundle resourceBundle) {
        stockidclmn.setCellValueFactory(new PropertyValueFactory<>("Stock_Id"));
        stockqtyclmn.setCellValueFactory(new PropertyValueFactory<>("Stock_Qty"));
        orderidclmn.setCellValueFactory(new PropertyValueFactory<>("Order_Id"));
        orderqtyclmn.setCellValueFactory(new PropertyValueFactory<>("Order_Qty"));

        try {
            loadStockIds();
            loadOrderIds();
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();

        String nextSupplierOrderID = supplierOrderModel.getNextSupplierOrderId();
        lblid.setText(nextSupplierOrderID);

        qtytxt.setText("");
        suporderdate.setText(LocalDate.now().toString());

        savebtn.setDisable(false);
        dltbtn.setDisable(true);
        updatebtn.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<SupplierOrderDTO> supplierOrderDTOS = supplierOrderModel.getAllSupplierOrders();
        ObservableList<SupplierOrderTM> supplierOrderTMS = FXCollections.observableArrayList();

        for (SupplierOrderDTO supplierOrderDTO : supplierOrderDTOS) {
            SupplierOrderTM supplierOrderTM = new SupplierOrderTM(
                    supplierOrderDTO.getOrder_id(),
                    supplierOrderDTO.getQty_kg(),
                    supplierOrderDTO.getDate(),
                    supplierOrderDTO.getSupplier_id()
            );
            supplierOrderTMS.add(supplierOrderTM);
        }
        supplierordertable.setItems(supplierOrderTMS);
    }
*/
    @FXML
    void cmborderidOnAction(ActionEvent event) {

    }

    @FXML
    void cmbstockidOnAction(ActionEvent event) {

    }

    @FXML
    void dltbtnOnAction(ActionEvent event) {

    }

    @FXML
    void onClickTable(MouseEvent event) {

    }

    @FXML
    void resetbtnOnAction(ActionEvent event) {

    }

    @FXML
    void savebtnOnAction(ActionEvent event) {

    }

    @FXML
    void updatebtnOnAction(ActionEvent event) {

    }
}
