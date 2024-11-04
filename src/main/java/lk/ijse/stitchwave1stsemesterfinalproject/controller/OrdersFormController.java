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
import lk.ijse.stitchwave1stsemesterfinalproject.dto.tm.CustomerTM;
import lk.ijse.stitchwave1stsemesterfinalproject.dto.tm.OrdersTM;
import lk.ijse.stitchwave1stsemesterfinalproject.dto.tm.PaymentTM;

import java.time.LocalDate;

public class OrdersFormController {

    @FXML
    private TableColumn<CustomerTM, String> cusidclmn;

    @FXML
    private Label cusidlbl;

    @FXML
    private TextField cusidtxt;

    @FXML
    private Label cusnamelbl;

    @FXML
    private Button customersearchbtn;

    @FXML
    private TableColumn<OrdersTM, LocalDate> dateclmn;

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
    private TableView<OrdersTM> orderstable;

    @FXML
    private TableColumn<PaymentTM, String> paymentidclmn;

    @FXML
    private Label paymentidlbl;

    @FXML
    private TextField paymentidtxt;

    @FXML
    private Label paymentlbl;

    @FXML
    private Button paymentsearchbtn;

    @FXML
    private TableColumn<OrdersTM, Integer> qtyclmn;

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

    @FXML
    void customersearchbtnOnAction(ActionEvent event) {

    }

    @FXML
    void dltbtnOnAction(ActionEvent event) {

    }

    @FXML
    void onClickTable(MouseEvent event) {

    }

    @FXML
    void paymentsearchbtnOnAction(ActionEvent event) {

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
