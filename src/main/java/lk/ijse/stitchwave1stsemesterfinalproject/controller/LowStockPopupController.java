package lk.ijse.stitchwave1stsemesterfinalproject.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.ijse.stitchwave1stsemesterfinalproject.dto.tm.SewnClothesStockTM;
import lk.ijse.stitchwave1stsemesterfinalproject.model.SewnClothesStockModel;

import java.util.Map;

public class LowStockPopupController {
    @FXML
    private TableColumn<?, ?> colFabric;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colStockId;

    @FXML
    private TableView<SewnClothesStockTM> stockTable;

    private final ObservableList<SewnClothesStockTM> lowStockData = FXCollections.observableArrayList();

    SewnClothesStockModel sewnClothesStockModel = new SewnClothesStockModel();

    @FXML
    public void initialize() {
        colStockId.setCellValueFactory(new PropertyValueFactory<>("stock_id"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colFabric.setCellValueFactory(new PropertyValueFactory<>("fabric_id"));

        stockTable.setItems(lowStockData);
    }

    public void setLowStockData(Map<String, Integer> stockItems) {
        lowStockData.clear(); // Clear existing data
        stockItems.forEach((fabricId, quantity) -> {
            if (quantity < 5000) { // Only add items below the threshold
                lowStockData.add(new SewnClothesStockTM(fabricId, quantity, fabricId.split("-")[0]));
            }
        });
    }

    @FXML
    public void onCloseButtonAction() {
        Stage stage = (Stage) stockTable.getScene().getWindow();
        stage.close();
    }
}