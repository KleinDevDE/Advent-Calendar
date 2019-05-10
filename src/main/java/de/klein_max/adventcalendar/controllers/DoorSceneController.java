package de.klein_max.adventcalendar.controllers;

import de.klein_max.adventcalendar.objects.GiftObject;
import de.klein_max.adventcalendar.tools.SQLite3;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class DoorSceneController {
    private static int day;
    public ImageView image;
    public Label title;
    public Label description;
    private static Stage stage = new Stage();

    {
        stage.setOnShowing(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                GiftObject giftObject = SQLite3.getGiftObject(day);
                if (giftObject.getImage() == null)
                    image.setImage(new Image("img/no-image.png"));
                else image.setImage(giftObject.getImage());
                title.setText(giftObject.getTitle());
                description.setText(giftObject.getDescription());
            }
        });
    }

    static void open(int day1) {
        day = day1;
        try {
            stage.setScene(new Scene(FXMLLoader.load(DoorSceneController.class.getClassLoader().getResource("fxml/Door.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.show();
    }

    public void onImageClicked(MouseEvent mouseEvent) {
        Stage stage1 = new Stage(StageStyle.UNDECORATED);
        stage1.setWidth(300);
        stage1.setHeight(200);
        Pane pane = new Pane();
        pane.getChildren().add(image);
        stage1.setScene(new Scene(pane));
        stage1.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage1.close();
            }
        });
        stage1.show();
    }
}
