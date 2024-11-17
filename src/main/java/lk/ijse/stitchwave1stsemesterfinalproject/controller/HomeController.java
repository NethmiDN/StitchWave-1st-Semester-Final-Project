package lk.ijse.stitchwave1stsemesterfinalproject.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {

    @FXML
    private AnchorPane homepane;

    @FXML
    private ImageView iconimg;

    @FXML
    private ImageView img1;

    @FXML
    private ImageView img2;

    @FXML
    private Button logoutbtn;

    @FXML
    void logoutbtnOnAction(ActionEvent event) {
        navigateTo();
    }

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/loginForm.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void navigateTo() {
        try {

            Stage stage = (Stage) homepane.getScene().getWindow();
            stage.close();

            start(new Stage());

        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load page!").show();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
