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
import lk.ijse.stitchwave1stsemesterfinalproject.dto.SupplierOrderDTO;
import lk.ijse.stitchwave1stsemesterfinalproject.dto.tm.SupplierOrderTM;
import lk.ijse.stitchwave1stsemesterfinalproject.dto.tm.SupplierTM;
import lk.ijse.stitchwave1stsemesterfinalproject.model.SupplierOrderModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class SupplierOrderFormController implements Initializable {

    @FXML
    private TableColumn<SupplierOrderTM, LocalDate> dateclmn;

    @FXML
    private Label datelbl;

    @FXML
    private Button dltbtn;

    @FXML
    private Label supnamelbl;

    @FXML
    private ImageView iconimg;

    @FXML
    private TableColumn<SupplierOrderTM, String> idclmn;

    @FXML
    private Label lb;

    @FXML
    private Label lblid;

    @FXML
    private Label orderidlbl;

    @FXML
    private TableColumn<SupplierOrderTM, Integer> qtyclmn;

    @FXML
    private Label qtylbl;

    @FXML
    private TextField qtytxt;

    @FXML
    private Button resetbtn;

    @FXML
    private Button savebtn;

    @FXML
    private TableColumn<SupplierTM, String> supidclmn;

    @FXML
    private Label supidlbl;

    @FXML
    private TextField supidtxt;

    @FXML
    private Label suporderdate;

    @FXML
    private AnchorPane supplierorderap;

    @FXML
    private TableView<SupplierOrderTM> supplierordertable;

    @FXML
    private Button updatebtn;

    SupplierOrderModel supplierOrderModel = new SupplierOrderModel();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        idclmn.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        qtyclmn.setCellValueFactory(new PropertyValueFactory<>("qty_kg"));
        dateclmn.setCellValueFactory(new PropertyValueFactory<>("date"));
        supidclmn.setCellValueFactory(new PropertyValueFactory<>("supplier_id"));

        try {
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
        supidtxt.setText("");

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

    @FXML
    void dltbtnOnAction(ActionEvent event) throws SQLException {
        String order_id = lblid.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Supplier Order?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = supplierOrderModel.deleteSupplierOrder(order_id);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier Order deleted...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Supplier Order...!").show();
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        SupplierOrderTM selectedItem = supplierordertable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblid.setText(selectedItem.getOrder_id());
            qtytxt.setText(String.valueOf(selectedItem.getQty_kg()));
            suporderdate.setText(String.valueOf(selectedItem.getDate()));
            supidtxt.setText(selectedItem.getSupplier_id());

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
        String order_id = lblid.getText();
        Integer qty = Integer.valueOf(qtytxt.getText());
        LocalDate date = LocalDate.parse(suporderdate.getText());
        String supplier_id = supidtxt.getText();

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
        supidtxt.setStyle(supidtxt.getStyle() + ";-fx-border-color:  #091057;");

        // Highlight invalid fields in red

        if (!isValidQty) {
            qtytxt.setStyle(qtytxt.getStyle() + ";-fx-border-color: red;");
        }

        // Save customer if all fields are valid
        if (isValidQty) {
            SupplierOrderDTO supplierOrderDTO = new SupplierOrderDTO(order_id, qty, date, supplier_id);

            boolean isSaved = supplierOrderModel.saveSupplierOrder(supplierOrderDTO);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier Order saved...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save supplier order...!").show();
            }
        }
    }

    @FXML
    void updatebtnOnAction(ActionEvent event) throws SQLException {
        String order_id = lblid.getText();
        Integer qty = Integer.valueOf(qtytxt.getText());
        LocalDate date = LocalDate.parse(suporderdate.getText());
        String supplier_id = supidtxt.getText();

        String quantityPattern = "^([1-9]\\d{0,4}|0)$";

//        Pattern compile = Pattern.compile(namePattern);
//        System.out.println(compile.matcher(name).matches());
        boolean isValidQty = String.valueOf(qty).matches(String.valueOf(quantityPattern));


        qtytxt.setStyle(qtytxt.getStyle() + ";-fx-border-color:  #091057;");
        supidtxt.setStyle(supidtxt.getStyle() + ";-fx-border-color:  #091057;");

        if (!isValidQty) {
            qtytxt.setStyle(qtytxt.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidQty) {

            SupplierOrderDTO supplierOrderDTO = new SupplierOrderDTO(order_id, qty, date, supplier_id);

            boolean isUpdate = supplierOrderModel.updateSupplierOrder(supplierOrderDTO);

            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier Order updated...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update supplier order...!").show();
            }
        }
    }

}