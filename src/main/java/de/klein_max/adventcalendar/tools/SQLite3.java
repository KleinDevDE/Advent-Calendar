package de.klein_max.adventcalendar.tools;

import de.klein_max.adventcalendar.objects.GiftObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLite3 {
    private static Connection c;
    private static PreparedStatement ps1;
    private static PreparedStatement ps2;
    private static PreparedStatement ps3;
    private static PreparedStatement ps4;
    private static PreparedStatement ps5;
    private static PreparedStatement ps6;

    public static List<GiftObject> getGiftObjects(int day) {
        List<GiftObject> output = new ArrayList<>();
        try {
            ResultSet rs = ps1.executeQuery();
            while (rs.next()) {
                output.add(new GiftObject(
                        rs.getInt("gift_day"),
                        rs.getString("title"),
                        rs.getString("description"),
                        DevTweaks.convertTextToImage(rs.getString("image"))
                ));
            }
            rs.close();
            return output;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static GiftObject getGiftObject(int day) {
        try {
            ps3.setInt(1, day);
            ResultSet rs = ps3.executeQuery();
            while (rs.next()) {
                return new GiftObject(day,
                        rs.getString("title"),
                        rs.getString("description"),
                        DevTweaks.convertTextToImage(rs.getString("image")));
            }
            rs.close();
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void addOrReplaceGiftObject(GiftObject giftObject) {
        try {
            ps2.setInt(1, giftObject.getDay());
            ps2.setString(2, giftObject.getTitle());
            ps2.setString(3, giftObject.getDescription());
            ps2.setString(4, DevTweaks.convertImageToText(giftObject.getImage()));
            ps2.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean exists(int day){
        try {
            ps3.setInt(1, day);
            ResultSet rs = ps3.executeQuery();
            boolean end = rs.next();
            rs.close();
            return end;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void changeState(int day, int state){
        // 1 - opened
        try {
            ps4.setInt(1, state);
            ps4.setInt(2, day);
            ps4.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Integer getState(int day){
        try {
            ps5.setInt(1, day);
            ResultSet rs = ps5.executeQuery();
            rs.next();
            return rs.getInt("state");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean createTables() {
        try {

            PreparedStatement ps = c.prepareStatement("CREATE TABLE IF NOT EXISTS GiftObjects " +
                    "(gift_day          INTEGER     PRIMARY KEY NOT NULL," +
                    " title             TEXT        NULL, " +
                    " description       TEXT        NOT NULL, " +
                    " image             TEXT," +
                    " state             INTEGER     DEFAULT 0)");
            ps.executeUpdate();
            ps.close();
            return true;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
    }

    public static boolean start() {
        boolean status = true;
        boolean failure = false;
        c = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite: data.db");
            createTables();
            initPreparedStatements();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            status = false;
        }
        return status;
    }

    private static void initPreparedStatements(){
        try {
            ps1 = c.prepareStatement("SELECT * FROM GiftObjects");
            ps2 = c.prepareStatement("insert or replace into GiftObjects (gift_day, title, description, image) values " +
                    "(?, ?, ?, ?);");
            ps3 = c.prepareStatement("SELECT * FROM GiftObjects WHERE gift_day=?");
            ps4 = c.prepareStatement("UPDATE GiftObjects SET state=? WHERE gift_day=?");
            ps5 = c.prepareStatement("SELECT state FROM GiftObjects WHERE gift_day=?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}