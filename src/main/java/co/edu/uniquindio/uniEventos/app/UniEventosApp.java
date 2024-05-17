package co.edu.uniquindio.uniEventos.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UniEventosApp extends Application {
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(UniEventosApp.class.getResource("/paginaInicio.fxml"));
        Parent parent = loader.load();

        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Empresa Envios");
        stage.setResizable(false);
        stage.show();

    }

    public static void main(String[] args) {
        launch(UniEventosApp.class, args);
    }
}
