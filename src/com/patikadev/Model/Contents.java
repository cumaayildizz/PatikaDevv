package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Contents {
    private int id;
    private int course_id;
    private String title;
    private String explanation;
    private String youtube_link;

    private Course course;

    public Contents(int id, int course_id, String title, String explanation, String youtube_link) {
        this.id = id;
        this.course_id = course_id;
        this.title = title;
        this.explanation = explanation;
        this.youtube_link = youtube_link;

        this.course = Course.getFetch(course_id);
    }
    public Contents(int id, String title, String explanation, String youtube_link) {
        this.id = id;
        this.title = title;
        this.explanation = explanation;
        this.youtube_link = youtube_link;
    }

    public Contents() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getYoutube_link() {
        return youtube_link;
    }

    public void setYoutube_link(String youtube_link) {
        this.youtube_link = youtube_link;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    //VERITABANINDAKI OBJELERI TUTTUGUMUZ arratlist
    public static ArrayList<Contents> getList(){
        ArrayList<Contents> contentsList = new ArrayList<>();
        String query = "SELECT * FROM contents";
        Contents obj;
        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                int course_id = resultSet.getInt("course_id");
                String title = resultSet.getString("title");
                String explanation = resultSet.getString("explanation");
                String youtube_link = resultSet.getString("youtube_link");

                obj = new Contents(id , course_id, title , explanation , youtube_link);
                contentsList.add(obj);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return contentsList;
    }

    public static ArrayList<Contents> getListByCourseID(int courseID){
        ArrayList<Contents> contentsList = new ArrayList<>();
        String query = "SELECT * FROM contents WHERE course_id = " + courseID;
        Contents obj;
        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                int course_id = resultSet.getInt("course_id");
                String title = resultSet.getString("title");
                String explanation = resultSet.getString("explanation");
                String youtube_link = resultSet.getString("youtube_link");

                obj = new Contents(id , course_id, title , explanation , youtube_link);
                contentsList.add(obj);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return contentsList;
    }

    public static boolean update(int id , String title , String explanation , String youtube_link){
        String query = "UPDATE contents SET title=?,explanation=?,youtube_link=? WHERE id=?";

        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setString(1,title);
            preparedStatement.setString(2,explanation);
            preparedStatement.setString(3,youtube_link);
            preparedStatement.setInt(4,id);

            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }


    public static boolean add(int course_id, String title, String explanation , String youtube_link){
        String query = "INSERT INTO contents (course_id , title , explanation , youtube_link) VALUES (?,?,?,?)";
        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1, course_id);
            preparedStatement.setString(2, title);
            preparedStatement.setString(3, explanation);
            preparedStatement.setString(4, youtube_link);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static boolean delete(int id){
        String query = "DELETE FROM contents WHERE id = ?";
        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1,id);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static boolean deleteByCourseID(int course_id){
        String query = "DELETE FROM contents WHERE course_id = ?";
        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1,course_id);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static Contents getFetch(int id){
        Contents obj = null;
        String query = "SELECT * FROM contents WHERE id = ?";
        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                obj = new Contents();
                obj.setId(resultSet.getInt("id"));
                obj.setCourse_id(resultSet.getInt("course_id"));
                obj.setTitle(resultSet.getString("title"));
                obj.setExplanation(resultSet.getString("explanation"));
                obj.setYoutube_link(resultSet.getString("youtube_link"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return obj;
    }

    public static Contents getFetchByCourseID(int courseID){
        Contents obj = null;
        String query = "SELECT * FROM contents WHERE course_id = ?";
        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1,courseID);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                obj = new Contents();
                obj.setId(resultSet.getInt("id"));
                obj.setCourse_id(resultSet.getInt("course_id"));
                obj.setTitle(resultSet.getString("title"));
                obj.setExplanation(resultSet.getString("explanation"));
                obj.setYoutube_link(resultSet.getString("youtube_link"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return obj;
    }

}
