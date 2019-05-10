package de.klein_max.adventcalendar.tools;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * CalcTools
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * 2017, created by KleinDev
 */
public class WebPage {

    /* ------------------------------------------------
    https://stackoverflow.com/questions/10967451/open-a-link-in-browser-with-java-button */
    public static void openWebpage(URI uri) {
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                //desktop = null;
                desktop.browse(uri);
            } catch (Exception e) {
                //
                // TODO
                // Option: Copy URL to Clipboard
                //
                ButtonType buttonType = new ButtonType("Copy URL to Clipboard");
                Alert alert = new Alert(Alert.AlertType.ERROR, "Cannot open default Browser!", buttonType, ButtonType.OK);
                alert.showAndWait();
                if (alert.getResult() == buttonType) {
                    try {
                        StringSelection selection = new StringSelection(uri.toURL().toExternalForm());
                        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                        clipboard.setContents(selection, selection);
                    } catch (MalformedURLException e1) {
                        e1.printStackTrace();
                    }
                } else alert.close();
            }
        }
    }

    public static void openWebpage(URL url) {
        try {
            openWebpage(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
    /* ------------------------------------------------ */


    public static void openWebpage(String url) {
        try {
            openWebpage(new URL(url).toURI());
        } catch (URISyntaxException | MalformedURLException e) {
            e.printStackTrace();
        }
    }
}