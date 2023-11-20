package com.patikadev.Model;

import com.patikadev.Helper.DBConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Quiz {
    private int id;
    private int qCourse_id;
    private int qContents_id;
    private String quiz_question;
    private String quiz_answer;

    private Course course;
    private Contents contents;

    public Quiz(int id, int qCourse_id, int qContents_id, String quiz_question ,String quiz_answer) {
        this.id = id;
        this.qCourse_id = qCourse_id;
        this.qContents_id = qContents_id;
        this.quiz_question = quiz_question;
        this.quiz_answer = quiz_answer;

        this.course = Course.getFetch(qCourse_id);
        this.contents = Contents.getFetch(qContents_id);
    }

    public Quiz(int id,  String quiz_question ,String quiz_answer) {
        this.id = id;
        this.quiz_question = quiz_question;
        this.quiz_answer = quiz_answer;

    }
    public Quiz() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getqCourse_id() {
        return qCourse_id;
    }

    public void setqCourse_id(int qCourse_id) {
        this.qCourse_id = qCourse_id;
    }

    public int getqContents_id() {
        return qContents_id;
    }

    public void setqContents_id(int qContents_id) {
        this.qContents_id = qContents_id;
    }


    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Contents getContents() {
        return contents;
    }

    public void setContents(Contents contents) {
        this.contents = contents;
    }

    public String getQuiz_question() {
        return quiz_question;
    }

    public void setQuiz_question(String quiz_question) {
        this.quiz_question = quiz_question;
    }

    public String getQuiz_answer() {
        return quiz_answer;
    }

    public void setQuiz_answer(String quiz_answer) {
        this.quiz_answer = quiz_answer;
    }

    //VERITABANINDAKI OBJELERI TUTTUGUMUZ arratlist
    public static ArrayList<Quiz> getList(){
        ArrayList<Quiz> quizList = new ArrayList<>();
        String query = "SELECT * FROM quiz";
        Quiz obj;
        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                int qCourse_id = resultSet.getInt("qCourse_id");
                int qContents_id = resultSet.getInt("qContents_id");
                String quiz_question = resultSet.getString("question");
                String quiz_answer = resultSet.getString("answer");

                obj = new Quiz(id , qCourse_id, qContents_id , quiz_question , quiz_answer);
                quizList.add(obj);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return quizList;
    }

    public static ArrayList<Quiz> getListByContents(int contentsID){
        ArrayList<Quiz> quizList = new ArrayList<>();
        String query = "SELECT * FROM quiz WHERE qContents_id = " + contentsID;
        Quiz obj;
        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                int qCourse_id = resultSet.getInt("qCourse_id");
                int qContents_id = resultSet.getInt("qContents_id");
                String quiz_question = resultSet.getString("question");
                String quiz_answer = resultSet.getString("answer");

                obj = new Quiz(id , qCourse_id, qContents_id , quiz_question , quiz_answer);
                quizList.add(obj);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return quizList;
    }


    public static boolean add(int qCourse_id, int qContents_id, String question , String answer){
        String query = "INSERT INTO quiz (qCourse_id , qContents_id , question , answer) VALUES (?,?,?,?)";
        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1, qCourse_id);
            preparedStatement.setInt(2, qContents_id);
            preparedStatement.setString(3, question);
            preparedStatement.setString(4, answer);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static Quiz getFetch (int id){
        Quiz obj = null;
        String query = "SELECT * FROM quiz WHERE id=?";
        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                obj = new Quiz();
                obj.setId(resultSet.getInt("id"));
                obj.setqCourse_id(resultSet.getInt("qCourse_id"));
                obj.setqContents_id(resultSet.getInt("qContents_id"));
                obj.setQuiz_question(resultSet.getString("question"));
                obj.setQuiz_answer(resultSet.getString("answer"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return obj;
    }

    public static boolean delete(int id){
        String query = "DELETE FROM quiz WHERE id = ?";
        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1,id);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static boolean deleteByContentsID(int qContents_id){
        String query = "DELETE FROM quiz WHERE qContents_id = ?";
        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1,qContents_id);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static boolean deleteByCourseId(int qCourse_id){
        String query = "DELETE FROM quiz WHERE qCourse_id = ?";
        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1,qCourse_id);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static boolean deleteByCourseID(int qCourse_id){
        String query = "DELETE FROM quiz WHERE qCourse_id = ?";
        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setInt(1,qCourse_id);
            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static boolean update(int id , String question , String answer ){
        String query = "UPDATE quiz SET question=?,answer=? WHERE id=?";
        try {
            PreparedStatement preparedStatement = DBConnector.getInstance().prepareStatement(query);
            preparedStatement.setString(1,question);
            preparedStatement.setString(2,answer);
            preparedStatement.setInt(3,id);

            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public static ArrayList<Quiz> searchQuizList(String query){
        ArrayList<Quiz> quizList = new ArrayList<>();
        Quiz obj;
        try {
            Statement statement = DBConnector.getInstance().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()){
                obj = new Quiz(resultSet.getInt("q_id"), resultSet.getInt("qCourse_id"), resultSet.getInt("qContents_id"), resultSet.getString("question"), resultSet.getString("answer") );

                quizList.add(obj);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return quizList;
    }

    /*public static String searchQuery(String qCourse_id,String qContents_id){
        String query = "SELECT * FROM quiz WHERE ";
        if (!qCourse_id.isEmpty()){
            query += " qCourse_id LIKE '{{qCourse_id}}'";
            query = query.replace("{{qCourse_id}}", qCourse_id);
        }
        if (!qContents_id.isEmpty()){
            query += " AND qContents_id LIKE '{{qContents_id}}'";
            query = query.replace("{{qContents_id}}", qContents_id);
        }
        return query;
    }
    public static String searchQuery(String qCourse_id,String qContents_id){
        String query = "SELECT * FROM quiz WHERE qCourse_id '{{qCourse_id}}' AND qContents_id LIKE '{{qContents_id}}'";
        query = query.replace("{{qCourse_id}}", qCourse_id);
        query = query.replace("{{qContents_id}}", qContents_id);
        return query;
    }*/

    public static String searchQuery(int educator_id , String qCourse_id,String qContents_id){
        String query = "SELECT * , q.id AS q_id, co.name as course_name," +
                " cs.title as title FROM quiz q , course co , contents cs WHERE co.user_id={{educator_id}} " +
                "AND q.qCourse_id=co.id AND q.qContents_id=cs.id AND co.name='{{qCourse_id}}' " +
                "AND cs.title='{{qContents_id}}'";
        System.out.println(query);
        query = query.replace("{{educator_id}}", Integer.toString(educator_id));
        query = query.replace("{{qCourse_id}}", qCourse_id);
        query = query.replace("{{qContents_id}}", qContents_id);
        return query;
    }


}
