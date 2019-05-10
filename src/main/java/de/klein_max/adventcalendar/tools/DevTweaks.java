package de.klein_max.adventcalendar.tools;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.util.Base64;

public class DevTweaks {
    public static void showError(Exception ex){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setHeaderText("Oups, you fucked up!");
        alert.setContentText(ex.getMessage());
        ImageView imageView = new ImageView(new Image("img/trollface.png"));
        imageView.setFitWidth(64);
        imageView.setFitHeight(64);
        alert.setGraphic(imageView);

// Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

// Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }

    public static boolean isInteger(String string){
        try{
            Integer.parseInt(string);
            return true;
        } catch(NumberFormatException ex) {
            return false;
        }
    }

    public static Image convertTextToImage(String string){
        InputStream in = new ByteArrayInputStream(Base64.getDecoder().decode(string));
        return new Image(in);
    }

    public static String convertImageToText(File image){
        try {
            return Base64.getEncoder().encodeToString(Files.readAllBytes(image.toPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String convertImageToText(Image image){
        try {
            BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
            ByteArrayOutputStream s = new ByteArrayOutputStream();
            ImageIO.write(bImage, "png", s);
            byte[] res  = s.toByteArray();
            s.close();
            return Base64.getEncoder().encodeToString(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
