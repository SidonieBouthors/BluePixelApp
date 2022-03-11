package bluepixel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BluePixel extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("bluepixel-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 1200, 800);
        stage.setTitle("BluePixel");
        stage.setScene(scene);
        BluePixelController controller = loader.getController();
        controller.setStage(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}