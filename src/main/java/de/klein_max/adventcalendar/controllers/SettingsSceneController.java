package de.klein_max.adventcalendar.controllers;

import de.klein_max.adventcalendar.objects.GiftObject;
import de.klein_max.adventcalendar.tools.DevTweaks;
import de.klein_max.adventcalendar.tools.SQLite3;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;

public class SettingsSceneController {
    public TextField imagePath;
    private static Stage stage = new Stage();
    public ImageView imageView;
    public TextField title;
    public TextArea description;

    public void browseClicked(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Image files", "png", "jpg", "jpeg", "bmp"));
        fileChooser.setTitle("Choose an image...");
        File file = fileChooser.showOpenDialog(stage);
        imagePath.setText(file.getAbsolutePath());
        try {
            imageView.setImage(new Image(new FileInputStream(file)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            DevTweaks.showError(e);
        }
    }

    public void onImagePathChanged(InputMethodEvent inputMethodEvent) {
        if (!new File(inputMethodEvent.getCommitted()).exists())
            imagePath.setStyle("-fx-background-color:  red;");
        else imagePath.setStyle("-fx-background-color: green;");
    }

    static void open() {
        try {
            stage.setScene(new Scene(FXMLLoader.load(DoorSceneController.class.getClassLoader().getResource("fxml/Settings.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        stage.show();
    }

    public void onSaveClicked(MouseEvent mouseEvent) {
        //TODO save
        if (title.getText().equals("") || description.getText().equals("") || imageView.getImage() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You must fill all fields!", ButtonType.OK);
            alert.show();
            return;
        }
        Optional<Integer> optionalInteger = getDay();
        if (optionalInteger.isPresent()) {
            int day = optionalInteger.get();
            SQLite3.addOrReplaceGiftObject(new GiftObject(
                    day,
                    title.getText(),
                    description.getText(),
                    imageView.getImage()

            ));
        }
    }

    public void onLoadClicked(MouseEvent mouseEvent) {
        //TODO load
        Optional<Integer> optionalInteger = getDay();
        if (optionalInteger.isPresent()) {
            int day = optionalInteger.get();
            GiftObject giftObject = SQLite3.getGiftObject(day);
            if (giftObject.getImage() == null)
                imageView.setImage(new Image("img/no-image.png"));
            else imageView.setImage(giftObject.getImage());
            title.setText(giftObject.getTitle());
            description.setText(giftObject.getDescription());
        }
    }

    private Optional<Integer> getDay() {
        Dialog dialog = new Dialog();

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField dayfield = new TextField();
        dayfield.setPrefWidth(80);
        dayfield.setPromptText("Day");
        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

        Node closeButton = dialog.getDialogPane().lookupButton(ButtonType.CLOSE);

        closeButton.managedProperty().bind(closeButton.visibleProperty());
        closeButton.setVisible(false);

        dayfield.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (newValue.matches("\\d*") && !dayfield.getText().equals("")) {
                    int day = Integer.parseInt(dayfield.getText());
                    if (day >= 1 && day <= 24) {
                        dayfield.setStyle("-fx-text-inner-color: black;");
                        dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
                    } else {
                        dayfield.setStyle("-fx-text-inner-color: red;");
                        dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
                    }
                } else dayfield.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });


        grid.add(new Label("Please insert the day"), 0, 0);
        grid.add(dayfield, 1, 0);

        dialog.showAndWait();

        if (dayfield.getText().matches("\\d*") && !dayfield.getText().equals("")) {
            int day = Integer.parseInt(dayfield.getText());
            if (day >= 1 && day <= 24)
                return Optional.of(Integer.parseInt(dayfield.getText()));
        }
        return Optional.empty();
    }
}
