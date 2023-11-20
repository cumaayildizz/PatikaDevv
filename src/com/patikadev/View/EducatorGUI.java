package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Helper.Item;
import com.patikadev.Model.*;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class EducatorGUI extends JFrame{
    private JPanel wrapper;
    private JTabbedPane pnl_contents_list;
    private JPanel pnl_top;
    private JButton btn_logout;
    private JTextField fld_contents_title;
    private JTextField fld_contents_explanation;
    private JTextField fld_contents_youtube_link;
    private JButton btn_contents_add;
    private JButton btn_contants_delete;
    private JScrollPane scrl_contents_list;
    private JTable tbl_contents_list;
    private JTextField fld_delete_contents;
    private JComboBox cmb_contents_course;
    private JComboBox cmb_contents_course_search;
    private JLabel lbl_welcome;
    private JPanel pnl_contennts_right;
    private JTextField fld_contents_title_search;
    private JTextField fld_exlapanation;
    private JButton btn_contents_search;
    private JPanel pnl_educator_quiz_list;
    private JScrollPane scrl_educator_quiz;
    private JTable tbl_quiz_list;
    private JComboBox cmb_quiz_course_name;
    private JComboBox cmb_quiz_contents_name;
    private JTextField fld_quiz_question;
    private JTextField fld_quiz_answer;
    private JButton btn_quiz_add;
    private JTextField fld_quiz_delete;
    private JButton btn_quiz_delete;
    private JComboBox cmb_quiz_course_search;
    private JComboBox cmb_quiz_contents_search;
    private JButton btn_quiz_search;
    private DefaultTableModel mdl_contents_list;
    private Object[] row_content_list;
    DefaultTableModel mdl_quiz_list;
    private Object[] row_quiz_list;
    private Educator educator;

    public EducatorGUI(Educator educator){
        this.educator = educator;
        add(wrapper);
        setSize(1000,500);
        setLocation(Helper.screenCenterPoint("x",getSize()) , Helper.screenCenterPoint("y" , getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(true);
        setVisible(true);

        lbl_welcome.setText("Welcome" + educator.getName());

        //ContentsModel
        mdl_contents_list = new DefaultTableModel(){
            //Bu metod sayesinde tablomuzdaki ID ve Course Name degerlerinde tablo uzarinden degisiklik yapilamaz
            @Override
            public boolean isCellEditable(int row, int column) {
                if ((column == 0) || (column == 1))
                    return false;

                return super.isCellEditable(row, column);
            }
        };
        Object[] col_contents_list = {"ID" , "Course Name" , "Title" , "Explanation" , "YouTube Link"};
        mdl_contents_list.setColumnIdentifiers(col_contents_list);
        row_content_list = new Object[col_contents_list.length];
        loadContentsModel();
        tbl_contents_list.setModel(mdl_contents_list);
        tbl_contents_list.getColumnModel().getColumn(0).setMaxWidth(50);
        tbl_contents_list.getColumnModel().getColumn(0).setMinWidth(20);
        tbl_contents_list.getTableHeader().setReorderingAllowed(false);

        //TABLODA TIKLADIGIMIZ YERIN ID DEGERINI SILME ISLEMININ YAPILDIGI KUTUCUGA YAZDIRDIGIMIZ METOD
        tbl_contents_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String selected_row = tbl_contents_list.getValueAt(tbl_contents_list.getSelectedRow() , 0).toString();
                fld_delete_contents.setText(selected_row);
            }catch (Exception ex){
            }
        });

        tbl_contents_list.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE){
                int id = Integer.parseInt(tbl_contents_list.getValueAt(tbl_contents_list.getSelectedRow(),0).toString());
                String contents_title = (tbl_contents_list.getValueAt(tbl_contents_list.getSelectedRow(),2).toString());
                String contents_explanation = (tbl_contents_list.getValueAt(tbl_contents_list.getSelectedRow(),3).toString());
                String contents_youtube_link = (tbl_contents_list.getValueAt(tbl_contents_list.getSelectedRow(),4).toString());

                if (Contents.update(id,contents_title,contents_explanation,contents_youtube_link)){
                    Helper.showMessage("done");
                    loadContentsModel();
                    loaQuizModel();
                    loadQuizContentsSearchCmbBox();
                    loadContentsQuizCmbBox();
                }else {
                    Helper.showMessage("error");
                }
            }
        });

        loadContentsCmbBox();
        loadContentsCmbBoxSearch();
        loadQuizCourseCmbBox();
        loadContentsQuizCmbBox();

        //ContentsModel#####

        //QuizModel
        mdl_quiz_list = new DefaultTableModel(){
            //Bu metod sayesinde tablomuzdaki ID ve Course Name degerlerinde tablo uzarinden degisiklik yapilamaz
            @Override
            public boolean isCellEditable(int row, int column) {
                if ((column == 0) || (column == 1) || (column == 2))
                    return false;

                return super.isCellEditable(row, column);
            }
        };

        Object[] col_quiz_list = {"ID" , "Course Name" , "Contents Title" , "Question" , "Answer"};
        mdl_quiz_list.setColumnIdentifiers(col_quiz_list);
        row_quiz_list = new Object[col_quiz_list.length];
        loaQuizModel();
        tbl_quiz_list.setModel(mdl_quiz_list);
        tbl_quiz_list.getColumnModel().getColumn(0).setMaxWidth(50);
        tbl_quiz_list.getColumnModel().getColumn(0).setMinWidth(20);
        tbl_quiz_list.getTableHeader().setReorderingAllowed(false);
        loadQuizCourseCmbBox();
        loadContentsQuizCmbBox();
        loadQuizContentsSearchCmbBox();
        loadQuizCourseSearchCmbBox();

        tbl_quiz_list.getModel().addTableModelListener(e -> {
            if (e.getType() == TableModelEvent.UPDATE){
                int id = Integer.parseInt(tbl_quiz_list.getValueAt(tbl_quiz_list.getSelectedRow(),0).toString());
                String quiz_question = (tbl_quiz_list.getValueAt(tbl_quiz_list.getSelectedRow(),3).toString());
                String quiz_answer = (tbl_quiz_list.getValueAt(tbl_quiz_list.getSelectedRow(),4).toString());
                if (Quiz.update(id, quiz_question ,quiz_answer)){
                    Helper.showMessage("done");
                    loaQuizModel();
                }else {
                    Helper.showMessage("error");
                }
            }
        });

        //TABLODA TIKLADIGIMIZ YERIN ID DEGERINI SILME ISLEMININ YAPILDIGI KUTUCUGA YAZDIRDIGIMIZ METOD
        tbl_quiz_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String selected_row = tbl_quiz_list.getValueAt(tbl_quiz_list.getSelectedRow() , 0).toString();
                fld_quiz_delete.setText(selected_row);
            }catch (Exception ex){
            }
        });

        //QuizModel###




        btn_logout.addActionListener(e -> {
            dispose();
            LoginGUI loginGUI = new LoginGUI();
        });

        btn_contents_add.addActionListener(e -> {
             Item courseItem = (Item) cmb_contents_course.getSelectedItem();
             if (Helper.isFieldEmpty(fld_contents_title) || Helper.isFieldEmpty(fld_contents_explanation) || Helper.isFieldEmpty(fld_contents_youtube_link)){
                 Helper.showMessage("fill");
             }else {
                 if (Contents.add(courseItem.getKey(), fld_contents_title.getText() ,fld_contents_explanation.getText() ,fld_contents_youtube_link.getText())){
                     Helper.showMessage("done");
                     loadContentsModel();
                     loaQuizModel();
                     loadContentsQuizCmbBox();
                     loadQuizContentsSearchCmbBox();
                 }else {
                     Helper.showMessage("error");
                 }
                 fld_contents_title.setText(null);
                 fld_contents_explanation.setText(null);
                 fld_contents_youtube_link.setText(null);
             }
        });


        btn_contants_delete.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_delete_contents)){
                Helper.showMessage("fill");
            }else {
                int fld_delete= Integer.parseInt(fld_delete_contents.getText());
                if (Contents.delete(fld_delete)){
                    Helper.showMessage("done");
                    Quiz.deleteByContentsID(fld_delete);
                    loadContentsModel();
                    loaQuizModel();
                    loadContentsQuizCmbBox();
                    loadQuizContentsSearchCmbBox();
                }else {
                    Helper.showMessage("error");
                }
            }

        });

        btn_quiz_add.addActionListener(e -> {
            Item courseItem = (Item) cmb_quiz_course_name.getSelectedItem();
            Item contentsItem = (Item) cmb_quiz_contents_name.getSelectedItem();
            if (Helper.isFieldEmpty(fld_quiz_question) || Helper.isFieldEmpty(fld_quiz_answer)){
                Helper.showMessage("fill");
            }else {
                if(Quiz.add(courseItem.getKey() , contentsItem.getKey() ,fld_quiz_question.getText() , fld_quiz_answer.getText())){
                    Helper.showMessage("done");
                    loaQuizModel();
                }else{
                    Helper.showMessage("error");
                }
                fld_quiz_question.setText(null);
                fld_quiz_answer.setText(null);
            }
        });

        btn_quiz_delete.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_quiz_delete)){
                Helper.showMessage("fill");
            }else {
                int fld_delete= Integer.parseInt(fld_quiz_delete.getText());
                if (Quiz.delete(fld_delete)){
                    Helper.showMessage("done");
                    loaQuizModel();
                }else {
                    Helper.showMessage("error");
                }
            }
        });

        btn_quiz_search.addActionListener(e -> {
            String courseName = cmb_quiz_course_search.getSelectedItem().toString();
            String ContentsTitle = cmb_quiz_contents_search.getSelectedItem().toString();
            String query = Quiz.searchQuery(educator.getId(),courseName,ContentsTitle);
            ArrayList<Quiz> searchQuiz = Quiz.searchQuizList(query);
            loaQuizModel(searchQuiz);
        });
        cmb_quiz_course_name.addActionListener(e -> {
            loadContentsQuizCmbBox();
        });
    }

    private void loaQuizModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_quiz_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Quiz obj : Quiz.getList()){

            i = 0;
            row_quiz_list[i++] = obj.getId();
            row_quiz_list[i++] = obj.getCourse().getName();
            row_quiz_list[i++] = obj.getContents().getTitle();
            row_quiz_list[i++] = obj.getQuiz_question();
            row_quiz_list[i++] = obj.getQuiz_answer();
            mdl_quiz_list.addRow(row_quiz_list);
        }
    }

    private void loaQuizModel(ArrayList<Quiz> list) {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_quiz_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for (Quiz obj : list){
            i = 0;
            row_quiz_list[i++] = obj.getId();
            row_quiz_list[i++] = obj.getCourse().getName();
            row_quiz_list[i++] = obj.getContents().getTitle();
            row_quiz_list[i++] = obj.getQuiz_question();
            row_quiz_list[i++] = obj.getQuiz_answer();
            mdl_quiz_list.addRow(row_quiz_list);
        }
    }

    //VERITABANINDAKI OBJELERI TABLOYA CEKME METODU
    private void loadContentsModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_contents_list.getModel();
        clearModel.setRowCount(0);

        int i;
        for (Contents obj : Contents.getList()){
            i = 0;
            row_content_list[i++] = obj.getId();
            row_content_list[i++] = obj.getCourse().getName();
            row_content_list[i++] = obj.getTitle();
            row_content_list[i++] = obj.getExplanation();
            row_content_list[i++] = obj.getYoutube_link();
            mdl_contents_list.addRow(row_content_list);
        }
    }

    public void loadContentsCmbBox(){
        cmb_contents_course.removeAllItems();
        for(Course obj : Course.getList()){
            cmb_contents_course.addItem(new Item(obj.getId(), obj.getName()));
        }
    }
    public void loadContentsCmbBoxSearch(){
        cmb_contents_course_search.removeAllItems();
        for(Course obj : Course.getList()){
            cmb_contents_course_search.addItem(new Item(obj.getId(), obj.getName()));
        }
    }
    public void loadQuizContentsSearchCmbBox(){
        cmb_quiz_contents_search.removeAllItems();
        for(Contents obj : Contents.getList()){
            cmb_quiz_contents_search.addItem(new Item(obj.getId(), obj.getTitle()));
        }
    }
    public void loadQuizCourseCmbBox(){
        cmb_quiz_course_name.removeAllItems();
        for (Course obj : Course.getList()){
            cmb_quiz_course_name.addItem(new Item(obj.getId(), obj.getName()));
        }
    }

    public void loadQuizCourseSearchCmbBox(){
        cmb_quiz_course_search.removeAllItems();
        for (Course obj : Course.getList()){
            cmb_quiz_course_search.addItem(new Item(obj.getId(), obj.getName()));
        }
    }

    public void loadContentsQuizCmbBox(){
        Item courseItem = (Item) cmb_quiz_course_name.getSelectedItem();
        cmb_quiz_contents_name.removeAllItems();
        cmb_quiz_contents_name.addItem(new Item(0,null));
        for (Contents obj : Contents.getListByCourseID(courseItem.getKey())){
            cmb_quiz_contents_name.addItem(new Item(obj.getId(), obj.getTitle()));
        }
    }

    /*public void loadQuizContentsCmbBox(){
        cmb_quiz_contents_name.removeAllItems();
        for (Contents obj : Contents.getList()){
            cmb_quiz_contents_name.addItem(new Item(obj.getId() , obj.getTitle()));
        }
    }*/

}



