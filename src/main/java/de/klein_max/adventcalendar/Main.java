package de.klein_max.adventcalendar;

import de.klein_max.adventcalendar.tools.SQLite3;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import de.klein_max.adventcalendar.tools.ExceptionListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Main extends Application {
    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        SQLite3.start();
        primaryStage = stage;
        stage.initStyle(StageStyle.UNDECORATED);
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/Home.fxml"));
        Scene scene = new Scene(root);

        stage.setTitle("Advent calendar " + new SimpleDateFormat("yyyy").format(new Date()));
        stage.getIcons().add(new Image("img/door.jpg"));
        stage.setScene(scene);
        stage.show();
        ExceptionListener.registerExceptionHandler();
    }
}
