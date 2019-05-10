package de.klein_max.adventcalendar.controllers;

import de.klein_max.adventcalendar.objects.GiftObject;
import de.klein_max.adventcalendar.tools.SQLite3;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
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
                centerImage();
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

    public void centerImage() {
        Image img = image.getImage();
        if (img != null) {
            double w = 0;
            double h = 0;

            double ratioX = image.getFitWidth() / img.getWidth();
            double ratioY = image.getFitHeight() / img.getHeight();

            double reducCoeff = 0;
            if(ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            image.setX((image.getFitWidth() - w) / 2);
            image.setY((image.getFitHeight() - h) / 2);

        }
    }
}
