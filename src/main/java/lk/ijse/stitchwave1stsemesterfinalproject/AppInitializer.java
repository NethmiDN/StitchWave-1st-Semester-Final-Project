package lk.ijse.stitchwave1stsemesterfinalproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class AppInitializer extends Application {
    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("STITCH WAVE");
        stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/loginForm.fxml")))));
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/assets/img/3dmachine.png"))));
        stage.show();
    }
}