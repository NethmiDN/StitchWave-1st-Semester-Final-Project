package lk.ijse.stitchwave1stsemesterfinalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
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

    public void initialize(URL url, ResourceBundle rb) {
        navigateTo("/view/EmployeeForm.fxml");
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

    public void navigateTo(String fxmlPath) {
        try {
            ap.getChildren().clear();
            AnchorPane load = FXMLLoader.load(getClass().getResource(fxmlPath));

//  -------- Loaded anchor edges are bound to the content anchor --------
//      (1) Bind the loaded FXML to all edges of the content anchorPane
            load.prefWidthProperty().bind(ap.widthProperty());
            load.prefHeightProperty().bind(ap.heightProperty());

//            AnchorPane.setTopAnchor(load, 0.0);
//            AnchorPane.setRightAnchor(load, 0.0);
//            AnchorPane.setBottomAnchor(load, 0.0);
//            AnchorPane.setLeftAnchor(load, 0.0);

            ap.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Fail to load page!").show();
        }
    }

    private void loadUI(String resource) {
        rootPane.getChildren().clear();
        try {
            rootPane.getChildren().add(FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resource))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}