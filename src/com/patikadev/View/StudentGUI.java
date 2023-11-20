package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Helper.Item;
import com.patikadev.Model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentGUI extends JFrame{
    private JPanel wrapperr;
    private JTabbedPane tab_student;
    private JButton logoutButton;
    private JScrollPane scrl_student_patika_register;
    private JTable tbl_student_course_register;
    private JLabel lbl_welcome;
    private JComboBox cmb_patika_list;
    private JComboBox cmb_course_list;
    private JButton btn_register_course;
    private JTextField fld_course_delete;
    private JButton btn_course_delete;
    private JPanel pnl_course_contents;
    private JScrollPane scrl_course_contents;
    private JTable tbl_student_course_contents;
    private JTextField fld_course_contents_remove;
    private JButton btn_course_contents_delete;
    private JTextField fld_srch_course_contents_course_name;
    private JTextField fld_srch_course_contents_contents_title;
    private JButton btn_srch_course_contents;
    private JComboBox cmb_contents_course_name;
    private JComboBox cmb_contents_title;
    private JButton btn_course_contents_add;
    private JComboBox cmb_contents_quiz_question;
    private JTextField fld_contents_quiz_answer;
    private JTextField fld_contents_comment;
    DefaultTableModel mdl_student_course_register_list;
    Object[] row_student_course_register_list;
    DefaultTableModel mdl_student_course_contents_list;
    Object[] row_student_course_contents_list;

    private Student student;

    public StudentGUI(Student student){
        this.student = student;
        add(wrapperr);
        setSize(1000,650);
        setLocation(Helper.screenCenterPoint("x",getSize()) , Helper.screenCenterPoint("y" , getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(true);
        setVisible(true);

        lbl_welcome.setText("Welcome" + student.getName());
        //CourseRegisterModel
        mdl_student_course_register_list = new DefaultTableModel(){
            //Bu metod sayesinde tablomuzdaki ID ve Course Name degerlerinde tablo uzarinden degisiklik yapilamaz
            @Override
            public boolean isCellEditable(int row, int column) {
                if ((column == 0) || (column == 1))
                    return false;

                return super.isCellEditable(row, column);
            }
        };
        Object[] col_student_course_register_list = {"ID" , "Patika Name" , "Course Name" , "Educator Name"};
        mdl_student_course_register_list.setColumnIdentifiers(col_student_course_register_list);
        row_student_course_register_list = new Object[col_student_course_register_list.length];
        loadCourseRegisterModel();
        loadPatikasCmbBox();
        tbl_student_course_register.setModel(mdl_student_course_register_list);
        tbl_student_course_register.getColumnModel().getColumn(0).setMaxWidth(50);
        tbl_student_course_register.getColumnModel().getColumn(0).setMinWidth(25);
        tbl_student_course_register.getTableHeader().setReorderingAllowed(false);

        //TABLODA TIKLADIGIMIZ YERIN ID DEGERINI SILME ISLEMININ YAPILDIGI KUTUCUGA YAZDIRDIGIMIZ METOD
        tbl_student_course_register.getSelectionModel().addListSelectionListener(e -> {
            try {
                String selected_row = tbl_student_course_register.getValueAt(tbl_student_course_register.getSelectedRow() , 0).toString();
                fld_course_delete.setText(selected_row);
            }catch (Exception ex){
            }
        });
        //####CourseRegisterModel

        //CourseContentsModel
        mdl_student_course_contents_list = new DefaultTableModel(){
            //Bu metod sayesinde tablomuzdaki ID ve Course Name degerlerinde tablo uzarinden degisiklik yapilamaz
            @Override
            public boolean isCellEditable(int row, int column) {
                if ((column == 0) || (column == 1))
                    return false;

                return super.isCellEditable(row, column);
            }
        };
        Object[] col_student_course_contents_list = {"ID"  , "Course Name" , "Contents Title" ,
                "Contents Questions" , "Student Answer" , "Student Comment"};
        mdl_student_course_contents_list.setColumnIdentifiers(col_student_course_contents_list);
        row_student_course_contents_list = new Object[col_student_course_contents_list.length];
        loadStudentAnswerModel();
        loadContentsCourseCmbBox();
        tbl_student_course_contents.setModel(mdl_student_course_contents_list);
        tbl_student_course_contents.getColumnModel().getColumn(0).setMaxWidth(50);
        tbl_student_course_contents.getColumnModel().getColumn(0).setMinWidth(25);
        tbl_student_course_contents.getTableHeader().setReorderingAllowed(false);

        //TABLODA TIKLADIGIMIZ YERIN ID DEGERINI SILME ISLEMININ YAPILDIGI KUTUCUGA YAZDIRDIGIMIZ METOD
        tbl_student_course_contents.getSelectionModel().addListSelectionListener(e -> {
            try {
                String selected_row = tbl_student_course_contents.getValueAt(tbl_student_course_contents.getSelectedRow() , 0).toString();
                fld_course_contents_remove.setText(selected_row);
            }catch (Exception ex){
            }
        });
        //####CourseContentsModel



        btn_register_course.addActionListener(e -> {
            Item patikaItem = (Item) cmb_patika_list.getSelectedItem();
            Item courseItem = (Item) cmb_course_list.getSelectedItem();
            int patikaID = patikaItem.getKey();
            int courseID = courseItem.getKey();

            if (StudentCourse.add(patikaID , courseID)){
                Helper.showMessage("done");
                loadCourseRegisterModel();
                loadContentsCourseCmbBox();
                loadQuizQuestionCmbBox();
                loadContentsTitleCmbBox();
            }else {
                Helper.showMessage("error");
            }

        });

        cmb_patika_list.addActionListener(e -> {
            loadCoursesCmbBox();
            cmb_course_list.setSelectedItem(0);
        });

        logoutButton.addActionListener(e -> {
            dispose();
            LoginGUI loginGUI = new LoginGUI();
        });
        btn_course_delete.addActionListener(e -> {
            int selected_course = Integer.parseInt(fld_course_delete.getText());
            if ( Helper.isFieldEmpty(fld_course_delete)){
               Helper.showMessage("fill");
            }else {
                StudentCourse.delete(selected_course);
                Helper.showMessage("done");
                StudentCourseContents.deleteByCourseID(selected_course);
                loadCourseRegisterModel();
                loadStudentAnswerModel();
                loadContentsCourseCmbBox();
                loadQuizQuestionCmbBox();
                loadContentsTitleCmbBox();
            }
        });

        cmb_contents_course_name.addActionListener(e -> {
            loadContentsTitleCmbBox();
            cmb_contents_title.addActionListener(ex ->{
                    loadQuizQuestionCmbBox();

            });

        });

        btn_course_contents_add.addActionListener(e -> {
            Item courseItem = (Item) cmb_contents_course_name.getSelectedItem();
            Item contentsItem = (Item) cmb_contents_title.getSelectedItem();
            Item quizItem = (Item) cmb_contents_quiz_question.getSelectedItem();
            int courseID = courseItem.getKey();
            int contentsID = contentsItem.getKey();
            int quizID = quizItem.getKey();
            String answer = fld_contents_quiz_answer.getText();
            String comment = fld_contents_comment.getText();

            if (Helper.isFieldEmpty(fld_contents_quiz_answer) && Helper.isFieldEmpty(fld_contents_comment) &&
            Helper.isCmbBoxEmpty(cmb_contents_course_name) && Helper.isCmbBoxEmpty(cmb_contents_title) &&
            Helper.isCmbBoxEmpty(cmb_contents_quiz_question)){
                Helper.showMessage("fiil");
            }else {
                if (StudentCourseContents.add(courseID , contentsID , quizID , answer , comment)){
                    Helper.showMessage("done");
                    loadStudentAnswerModel();
                }else {
                    Helper.showMessage("error");
                }
            }
            fld_contents_comment.setText(null);
            fld_contents_quiz_answer.setText(null);
        });

        btn_course_contents_delete.addActionListener(e -> {
            int selected_contents = Integer.parseInt(fld_course_contents_remove.getText());
            if (fld_course_contents_remove != null  && Helper.isFieldEmpty(fld_course_contents_remove)){
               Helper.showMessage("fiil");
            }else {
                StudentCourseContents.delete(selected_contents);
                Helper.showMessage("done");
                loadStudentAnswerModel();
                loadContentsCourseCmbBox();
                loadQuizQuestionCmbBox();
                loadContentsTitleCmbBox();
            }
        });

    }

    private void loadCourseRegisterModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_student_course_register.getModel();
        clearModel.setRowCount(0);
        int i;
        for (StudentCourse obj : StudentCourse.getList()){
            i = 0;
            row_student_course_register_list[i++] = obj.getId();
            row_student_course_register_list[i++] = Patika.getFetch(obj.getPatika_id()).getName();
            row_student_course_register_list[i++] = Course.getFetch(obj.getCourse_id()).getName();
            row_student_course_register_list[i++] = User.getFetch(Course.getFetch(obj.getCourse_id()).getUser_id()).getName();
            mdl_student_course_register_list.addRow(row_student_course_register_list);
        }
    }

    private void loadStudentAnswerModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_student_course_contents.getModel();
        clearModel.setRowCount(0);
        int i;
        for (StudentCourseContents obj : StudentCourseContents.getList()){
            //"ID"  , "Course Name" , "Contents Title", "Contents Questions" , "Student Answer" , "Student Comment"
            i = 0;
            row_student_course_contents_list[i++] = obj.getId();
            row_student_course_contents_list[i++] = Course.getFetch(obj.getCourse_id()).getName();
            row_student_course_contents_list[i++] = Contents.getFetchByCourseID(obj.getCourse_id()).getTitle();
            row_student_course_contents_list[i++] = Quiz.getFetch(obj.getQuiz_id()).getQuiz_question();
            row_student_course_contents_list[i++] =obj.getAnswer();
            row_student_course_contents_list[i++] =obj.getComment();
            mdl_student_course_contents_list.addRow(row_student_course_contents_list);
        }
    }

    public void loadPatikasCmbBox(){
        cmb_patika_list.removeAllItems();
        cmb_patika_list.addItem(new Item(0,null));
        for ( Patika obj : Patika.getList()){
            cmb_patika_list.addItem(new Item(obj.getId(), obj.getName()));
        }
    }
    public void loadCoursesCmbBox(){
        Item patikaItem = (Item) cmb_patika_list.getSelectedItem();
        cmb_course_list.removeAllItems();
        cmb_course_list.addItem(new Item(0,null));
        for ( Course obj : Course.getListByPatikaID(patikaItem.getKey())){
            cmb_course_list.addItem(new Item(obj.getId(), obj.getName()));
        }
    }

    public void loadContentsCourseCmbBox(){
        cmb_contents_course_name.removeAllItems();
        cmb_contents_course_name.addItem(new Item(0,null));
        for ( StudentCourse obj : StudentCourse.getList()){
            cmb_contents_course_name.addItem(new Item(obj.getId() , Course.getFetch(obj.getCourse_id()).getName()));
        }
    }

    public void loadContentsTitleCmbBox(){
        Item courseItem = (Item) cmb_contents_course_name.getSelectedItem();
        cmb_contents_title.removeAllItems();
        cmb_contents_title.addItem(new Item(0,null));
        for (Contents obj : Contents.getListByCourseID(courseItem.getKey())){
            cmb_contents_title.addItem(new Item(obj.getId(), obj.getTitle()));
        }
    }

    public void loadQuizQuestionCmbBox(){
        Item contentsItem = (Item) cmb_contents_title.getSelectedItem();
        cmb_contents_quiz_question.removeAllItems();
        cmb_contents_quiz_question.addItem(new Item(0,null));

            for (Quiz obj : Quiz.getListByContents(contentsItem.getKey())){
                cmb_contents_quiz_question.addItem(new Item(obj.getId(), obj.getQuiz_question()));
            }

    }



}
