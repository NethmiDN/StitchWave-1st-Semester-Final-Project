package lk.ijse.stitchwave1stsemesterfinalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import lk.ijse.stitchwave1stsemesterfinalproject.dto.tm.FabricOrderTM;
import lk.ijse.stitchwave1stsemesterfinalproject.dto.tm.FabricTM;
import lk.ijse.stitchwave1stsemesterfinalproject.dto.tm.SupplierOrderTM;

public class FabricOrderDetailsFormController {

    @FXML
    private TableView<FabricOrderTM> fabricordertable;

    @FXML
    private ComboBox<FabricTM> cmbfabricid;

    @FXML
    private ComboBox<SupplierOrderTM> cmborderid;

    @FXML
    private Button dltbtn;

    @FXML
    private TableColumn<FabricTM, String> fabricidclmn;

    @FXML
    private Label fabricidlbl;

    @FXML
    private AnchorPane fabricorderap;

    @FXML
    private ImageView iconimg;

    @FXML
    private Label lb;

    @FXML
    private TableColumn<SupplierOrderTM, String> orderidclmn;

    @FXML
    private Label orderidlbl;

    @FXML
    private Button resetbtn;

    @FXML
    private Button savebtn;

    @FXML
    private Button updatebtn;

    @FXML
    void cmbfabricidOnAction(ActionEvent event) {

    }

    @FXML
    void cmborderidOnAction(ActionEvent event) {

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
