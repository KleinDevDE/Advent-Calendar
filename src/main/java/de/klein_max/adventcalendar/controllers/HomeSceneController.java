package de.klein_max.adventcalendar.controllers;

import de.klein_max.adventcalendar.Main;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HomeSceneController {
    public Pane topbar;
    public Button minimizeButton;
    public Button closeButton;
    public Pane pane1, pane2, pane3, pane4, pane5, pane6, pane7, pane8, pane9, pane10, pane11, pane12, pane13, pane14, pane15, pane16, pane17, pane18, pane19, pane20, pane21, pane22, pane23, pane24;

    private double xOffset = 0;
    private double yOffset = 0;
    public ImageView informationButton;
    public Label label;
    public ArrayList<StackPane> doors = new ArrayList<>();

    {
        Main.primaryStage.setOnShowing(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                label.setText(label.getText().replace("%YEAR%", new SimpleDateFormat("yyyy").format(new Date())));
                int day = Integer.parseInt(new SimpleDateFormat("d").format(new Date()));
                int count = 0;
                for (Field f : HomeSceneController.this.getClass().getDeclaredFields()){
                    if (f.getType().equals(Pane.class) && f.getName().startsWith("pane")){
                        StackPane stackPane = new StackPane();
                        stackPane.setPrefSize(50,50);
                        ImageView door = new ImageView("img/door.jpg");
                        Label label = new Label(""+(count+1));

                        label.setPrefSize(60, 60);
                        label.setFont(new Font(30));
                        label.setStyle("-fx-background-color: transparent");
                        label.setAlignment(Pos.CENTER);
                        door.setFitWidth(50);
                        door.setFitHeight(50);
                        if (count+1 == day){
                            int finalCount = count+1;
                            label.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    openDoor(finalCount);
                                }
                            });
                            label.setCursor(Cursor.HAND);
                        } else if (count+1 < day){
                            int finalCount = count+1;
                            label.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    openDoor(finalCount);
                                }
                            });
                            label.setCursor(Cursor.HAND);
                            door.setStyle("-fx-blend-mode: soft-light;");
                        }

                        stackPane.getChildren().add(door);
                        stackPane.getChildren().add(label);
                        //TODO label & image -> button

                        doors.add(stackPane);
                        try {
                            ((Pane)f.get(HomeSceneController.this)).getChildren().add(stackPane);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        count++;
                    }
                }
            }
        });
    }

    public void onInformationClicked(MouseEvent mouseEvent) {
        Stage stage = new Stage();
        stage.setTitle("AdventCalendar ~ about");
        try {
            stage.setScene(new Scene(FXMLLoader.load(Main.class.getClassLoader().getResource("fxml/About.fxml"))));
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
            e.printStackTrace();
        }
        stage.show();
    }

    public void topBarOnMouseDraggedEvent(MouseEvent mouseEvent) {
        Main.primaryStage.setX(mouseEvent.getScreenX() - xOffset);
        Main.primaryStage.setY(mouseEvent.getScreenY() - yOffset);
    }

    public void topBarOnMousePressed(MouseEvent mouseEvent) {
        xOffset = mouseEvent.getSceneX();
        yOffset = mouseEvent.getSceneY();
    }

    public void onClose(MouseEvent mouseEvent) {
        System.exit(0);
    }

    public void onMinimize(MouseEvent mouseEvent) {
        Main.primaryStage.setIconified(true);
        mouseEvent.setDragDetect(false);
    }

    private void openDoor(int id){
        int day = Integer.parseInt(new SimpleDateFormat("d").format(new Date()));
        if (day == id){
            StackPane stackPane = doors.get(id-1);
            ImageView imageView = (ImageView) stackPane.getChildren().get(0);
            imageView.setStyle("-fx-blend-mode: soft-light;");
            DoorSceneController.open(id);
            //TODO save, that door was opened
        } else if(id < day){
            DoorSceneController.open(id);
        }
    }


    public void onSettingsClicked(MouseEvent mouseEvent) {
       SettingsSceneController.open();
    }
}
