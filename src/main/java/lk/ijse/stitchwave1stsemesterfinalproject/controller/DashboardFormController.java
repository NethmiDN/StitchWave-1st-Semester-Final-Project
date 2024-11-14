package lk.ijse.stitchwave1stsemesterfinalproject.controller;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {

    @FXML
    private AnchorPane ap;

    @FXML
    private Button clothesorderdetailbtn, customerbtn, empbtn, fabricbtn,
            fabricorderdetailbtn, paymentbtn, sewnclothesstockbtn,
            stylebtn, supplierbtn, supplierorderbtn, settingbtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        navigateTo("/view/EmployeeForm.fxml");
        initializeButtonEffect(empbtn);
        initializeButtonEffect(customerbtn);
        initializeButtonEffect(paymentbtn);
        initializeButtonEffect(clothesorderdetailbtn);
        initializeButtonEffect(fabricbtn);
        initializeButtonEffect(stylebtn);
        initializeButtonEffect(sewnclothesstockbtn);
        initializeButtonEffect(supplierbtn);
        initializeButtonEffect(supplierorderbtn);
        initializeButtonEffect(fabricorderdetailbtn);
        initializeButtonEffect(settingbtn);

        onButtonClicked(empbtn);
        setButtonSizes();
    }

    @FXML
    private void initializeButtonEffect(Button button) {
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.rgb(100, 100, 100, 0.4));

        button.setOnMouseEntered(e -> button.setEffect(shadow));
        button.setOnMouseExited(e -> button.setEffect(null));

        button.setOnMousePressed(e -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(150), button);
            scaleTransition.setToX(0.95);
            scaleTransition.setToY(0.95);
            scaleTransition.play();
        });

        button.setOnMouseReleased(e -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(150), button);
            scaleTransition.setToX(1.0);
            scaleTransition.setToY(1.0);
            scaleTransition.play();
        });
    }

    private void setButtonSizes() {
        double buttonWidth = 240;
        double buttonHeight = 20;

        empbtn.setPrefSize(buttonWidth, buttonHeight);
        customerbtn.setPrefSize(buttonWidth, buttonHeight);
        paymentbtn.setPrefSize(buttonWidth, buttonHeight);
        clothesorderdetailbtn.setPrefSize(buttonWidth, buttonHeight);
        fabricbtn.setPrefSize(buttonWidth, buttonHeight);
        stylebtn.setPrefSize(buttonWidth, buttonHeight);
        sewnclothesstockbtn.setPrefSize(buttonWidth, buttonHeight);
        supplierbtn.setPrefSize(buttonWidth, buttonHeight);
        supplierorderbtn.setPrefSize(buttonWidth, buttonHeight);
        fabricorderdetailbtn.setPrefSize(buttonWidth, buttonHeight);
        settingbtn.setPrefSize(buttonWidth, buttonHeight);
    }

    private void setActiveButtonStyle(Button activeButton) {
        activeButton.setStyle(
                "-fx-background-color: linear-gradient(to right, #1FA2FF, #1885D2);" +
                        "-fx-text-fill: #FFFFFF;" +
                        "-fx-border-color: #1885D2;" +
                        "-fx-border-width: 1;" +
                        "-fx-background-radius: 6;" +
                        "-fx-border-radius: 6;" +
                        "-fx-effect: dropshadow(gaussian, rgba(24, 133, 210, 0.4), 12, 0, 0, 3);"
        );
    }

    private void resetButtonStyles() {
        String resetStyle =
                "-fx-background-color: transparent;" +
                        "-fx-border-color: linear-gradient(to right, #1FA2FF, #1885D2);" +
                        "-fx-border-width: 1;" +
                        "-fx-background-radius: 6;" +
                        "-fx-border-radius: 6;" +
                        "-fx-text-fill: #1885D2;";

        empbtn.setStyle(resetStyle);
        customerbtn.setStyle(resetStyle);
        paymentbtn.setStyle(resetStyle);
        clothesorderdetailbtn.setStyle(resetStyle);
        fabricbtn.setStyle(resetStyle);
        stylebtn.setStyle(resetStyle);
        sewnclothesstockbtn.setStyle(resetStyle);
        supplierbtn.setStyle(resetStyle);
        supplierorderbtn.setStyle(resetStyle);
        fabricorderdetailbtn.setStyle(resetStyle);
        settingbtn.setStyle(resetStyle);
    }

    private void onButtonClicked(Button selectedButton) {
        resetButtonStyles();
        setActiveButtonStyle(selectedButton);
    }

    @FXML
    private void clothesorderdetailbtnOnAction(ActionEvent event) {
        navigateTo("/view/ClothesOrderDetailsForm.fxml");
        onButtonClicked(clothesorderdetailbtn);
    }

    @FXML
    private void customerbtnOnAction(ActionEvent event) {
        navigateTo("/view/CustomerForm.fxml");
        onButtonClicked(customerbtn);
    }

    @FXML
    private void empbtnOnAction(ActionEvent event) {
        navigateTo("/view/EmployeeForm.fxml");
        onButtonClicked(empbtn);
    }

    @FXML
    private void fabricbtnOnAction(ActionEvent event) {
        navigateTo("/view/FabricForm.fxml");
        onButtonClicked(fabricbtn);
    }

    @FXML
    private void fabricorderdetailbtnOnAction(ActionEvent event) {
        navigateTo("/view/FabricOrderDetailsForm.fxml");
        onButtonClicked(fabricorderdetailbtn);
    }

    @FXML
    private void paymentbtnOnAction(ActionEvent event) {
        navigateTo("/view/PaymentForm.fxml");
        onButtonClicked(paymentbtn);
    }

    @FXML
    private void sewnclothesstockbtnOnAction(ActionEvent event) {
        navigateTo("/view/SewnClothesStockForm.fxml");
        onButtonClicked(sewnclothesstockbtn);
    }

    @FXML
    private void stylebtnOnAction(ActionEvent event) {
        navigateTo("/view/StyleForm.fxml");
        onButtonClicked(stylebtn);
    }

    @FXML
    private void supplierbtnOnAction(ActionEvent event) {
        navigateTo("/view/SupplierForm.fxml");
        onButtonClicked(supplierbtn);
    }

    @FXML
    private void supplierorderbtnOnAction(ActionEvent event) {
        navigateTo("/view/SupplierOrderForm.fxml");
        onButtonClicked(supplierorderbtn);
    }

    @FXML
    void settingbtnOnAction(ActionEvent event) {
        navigateTo("/view/SettingForm.fxml");
        onButtonClicked(settingbtn);
    }

    private void navigateTo(String fxmlPath) {
        try {
            ap.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));

            load.prefWidthProperty().bind(ap.widthProperty());
            load.prefHeightProperty().bind(ap.heightProperty());
            ap.getChildren().add(load);

            applyEnhancedTransition(load);

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load page!").show();
        }
    }

    private void applyEnhancedTransition(Node node) {
        FadeTransition fadeIn = new FadeTransition(Duration.millis(400), node);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(400), node);
        scaleTransition.setFromX(0.85);
        scaleTransition.setFromY(0.85);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);

        TranslateTransition slideTransition = new TranslateTransition(Duration.millis(400), node);
        slideTransition.setFromY(20);
        slideTransition.setToY(0);

        ParallelTransition parallelTransition = new ParallelTransition(fadeIn, scaleTransition, slideTransition);
        parallelTransition.play();
    }
}