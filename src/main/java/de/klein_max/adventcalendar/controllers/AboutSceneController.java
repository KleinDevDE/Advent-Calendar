package de.klein_max.adventcalendar.controllers;

import de.klein_max.adventcalendar.tools.WebPage;
import javafx.scene.input.MouseEvent;

public class AboutSceneController {
    public void githubClicked(MouseEvent mouseEvent) {
        WebPage.openWebpage("https://github.com/KleinDevDE");
    }
}
