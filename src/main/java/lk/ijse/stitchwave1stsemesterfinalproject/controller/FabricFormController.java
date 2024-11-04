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
import lk.ijse.stitchwave1stsemesterfinalproject.dto.tm.FabricTM;
import lk.ijse.stitchwave1stsemesterfinalproject.model.FabricModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class FabricFormController  implements Initializable {

    @FXML
    private TableColumn<FabricTM, String> colorclmn;

    @FXML
    private Label colorlbl;

    @FXML
    private TextField colortxt;

    @FXML
    private Button dltbtn;

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
    private Button resetbtn;

    @FXML
    private Button savebtn;

    @FXML
    private Button updatebtn;

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
    private Label lb;

    @FXML
    private TextField widthtxt;

    FabricModel fabricModel = new FabricModel();

    public void initialize(URL url, ResourceBundle resourceBundle) {
        idclmn.setCellValueFactory(new PropertyValueFactory<>("fabric_id"));
        colorclmn.setCellValueFactory(new PropertyValueFactory<>("color"));
        weightclmn.setCellValueFactory(new PropertyValueFactory<>("weight_kg"));
        widthclmn.setCellValueFactory(new PropertyValueFactory<>("width_inch"));

        try {
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();

        String nextFabricID = fabricModel.getNextFabricId();
        lblid.setText(nextFabricID);

        colortxt.setText("");
        weighttxt.setText("");
        widthtxt.setText("");

        savebtn.setDisable(false);

        dltbtn.setDisable(true);
        updatebtn.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<FabricDTO> fabricDTOS = fabricModel.getAllFabrics();
        ObservableList<FabricTM> fabricTMS = FXCollections.observableArrayList();

        for (FabricDTO fabricDTO : fabricDTOS) {
            FabricTM fabricTM = new FabricTM(
                    fabricDTO.getFabric_id(),
                    fabricDTO.getColor(),
                    fabricDTO.getWeight_kg(),
                    fabricDTO.getWidth_inch()
            );
            fabricTMS.add(fabricTM);
        }
        fabtable.setItems(fabricTMS);
    }
    @FXML
    void dltbtnOnAction(ActionEvent event) throws SQLException {
        String fabric_id = lblid.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this fabric?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {

            boolean isDeleted = fabricModel.deleteFabric(fabric_id);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Fabric deleted...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete fabric...!").show();
            }
        }
    }

    @FXML
    void onClickTable(MouseEvent event) {
        FabricTM selectedItem = fabtable.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            lblid.setText(selectedItem.getFabric_id());
            colortxt.setText(selectedItem.getColor());
            weighttxt.setText(String.valueOf(selectedItem.getWeight_kg()));
            widthtxt.setText(String.valueOf(selectedItem.getWidth_inch()));

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
        String fabric_id = lblid.getText();
        String color = colortxt.getText();
        Double weight_kg = Double.valueOf(weighttxt.getText());
        Double width_inch = Double.valueOf(widthtxt.getText());

        // Define regex patterns for validation
        String colorPattern = "^[A-Za-z ]+$";
        String weightKgPattern = "^(\\d|[1-9]\\d)(\\.\\d+)?$";
        String widthInchPattern = "^(\\d|[1-9]\\d)(\\.\\d+)?$";



//        (1)
//        Pattern compile = Pattern.compile(namePattern);
//        boolean isValidName = compile.matcher(name).matches();

//        (2)
//        Validate each field using regex patterns
        boolean isValidColor = color.matches(colorPattern);
        boolean isValidWeight_kg = Pattern.compile(weightKgPattern).matcher(weighttxt.getText()).matches();
        boolean isValidWidth_inch = Pattern.compile(widthInchPattern).matcher(widthtxt.getText()).matches();

        // Reset input field styles
        colortxt.setStyle(colortxt.getStyle() + ";-fx-border-color:  #091057;");
        weighttxt.setStyle(weighttxt.getStyle() + ";-fx-border-color:  #091057;");
        widthtxt.setStyle(widthtxt.getStyle() + ";-fx-border-color:  #091057;");

        // Highlight invalid fields in red

        if (!isValidColor) {
            colortxt.setStyle(colortxt.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidWeight_kg) {
            weighttxt.setStyle(weighttxt.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidWidth_inch) {
            widthtxt.setStyle(widthtxt.getStyle() + ";-fx-border-color: red;");
        }

        // Save fabric if all fields are valid
        if (isValidColor && isValidWeight_kg && isValidWidth_inch) {
            FabricDTO fabricDTO = new FabricDTO(fabric_id, color, weight_kg, width_inch);

            boolean isSaved = fabricModel.saveFabric(fabricDTO);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Fabric saved...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to save fabric...!").show();
            }
        }
    }

    @FXML
    void updatebtnOnAction(ActionEvent event) throws SQLException {
        String fabric_id = lblid.getText();
        String color = colortxt.getText();
        Double weight_kg = Double.valueOf(weighttxt.getText());
        Double width_inch = Double.valueOf(widthtxt.getText());

        String colorPattern = "^[A-Za-z ]+$";
        String weightKgPattern = "^(\\d|[1-9]\\d)(\\.\\d+)?$";
        String widthInchPattern = "^(\\d|[1-9]\\d)(\\.\\d+)?$";



//        (1)
//        Pattern compile = Pattern.compile(namePattern);
//        boolean isValidName = compile.matcher(name).matches();

//        (2)
//        Validate each field using regex patterns
        boolean isValidColor = color.matches(colorPattern);
        boolean isValidWeight_kg = Pattern.compile(weightKgPattern).matcher(weighttxt.getText()).matches();
        boolean isValidWidth_inch = Pattern.compile(widthInchPattern).matcher(widthtxt.getText()).matches();

        colortxt.setStyle(colortxt.getStyle() + ";-fx-border-color:  #091057;");
        weighttxt.setStyle(weighttxt.getStyle() + ";-fx-border-color:  #091057;");
        widthtxt.setStyle(widthtxt.getStyle() + ";-fx-border-color:  #091057;");

        if (!isValidColor) {
            colortxt.setStyle(colortxt.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidWeight_kg) {
            weighttxt.setStyle(weighttxt.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidWidth_inch) {
            widthtxt.setStyle(widthtxt.getStyle() + ";-fx-border-color: red;");
        }

        if (isValidColor && isValidWeight_kg && isValidWidth_inch) {

            FabricDTO fabricDTO = new FabricDTO(fabric_id, color, weight_kg, width_inch);

            boolean isUpdate = fabricModel.updateFabric(fabricDTO);

            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "Fabric updated...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to update fabric...!").show();
            }
        }
    }

}
