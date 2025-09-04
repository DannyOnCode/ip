package aura.gui;

import java.io.IOException;

import aura.Aura;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * A GUI for Aura using FXML.
 */
public class Main extends Application {
    private Scene scene;
    private final Aura aura = new Aura("./data/Aura.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setAura(aura);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
