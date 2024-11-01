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
import lk.ijse.stitchwave1stsemesterfinalproject.model.OrdersModel;

public class OrdersFormController {

    @FXML
    private TableColumn<?, ?> cusidclmn;

    @FXML
    private Label cusidlbl;

    @FXML
    private TextField cusidtxt;

    @FXML
    private Label cusnamelbl;

    @FXML
    private TableColumn<?, ?> dateclmn;

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
    private TableColumn<?, ?> orderidclmn;

    @FXML
    private Label orderidlbl;

    @FXML
    private AnchorPane ordersap;

    @FXML
    private TableView<?> orderstable;

    @FXML
    private TableColumn<?, ?> paymentidclmn;

    @FXML
    private Label paymentidlbl;

    @FXML
    private TextField paymentidtxt;

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
    private Button updatebtn;

    OrdersModel ordersModel = new OrdersModel();

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