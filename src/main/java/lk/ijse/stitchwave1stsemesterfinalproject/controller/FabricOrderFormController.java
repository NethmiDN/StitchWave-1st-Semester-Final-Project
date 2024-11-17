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
import lk.ijse.stitchwave1stsemesterfinalproject.dto.FabricDTO;
import lk.ijse.stitchwave1stsemesterfinalproject.dto.SupplierOrderDTO;
import lk.ijse.stitchwave1stsemesterfinalproject.dto.tm.FabricTM;
import lk.ijse.stitchwave1stsemesterfinalproject.model.FabricModel;
import lk.ijse.stitchwave1stsemesterfinalproject.model.SupplierModel;
import lk.ijse.stitchwave1stsemesterfinalproject.model.SupplierOrderModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class FabricOrderFormController implements Initializable {

    @FXML
    private TableColumn<FabricTM, String> colorclmn;

    @FXML
    private Label colorlbl;

    @FXML
    private TextField colortxt;

    @FXML
    private Label fabidlbl;

    @FXML
    private AnchorPane fabricap;

    @FXML
    private TableView<FabricTM> fabtable;

    @FXML
    private ImageView iconimg;

    @FXML
    private TableColumn<FabricTM, String> idclmn;

    @FXML
    private Label lblid;

    @FXML
    private ComboBox<String> cmbSupplierId;

    @FXML
    private Button placeOrderBtn;

    @FXML
    private Button resetbtn;

    @FXML
    private Label totalWeightLbl;

    @FXML
    private TableColumn<FabricTM, Double> weightclmn;

    @FXML
    private Label weightlbl;

    @FXML
    private TextField weighttxt;

    @FXML
    private TableColumn<FabricTM, Double> widthclmn;

    @FXML
    private Label widthlbl;

    @FXML
    private TextField widthtxt;

    private FabricModel fabricModel = new FabricModel();
    private SupplierOrderModel supplierOrderModel = new SupplierOrderModel();
    private SupplierModel supplierModel = new SupplierModel();
    private ObservableList<FabricTM> fabricList = FXCollections.observableArrayList();
    private ArrayList<FabricTM> fabricArrayList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idclmn.setCellValueFactory(new PropertyValueFactory<>("fabric_id"));
        colorclmn.setCellValueFactory(new PropertyValueFactory<>("color"));
        weightclmn.setCellValueFactory(new PropertyValueFactory<>("weight_kg"));
        widthclmn.setCellValueFactory(new PropertyValueFactory<>("width_inch"));

        fabtable.setItems(fabricList);

        try {
            String nextFabricID = fabricModel.getNextFabricId();
            lblid.setText(nextFabricID);

            loadSupplierIds();
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadSupplierIds() throws SQLException {
        ArrayList<String> supplierIds = supplierModel.getAllSupplierIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(supplierIds);
        cmbSupplierId.setItems(observableList);
    }

    private void refreshPage() throws SQLException {
        refreshTable();

        colortxt.setText("");
        weighttxt.setText("");
        widthtxt.setText("");

        placeOrderBtn.setDisable(false);
        resetbtn.setDisable(true);
    }

    private void refreshTable() {
        fabricList.clear();
        fabricList.addAll(fabricArrayList);
        calculateTotalWeight();
    }

    private void calculateTotalWeight() {
        double totalWeight = fabricList.stream().mapToDouble(FabricTM::getWeight_kg).sum();
        totalWeightLbl.setText(String.valueOf(totalWeight));
    }

    @FXML
    void addFabricBtnOnAction(ActionEvent event) throws SQLException {
        String fabric_id = lblid.getText();
        String color = colortxt.getText();
        String weightStr = weighttxt.getText();
        String widthStr = widthtxt.getText();

        boolean isValidColor = color.matches("^[A-Za-z ]+$");
        boolean isValidWeight = weightStr.matches("^(\\d+)(\\.\\d+)?$");
        boolean isValidWidth = widthStr.matches("^(\\d+)(\\.\\d+)?$");

        colortxt.setStyle("-fx-border-color: #091057;");
        weighttxt.setStyle("-fx-border-color: #091057;");
        widthtxt.setStyle("-fx-border-color: #091057;");

        if (!isValidColor) colortxt.setStyle("-fx-border-color: red;");
        if (!isValidWeight) weighttxt.setStyle("-fx-border-color: red;");
        if (!isValidWidth) widthtxt.setStyle("-fx-border-color: red;");

        if (isValidColor && isValidWeight && isValidWidth) {
            Double weight = Double.parseDouble(weightStr);
            Double width = Double.parseDouble(widthStr);

            FabricTM fabricTm = new FabricTM(fabric_id, color, weight, width);
            fabricArrayList.add(fabricTm);

            setNextFabricId(lblid.getText());

            refreshTable();
            refreshPage();
        }
    }

    private void setNextFabricId(String lastId) {
        String substring = lastId.substring(1);
        int i = Integer.parseInt(substring);
        int newIdIndex = i + 1;
        lblid.setText(String.format("F%03d", newIdIndex));
    }

    @FXML
    void onClickTable(MouseEvent event) {
        FabricTM selectedFabric = fabtable.getSelectionModel().getSelectedItem();
        if (selectedFabric != null) {
            lblid.setText(selectedFabric.getFabric_id());
            colortxt.setText(selectedFabric.getColor());
            weighttxt.setText(String.valueOf(selectedFabric.getWeight_kg()));
            widthtxt.setText(String.valueOf(selectedFabric.getWidth_inch()));

            placeOrderBtn.setDisable(true);
            resetbtn.setDisable(false);
        }
    }

    @FXML
    void resetbtnOnAction(ActionEvent event) throws SQLException {
        refreshPage();
        if (!fabricArrayList.isEmpty()) {
            setNextFabricId(fabricArrayList.get(fabricArrayList.size() - 1).getFabric_id());
        }
    }

    @FXML
    void cmbSupplierIdOnAction(ActionEvent event) {

    }

    @FXML
    void placeOrderBtnOnAction(ActionEvent event) {
        if (cmbSupplierId.getSelectionModel().getSelectedItem() != null) {
            ArrayList<FabricDTO> fabrics = new ArrayList<>();
            for (FabricTM fabricTm : fabricArrayList) {
                fabrics.add(new FabricDTO(fabricTm.getFabric_id(), fabricTm.getColor(), fabricTm.getWeight_kg(), fabricTm.getWidth_inch()));
            }

            try {
                String orderId = supplierOrderModel.getNextSupplierOrderId();
                SupplierOrderDTO supplierOrderDTO = new SupplierOrderDTO();
                supplierOrderDTO.setOrder_id(orderId);
                supplierOrderDTO.setSupplier_id(cmbSupplierId.getValue());
                supplierOrderDTO.setDate(LocalDate.now());
                supplierOrderDTO.setQty_kg(Double.parseDouble(totalWeightLbl.getText()));

                boolean isOrderSaved = supplierOrderModel.saveOrderWithFabrics(supplierOrderDTO, fabrics);

                if (isOrderSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Order placed successfully!").show();
                    fabricArrayList.clear();
                    refreshTable();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to place order!").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Database error!").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Please select the supplier id").show();
        }
    }
}