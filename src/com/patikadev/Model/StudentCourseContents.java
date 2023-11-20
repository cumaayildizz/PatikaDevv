package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class StudentCourseContents {
    private int id;
    private int course_id;
    private int contents_id;
    private int quiz_id;
    private String answer;
    private String comment;

    private Course course;
    private Quiz quiz;
    private  Contents contents;

    public StudentCourseContents(int id, int course_id, int contents_id, int quiz_id, String answer, String comment) {
        this.id = id;
        this.course_id = course_id;
        this.contents_id = contents_id;
        this.quiz_id = quiz_id;
        this.answer = answer;
        this.comment = comment;

        this.course = Course.getFetch(course_id);
        this.contents = Contents.getFetch(contents_id);
        this.quiz = Quiz.getFetch(quiz_id);
    }

    public StudentCourseContents() {
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

    public int getContents_id() {
        return contents_id;
    }

    public void setContents_id(int contents_id) {
        this.contents_id = contents_id;
    }

    public int getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Contents getContents() {
        return contents;
    }

    public void setContents(Contents contents) {
        this.contents = contents;
    }

    public static ArrayList<StudentCourseContents> getList(){
        ArrayList<StudentCourseContents> studentCourseContentsList = new ArrayList<>();
        StudentCourseContents obj;
        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM student_course_contents");
            while (resultSet.next()){

                int id = resultSet.getInt("id");
                int course_id = resultSet.getInt("course_id");
                int contents_id = resultSet.getInt("contents_id");
                int quiz_id = resultSet.getInt("quiz_id");
                String answer = resultSet.getString("answer");
                String comment = resultSet.getString("comment");

                obj = new StudentCourseContents(id , course_id , contents_id , quiz_id , answer , comment);
                studentCourseContentsList.add(obj);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return studentCourseContentsList;
    }



    public static boolean add(int course_id ,int contents_id , int quiz_id , String answer , String comment){
        String query = "INSERT INTO student_course_contents ( course_id , contents_id , quiz_id , answer , comment) " +
                "VALUES (? , ? , ? , ? , ?)";
        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1, course_id);
            preparedStatement.setInt(2, contents_id);
            preparedStatement.setInt(3, quiz_id);
            preparedStatement.setString(4, answer);
            preparedStatement.setString(5, comment);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static boolean delete(int id){
        String query = "DELETE FROM student_course_contents WHERE id = ?";
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
        String query = "DELETE FROM student_course_contents WHERE course_id = ?";
        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1,course_id);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }





}
