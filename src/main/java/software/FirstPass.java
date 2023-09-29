package software;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import software.DataBase.DataBaseConnection;

import java.io.IOException;
import java.util.Objects;

public class FirstPass extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FirstPass.class.getResource("fxml/mainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1920, 1010);
        stage.setTitle("First Pass");
        stage.getIcons().add(new Image(Objects.requireNonNull(FirstPassController.class.getResourceAsStream("img/logo/logo.png"))));
        stage.setScene(scene);
        ((FirstPassController)fxmlLoader.getController()).creerElement();
        stage.show();
        stage.setOnCloseRequest(event -> {
            DataBaseConnection.getInstance().closeConnection();
            stage.close();
        });
    }

    public static void main(String[] args) {
        launch();
    }
}
