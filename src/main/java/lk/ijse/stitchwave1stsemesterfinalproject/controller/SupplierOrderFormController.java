package lk.ijse.stitchwave1stsemesterfinalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class SupplierOrderFormController {

    @FXML
    private TableColumn<?, ?> dateclmn;

    @FXML
    private Label datelbl;

    @FXML
    private Button dltbtn;

    @FXML
    private ImageView iconimg;

    @FXML
    private TableColumn<?, ?> idclmn;

    @FXML
    private Label lb;

    @FXML
    private Label lblid;

    @FXML
    private Label orderidlbl;

    @FXML
    private TableColumn<?, ?> qtyclmn;

    @FXML
    private Label qtylbl;

    @FXML
    private TextField qtytxt;

    @FXML
    private Button resetbtn;

    @FXML
    private Button savebtn;

    @FXML
    private TableColumn<?, ?> supidclmn;

    @FXML
    private Label supidlbl;

    @FXML
    private TextField supidtxt;

    @FXML
    private Label suporderdate;

    @FXML
    private AnchorPane supplierorderap;

    @FXML
    private TableView<?> supplierordertable;

    @FXML
    private Button updatebtn;

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