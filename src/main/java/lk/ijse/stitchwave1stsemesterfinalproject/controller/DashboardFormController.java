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
    private Button clothesorderdetailbtn;

    @FXML
    private Button customerbtn;

    @FXML
    private Button empbtn;

    @FXML
    private Button fabricbtn;

    @FXML
    private Button fabricorderdetailbtn;

    @FXML
    private Button orderbtn;

    @FXML
    private Button paymentbtn;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Button sewnclothesstockbtn;

    @FXML
    private Button stylebtn;

    @FXML
    private Button supplierbtn;

    @FXML
    private Button supplierorderbtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        navigateTo("/view/EmployeeForm.fxml");
        initializeButtonEffect(empbtn);
        initializeButtonEffect(customerbtn);
        initializeButtonEffect(orderbtn);
        initializeButtonEffect(paymentbtn);
        initializeButtonEffect(clothesorderdetailbtn);
        initializeButtonEffect(fabricbtn);
        initializeButtonEffect(stylebtn);
        initializeButtonEffect(sewnclothesstockbtn);
        initializeButtonEffect(supplierbtn);
        initializeButtonEffect(supplierorderbtn);
        initializeButtonEffect(fabricorderdetailbtn);
    }

    @FXML
    private void initializeButtonEffect(Button button) {
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.rgb(255, 105, 180, 0.6));
        button.setOnMouseEntered(e -> button.setEffect(shadow));
        button.setOnMouseExited(e -> button.setEffect(null));

        button.setOnMousePressed(e -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), button);
            scaleTransition.setToX(0.95);
            scaleTransition.setToY(0.95);
            scaleTransition.play();
        });

        button.setOnMouseReleased(e -> {
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(200), button);
            scaleTransition.setToX(1.0);
            scaleTransition.setToY(1.0);
            scaleTransition.play();
        });
    }

    @FXML
    void clothesorderdetailbtnOnAction(ActionEvent event) {
        navigateTo("/view/ClothesOrderDetailsForm.fxml");
    }

    @FXML
    void customerbtnOnAction(ActionEvent event) {
        navigateTo("/view/CustomerForm.fxml");
    }

    @FXML
    void empbtnOnAction(ActionEvent event) {
        navigateTo("/view/EmployeeForm.fxml");
    }

    @FXML
    void fabricbtnOnAction(ActionEvent event) {
        navigateTo("/view/FabricForm.fxml");
    }

    @FXML
    void fabricorderdetailbtnOnAction(ActionEvent event) {
        navigateTo("/view/FabricOrderDetailsForm.fxml");
    }

    @FXML
    void orderbtnOnAction(ActionEvent event) {
        navigateTo("/view/OrdersForm.fxml");
    }

    @FXML
    void paymentbtnOnAction(ActionEvent event) {
        navigateTo("/view/PaymentForm.fxml");
    }

    @FXML
    void sewnclothesstockbtnOnAction(ActionEvent event) {
        navigateTo("/view/SewnClothesStockForm.fxml");
    }

    @FXML
    void stylebtnOnAction(ActionEvent event) {
        navigateTo("/view/StyleForm.fxml");
    }

    @FXML
    void supplierbtnOnAction(ActionEvent event) {
        navigateTo("/view/SupplierForm.fxml");
    }

    @FXML
    void supplierorderbtnOnAction(ActionEvent event) {
        navigateTo("/view/SupplierOrderForm.fxml");
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
        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), node);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(500), node);
        scaleTransition.setFromX(0.9);
        scaleTransition.setFromY(0.9);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);

        TranslateTransition slideTransition = new TranslateTransition(Duration.millis(500), node);
        slideTransition.setFromX(50);
        slideTransition.setToX(0);

        ParallelTransition parallelTransition = new ParallelTransition(fadeIn, scaleTransition, slideTransition);
        parallelTransition.play();
    }
}