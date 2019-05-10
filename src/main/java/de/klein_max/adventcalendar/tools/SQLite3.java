package de.klein_max.adventcalendar.tools;

import de.klein_max.adventcalendar.objects.GiftObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLite3 {
    private static Connection c;

    public static List<GiftObject> getGiftObjects(int day) {
        List<GiftObject> output = new ArrayList<>();
        try {
            PreparedStatement ps = c.prepareStatement("SELECT * FROM GiftObjects");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                output.add(new GiftObject(
                        rs.getInt("gift_day"),
                        rs.getString("title"),
                        rs.getString("description"),
                        DevTweaks.convertTextToImage(rs.getString("image"))
                ));
            }
            ps.close();
            rs.close();
            return output;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static GiftObject getGiftObject(int day){
        try {
            PreparedStatement ps = c.prepareStatement("SELECT * FROM GiftObjects WHERE gift_day=?");
            ps.setInt(1, day);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return new GiftObject(day,
                        rs.getString("title"),
                        rs.getString("description"),
                        DevTweaks.convertTextToImage(rs.getString("image")));
            }
            rs.close();
            ps.close();
            return null;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addOrReplaceGiftObject(GiftObject giftObject){
        try {
            c.setAutoCommit(false);

            PreparedStatement ps = c.prepareStatement("insert or replace into GiftObjects (gift_day, title, description, image) values " +
                    "(?, ?, ?, ?);");
            ps.setInt(1, giftObject.getDay());
            ps.setString(2, giftObject.getTitle());
            ps.setString(3, giftObject.getDescription());
            ps.setString(4, DevTweaks.convertImageToText(giftObject.getImage()));
            ps.close();
            c.commit();
        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public static boolean start(){
        boolean status = true;
        boolean failure = false;
        c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite: data.db");
            createTables();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            status = false;
        }
        return status;
    }

    private static boolean createTables(){
        try {

            PreparedStatement ps = c.prepareStatement("CREATE TABLE IF NOT EXISTS GiftObjects " +
                    "(gift_day          INTEGER     PRIMARY KEY NOT NULL," +
                    " title             TEXT        NULL, " +
                    " description       TEXT        NOT NULL, " +
                    " image             TEXT)");
            ps.executeUpdate();
            ps.close();
            return true;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
    }
}