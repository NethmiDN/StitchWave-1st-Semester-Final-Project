package lk.ijse.stitchwave1stsemesterfinalproject.controller;

import com.jfoenix.controls.JFXBadge;
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
import lk.ijse.stitchwave1stsemesterfinalproject.dto.*;
import lk.ijse.stitchwave1stsemesterfinalproject.dto.tm.*;
import lk.ijse.stitchwave1stsemesterfinalproject.model.EmployeeModel;
import lk.ijse.stitchwave1stsemesterfinalproject.model.SewnClothesStockModel;
import lk.ijse.stitchwave1stsemesterfinalproject.model.StyleModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class StyleFormController implements Initializable {

    @FXML
    private ComboBox<String> cmbempid;

    @FXML
    private ComboBox<String> cmbstockid;

    @FXML
    private Button dltbtn;

    @FXML
    private TableColumn<EmployeeTM, String> empidclmn;

    @FXML
    private Label empidlbl;

    @FXML
    private Label employeenamelbl;

    @FXML
    private ImageView iconimg;

    @FXML
    private Label idlbl;

    @FXML
    private Label lb;

    @FXML
    private TableColumn<StyleTM, Integer> qtyclmn;

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
    private TableColumn<SewnClothesStockTM, String> stockidclmn;

    @FXML
    private Label stockidlbl;

    @FXML
    private Label stocklbl;

    @FXML
    private AnchorPane styleap;

    @FXML
    private TableColumn<StyleTM, String> styleidclmn;

    @FXML
    private Label styleidlbl;

    @FXML
    private TableView<StyleTM> styletable;

    @FXML
    private Button updatebtn;

    @FXML
    private TextField empidtxt;

    @FXML
    private TextField stockidtxt;

    StyleModel styleModel = new StyleModel();

    EmployeeModel employeeModel = new EmployeeModel();

    SewnClothesStockModel sewnClothesStockModel = new SewnClothesStockModel();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        styleidclmn.setCellValueFactory(new PropertyValueFactory<>("style_id"));
        sizeclmn.setCellValueFactory(new PropertyValueFactory<>("size"));
        qtyclmn.setCellValueFactory(new PropertyValueFactory<>("qty"));
        empidclmn.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
        stockidclmn.setCellValueFactory(new PropertyValueFactory<>("stock_id"));

        try {
            loadEmployeeIds();
            loadStockIds();
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();

        String nextStyleID = styleModel.getNextStyleId();
        idlbl.setText(nextStyleID);

        sizetxt.setText("");
        qtytxt.setText("");

        savebtn.setDisable(false);
        dltbtn.setDisable(true);
        updatebtn.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<StyleDTO> styleDTOS = styleModel.getAllStyles();
        ObservableList<StyleTM> styleTMS = FXCollections.observableArrayList();

        for (StyleDTO styleDTO : styleDTOS) {
            StyleTM styleTM = new StyleTM(
                    styleDTO.getStyle_id(),
                    styleDTO.getSize(),
                    styleDTO.getQty(),
                    styleDTO.getEmployee_id(),
                    styleDTO.getStock_id()
            );
            styleTMS.add(styleTM);
        }
        styletable.setItems(styleTMS);
    }

    @FXML
    void dltbtnOnAction(ActionEvent event) throws SQLException {
        String style_id = idlbl.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Style?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = styleModel.deleteStyle(style_id);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Style deleted...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete Style...!").show();
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        StyleTM selectedItem = styletable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            idlbl.setText(selectedItem.getStyle_id());
            sizetxt.setText(selectedItem.getSize());
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
        String style_id = idlbl.getText();
        String size = sizetxt.getText();
        Integer qty = Integer.valueOf(qtytxt.getText());
        String employee_id = cmbempid.getValue();
        String stock_id = cmbstockid.getValue();

        // Define regex patterns for validation
        String sizePattern = "(?i)^(xs|s|m|l|xl|xxl|xxxl)$";
        String quantityPattern = "^([1-9]\\d{0,4}|0)$";

//        (1)
//        Pattern compile = Pattern.compile(namePattern);
//        boolean isValidName = compile.matcher(name).matches();

//        (2)
//        Validate each field using regex patterns
        boolean isValidSize = size.matches(sizePattern);
        boolean isValidQty = String.valueOf(qty).matches(String.valueOf(quantityPattern));

        // Reset input field styles
        sizetxt.setStyle(sizetxt.getStyle() + ";-fx-border-color:  #091057;");
        qtytxt.setStyle(qtytxt.getStyle() + ";-fx-border-color:  #091057;");
        //empidtxt.setStyle(empidtxt.getStyle() + ";-fx-border-color:  #091057;");
        //stockidtxt.setStyle(stockidtxt.getStyle() + ";-fx-border-color:  #091057;");

        // Highlight invalid fields in red

        if (!isValidSize) {
            sizetxt.setStyle(qtytxt.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidQty) {
            qtytxt.setStyle(qtytxt.getStyle() + ";-fx-border-color: red;");
        }

        // Save customer if all fields are valid
        if (isValidSize && isValidQty) {
            StyleDTO styleDTO = new StyleDTO(style_id, size, qty, employee_id, stock_id);

            boolean isSaved = styleModel.saveStyle(styleDTO);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Style saved...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save style...!").show();
            }
        }
    }

    @FXML
    void updatebtnOnAction(ActionEvent event) throws SQLException {
        String style_id = idlbl.getText();
        String size = sizetxt.getText();
        Integer qty = Integer.valueOf(qtytxt.getText());
        String employee_id = cmbempid.getValue();
        String stock_id = cmbstockid.getValue();

        String sizePattern = "(?i)^(xs|s|m|l|xl|xxl|xxxl)$";
        String quantityPattern = "^([1-9]\\d{0,4}|0)$";

//        Pattern compile = Pattern.compile(namePattern);
//        System.out.println(compile.matcher(name).matches());
        boolean isValidSize = size.matches(sizePattern);
        boolean isValidQty = String.valueOf(qty).matches(String.valueOf(quantityPattern));


        sizetxt.setStyle(sizetxt.getStyle() + ";-fx-border-color:  #091057;");
        qtytxt.setStyle(qtytxt.getStyle() + ";-fx-border-color:  #091057;");
        //empidtxt.setStyle(empidtxt.getStyle() + ";-fx-border-color:  #091057;");
        //stockidtxt.setStyle(stockidtxt.getStyle() + ";-fx-border-color:  #091057;");

        if (!isValidSize) {
            sizetxt.setStyle(qtytxt.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidQty) {
            qtytxt.setStyle(qtytxt.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidSize && isValidQty) {

            StyleDTO styleDTO = new StyleDTO(style_id, size, qty, employee_id, stock_id);

            boolean isUpdate = styleModel.updateStyle(styleDTO);

            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "Style updated...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update style...!").show();
            }
        }
    }

    private void loadEmployeeIds() throws SQLException {
        ArrayList<String> employeeIds = employeeModel.getAllEmployeeIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(employeeIds);
        cmbempid.setItems(observableList);
    }

    @FXML
    void cmbempidOnAction(ActionEvent event) throws SQLException {
        String selectedEmpId = cmbempid.getSelectionModel().getSelectedItem();
        if (selectedEmpId != null) {
            EmployeeDTO employeeDTO = employeeModel.findById(selectedEmpId);
            if (employeeDTO != null) {
                employeenamelbl.setText(employeeDTO.getName());
            }
        }
    }

    private void loadStockIds() throws SQLException {
        ArrayList<String> stockIds = sewnClothesStockModel.getAllStockIds();
        ObservableList<String> observableList = FXCollections.observableArrayList();
        observableList.addAll(stockIds);
        cmbstockid.setItems(observableList);
    }

    @FXML
    void cmbstockidOnAction(ActionEvent event) throws SQLException {
        String selectedStockId = cmbstockid.getSelectionModel().getSelectedItem();
        if (selectedStockId != null) {
            SewnClothesStockDTO sewnClothesStockDTO = sewnClothesStockModel.findById(selectedStockId);
        }
    }

}
