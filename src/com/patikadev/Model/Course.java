package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Course {
    private int id;
    private int user_id;
    private int patika_id;
    private String name;
    private String language;

    private Patika patika;
    private User educator;

    public Course(int id, int user_id, int patika_id, String name, String language) {
        this.id = id;
        this.user_id = user_id;
        this.patika_id = patika_id;
        this.name = name;
        this.language = language;

        this.patika = Patika.getFetch(patika_id);
        this.educator = User.getFetch(user_id);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPatika_id() {
        return patika_id;
    }

    public void setPatika_id(int patika_id) {
        this.patika_id = patika_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Patika getPatika() {
        return patika;
    }

    public void setPatika(Patika patika) {
        this.patika = patika;
    }

    public User getEducator() {
        return educator;
    }

    public void setEducator(User educator) {
        this.educator = educator;
    }

    public static ArrayList<Course> getList(){
        ArrayList<Course> courseList = new ArrayList<>();
        Course obj;
        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM course");
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                int user_id = resultSet.getInt("user_id");
                int patika_id = resultSet.getInt("patika_id");
                String name = resultSet.getString("name");
                String language = resultSet.getString("language");

                obj = new Course(id , user_id , patika_id , name , language);
                courseList.add(obj);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return courseList;
    }

    public static ArrayList<Course> getListByPatikaID(int patikaID){
        ArrayList<Course> courseList = new ArrayList<>();
        Course obj;
        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM course WHERE patika_id = " + patikaID);
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                int user_id = resultSet.getInt("user_id");
                int patika_id = resultSet.getInt("patika_id");
                String name = resultSet.getString("name");
                String language = resultSet.getString("language");

                obj = new Course(id , user_id , patika_id , name , language);
                courseList.add(obj);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return courseList;
    }


    public static boolean add(int user_id, int patika_id, String name, String language){
        String query = "INSERT INTO course (user_id , patika_id , name , language) VALUES (?,?,?,?)";
        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1, user_id);
            preparedStatement.setInt(2, patika_id);
            preparedStatement.setString(3, name);
            preparedStatement.setString(4, language);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static ArrayList<Course> getListByUser(int user_id){
        ArrayList<Course> courseList = new ArrayList<>();
        Course obj;
        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM course WHERE user_id = " + user_id);
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                int userID = resultSet.getInt("user_id");
                int patika_id = resultSet.getInt("patika_id");
                String name = resultSet.getString("name");
                String language = resultSet.getString("language");

                obj = new Course(id , userID , patika_id , name , language);
                courseList.add(obj);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return courseList;
    }

    public static boolean delete(int id){
        String query = "DELETE FROM course WHERE id = ?";
        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1,id);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static boolean deleteByPatikaID(int patikaID){
        String query = "DELETE FROM course WHERE patika_id = ?";
        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1,patikaID);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static boolean deleteByEducatorID(int educatorID){
        String query = "DELETE FROM course WHERE user_id = ?";
        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1,educatorID);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static Course getFetch(int id){
        Course obj = null;
        String query = "SELECT * FROM course WHERE id=?";
        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1 , id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                //(int id, int user_id, int patika_id, String name, String language)
                obj = new Course(resultSet.getInt("id") , resultSet.getInt("user_id") ,
                resultSet.getInt("patika_id") , resultSet.getString("name"), resultSet.getString("language"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return obj;
    }

    public static Course getFetchByEducatorID(int educatorID){
        Course obj = null;
        String query = "SELECT * FROM course WHERE user_id=?";
        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1 , educatorID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                //(int id, int user_id, int patika_id, String name, String language)
                obj = new Course(resultSet.getInt("id") , resultSet.getInt("user_id") ,
                        resultSet.getInt("patika_id") , resultSet.getString("name"), resultSet.getString("language"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return obj;
    }

    public static Course getFetchByPatikaID(int patikaID){
        Course obj = null;
        String query = "SELECT * FROM course WHERE patika_id=?";
        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1 , patikaID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                //(int id, int user_id, int patika_id, String name, String language)
                obj = new Course(resultSet.getInt("id") , resultSet.getInt("user_id") ,
                        resultSet.getInt("patika_id") , resultSet.getString("name"), resultSet.getString("language"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return obj;
    }


    public static Course getByPatikaID(int patika_id){
        Course obj = null;
        String query = "SELECT * FROM course WHERE patika_id = ?";
        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1 , patika_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                //(int id, int user_id, int patika_id, String name, String language)
                obj = new Course(resultSet.getInt("id") , resultSet.getInt("user_id") ,
                        resultSet.getInt("patika_id") , resultSet.getString("name"), resultSet.getString("language"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return obj;
    }


}
