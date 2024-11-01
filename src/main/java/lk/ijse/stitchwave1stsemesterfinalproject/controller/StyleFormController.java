package lk.ijse.stitchwave1stsemesterfinalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import lk.ijse.stitchwave1stsemesterfinalproject.dto.tm.StyleTM;

public class StyleFormController {

    @FXML
    private Button dltbtn;

    @FXML
    private TableColumn<StyleTM, String> empidclmn;

    @FXML
    private Label empidlbl;

    @FXML
    private TextField employeeidtxt;

    @FXML
    private Label employeenamelbl;

    @FXML
    private ImageView iconimg;

    @FXML
    private Label idlbl;

    @FXML
    private Label lb;

    @FXML
    private TableColumn<StyleTM, Long> qtyclmn;

    @FXML
    private Label qtylbl;

    @FXML
    private TextField qtytxt;

    @FXML
    private Button resetbtn;

    @FXML
    private Button savebtn;

    @FXML
    private TableColumn<StyleTM, String> sizeclmn;

    @FXML
    private Label sizelbl;

    @FXML
    private TextField sizetxt;

    @FXML
    private TableColumn<StyleTM, String> stockidclmn;

    @FXML
    private Label stockidlbl;

    @FXML
    private TextField stockidtxt;

    @FXML
    private AnchorPane styleap;

    @FXML
    private TableColumn<StyleTM, String> styleclmn;

    @FXML
    private Label styleidlbl;

    @FXML
    private TableView<StyleTM> styletable;

    @FXML
    private Button updatebtn;

    @FXML
    void dltbtnOnAction(ActionEvent event) {

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
