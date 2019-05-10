package de.klein_max.adventcalendar.objects;

import javafx.scene.image.Image;

public class GiftObject {
    private int day;
    private String title, description;
    private Image image;

    public GiftObject(int day, String title, String description, Image image){
        this.day = day;
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public int getDay() {
        return day;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Image getImage() {
        return image;
    }
}
