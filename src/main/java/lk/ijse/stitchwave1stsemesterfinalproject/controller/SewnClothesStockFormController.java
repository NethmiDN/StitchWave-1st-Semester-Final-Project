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
import lk.ijse.stitchwave1stsemesterfinalproject.dto.SewnClothesStockDTO;
import lk.ijse.stitchwave1stsemesterfinalproject.dto.tm.FabricTM;
import lk.ijse.stitchwave1stsemesterfinalproject.dto.tm.SewnClothesStockTM;
import lk.ijse.stitchwave1stsemesterfinalproject.model.FabricModel;
import lk.ijse.stitchwave1stsemesterfinalproject.model.SewnClothesStockModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class SewnClothesStockFormController implements Initializable {

    @FXML
    private ComboBox<String> cmbfabid;

    @FXML
    private Button dltbtn;

    @FXML
    private TableColumn<FabricTM, String> fabidclmn;

    @FXML
    private Label fabidlbl;

    @FXML
    private ImageView iconimg;

    @FXML
    private TableColumn<SewnClothesStockTM, String> idclmn;

    @FXML
    private Label lb;

    @FXML
    private Label lblid;

    @FXML
    private TableColumn<SewnClothesStockTM, Integer> qtyclmn;

    @FXML
    private Label qtylbl;

    @FXML
    private TextField qtytxt;

    @FXML
    private TextField fabidtxt;

    @FXML
    private Button resetbtn;

    @FXML
    private Button savebtn;

    @FXML
    private AnchorPane sewnclothesstockap;

    @FXML
    private TableView<SewnClothesStockTM> sewnclothesstocktable;

    @FXML
    private Label stockidlbl;

    @FXML
    private Button updatebtn;

    FabricModel fabricModel = new FabricModel();
    SewnClothesStockModel sewnClothesStockModel = new SewnClothesStockModel();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        idclmn.setCellValueFactory(new PropertyValueFactory<>("stock_id"));
        qtyclmn.setCellValueFactory(new PropertyValueFactory<>("qty"));
        fabidclmn.setCellValueFactory(new PropertyValueFactory<>("fabric_id"));

        try {
            loadFabricIds();
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();

        String nextStockID = sewnClothesStockModel.getNextStockId();
        lblid.setText(nextStockID);

        qtytxt.setText("");

        savebtn.setDisable(false);
        dltbtn.setDisable(true);
        updatebtn.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<SewnClothesStockDTO> sewnClothesStockDTOS = sewnClothesStockModel.getAllStocks();
        ObservableList<SewnClothesStockTM> sewnClothesStockTMS = FXCollections.observableArrayList();

        for (SewnClothesStockDTO sewnClothesStockDTO : sewnClothesStockDTOS) {
            SewnClothesStockTM sewnClothesStockTM = new SewnClothesStockTM(
                    sewnClothesStockDTO.getStock_id(),
                    sewnClothesStockDTO.getQty(),
                    sewnClothesStockDTO.getFabric_id()
            );
            sewnClothesStockTMS.add(sewnClothesStockTM);
        }
        sewnclothesstocktable.setItems(sewnClothesStockTMS);
    }

    @FXML
    void dltbtnOnAction(ActionEvent event) throws SQLException {
        String stock_id = lblid.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Stock?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = sewnClothesStockModel.deleteStock(stock_id);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Stock deleted...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Stock...!").show();
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        SewnClothesStockTM selectedItem = sewnclothesstocktable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblid.setText(selectedItem.getStock_id());
            qtytxt.setText(String.valueOf(selectedItem.getQty()));

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
        String stock_id = lblid.getText();
        Integer qty = Integer.valueOf(qtytxt.getText());
        String fabric_id = cmbfabid.getValue();

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
        //fabidtxt.setStyle(fabidtxt.getStyle() + ";-fx-border-color:  #091057;");

        // Highlight invalid fields in red

        if (!isValidQty) {
            qtytxt.setStyle(qtytxt.getStyle() + ";-fx-border-color: red;");
        }

        // Save customer if all fields are valid
        if (isValidQty) {
            SewnClothesStockDTO sewnClothesStockDTO = new SewnClothesStockDTO(stock_id, qty, fabric_id);

            boolean isSaved = sewnClothesStockModel.saveStock(sewnClothesStockDTO);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Stock saved...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save stock...!").show();
            }
        }
    }

    @FXML
    void updatebtnOnAction(ActionEvent event) throws SQLException {
        String stock_id = lblid.getText();
        Integer qty = Integer.valueOf(qtytxt.getText());
        String fabric_id = cmbfabid.getValue();

        String quantityPattern = "^([1-9]\\d{0,4}|0)$";

//        Pattern compile = Pattern.compile(namePattern);
//        System.out.println(compile.matcher(name).matches());
        boolean isValidQty = String.valueOf(qty).matches(String.valueOf(quantityPattern));


        qtytxt.setStyle(qtytxt.getStyle() + ";-fx-border-color:  #091057;");
        //fabidtxt.setStyle(fabidtxt.getStyle() + ";-fx-border-color:  #091057;");

        if (!isValidQty) {
            qtytxt.setStyle(qtytxt.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidQty) {

            SewnClothesStockDTO sewnClothesStockDTO = new SewnClothesStockDTO(stock_id, qty, fabric_id);

            boolean isUpdate = sewnClothesStockModel.updateStock(sewnClothesStockDTO);

            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "Stock updated...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update stock...!").show();
            }
        }
    }

    private void loadFabricIds() throws SQLException {
        ArrayList<String> fabricIds = fabricModel.getAllFabricIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(fabricIds);
        cmbfabid.setItems(observableList);
    }

    @FXML
    void cmbfabidOnAction(ActionEvent event) throws SQLException {
        String selectedFabId = String.valueOf(cmbfabid.getSelectionModel().getSelectedItem());
        if (selectedFabId != null) {
            FabricDTO fabricDTO = fabricModel.findById(selectedFabId);

        }
    }
}