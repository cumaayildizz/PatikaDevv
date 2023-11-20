package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class StudentCourse {
    private int id;
    private int patika_id;
    private int course_id;
    private String register;

    private Patika patika;
    private Course course;

    public StudentCourse(int id, int patika_id, int course_id) {
        this.id = id;
        this.patika_id = patika_id;
        this.course_id = course_id;

        this.patika = Patika.getFetch(patika_id);
        this.course = Course.getFetch(course_id);
    }
    public StudentCourse() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPatika_id() {
        return patika_id;
    }

    public void setPatika_id(int patika_id) {
        this.patika_id = patika_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }


    public Patika getPatika() {
        return patika;
    }

    public void setPatika(Patika patika) {
        this.patika = patika;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }


    public static ArrayList<StudentCourse> getList(){
        ArrayList<StudentCourse> studentCourseList = new ArrayList<>();
        StudentCourse obj;
        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM student_course");
            while (resultSet.next()){

                int id = resultSet.getInt("id");
                int patika_id = resultSet.getInt("patika_id");
                int course_id = resultSet.getInt("course_id");

                obj = new StudentCourse(id , patika_id , course_id );
                studentCourseList.add(obj);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return studentCourseList;
    }

    public static ArrayList<StudentCourse> getListByCourseID(int courseID){
        ArrayList<StudentCourse> studentCourseList = new ArrayList<>();
        StudentCourse obj;
        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM student_course WHERE course_id = " + courseID);
            while (resultSet.next()){

                int id = resultSet.getInt("id");
                int patika_id = resultSet.getInt("patika_id");
                int course_id = resultSet.getInt("course_id");

                obj = new StudentCourse(id , patika_id , course_id );
                studentCourseList.add(obj);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return studentCourseList;
    }

    public static StudentCourse getFetch(int course_id){
        StudentCourse obj = null;
        String query = "SELECT * FROM student_course WHERE course_id=?";
        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(3 , course_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                obj = new StudentCourse(resultSet.getInt("id") ,
                        resultSet.getInt("patika_id") ,
                        resultSet.getInt("course_id"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return obj;
    }


    public static boolean add(int patika_id , int course_id){
        String query = "INSERT INTO student_course (patika_id , course_id) VALUES (? , ?)";
        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1, patika_id);
            preparedStatement.setInt(2, course_id);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static boolean delete(int id){
        String query = "DELETE FROM student_course WHERE id = ?";
        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1,id);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static boolean deleteByPatikaID(int patika_id){
        String query = "DELETE FROM student_course WHERE patika_id = ? ";
        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1,patika_id);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }


}
