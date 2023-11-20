package com.patikadev.View;

import com.patikadev.Helper.Config;
import com.patikadev.Helper.Helper;
import com.patikadev.Model.*;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame{
    private JPanel wrapper;
    private JPanel wtop;
    private JPanel wbottom;
    private JTextField fld_user_name;
    private JTextField fld_user_pass;
    private JButton btn_login;
    private JButton btn_student_register;

    public LoginGUI(){
        add(wrapper);
        setSize(400,400);
        setLocation(Helper.screenCenterPoint("x",getSize()) , Helper.screenCenterPoint("y" , getSize()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        setResizable(false);
        setVisible(true);
        btn_login.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_user_name) || Helper.isFieldEmpty(fld_user_pass)){
                Helper.showMessage("fill");
            }else {
                User user = User.getFetch(fld_user_name.getText() , fld_user_pass.getText());
                if (user == null){
                    Helper.showMessage("User not found!");
                }else {
                    switch (user.getType()){
                        case "operator" :
                            OperatorGUI operatorGUI = new OperatorGUI(new Operator(user.getId() ,user.getName(), user.getUname(), user.getPass(),user.getType()));
                            break;
                        case "educator" :
                            EducatorGUI educatorGUI = new EducatorGUI(new Educator(user.getId() ,user.getName(), user.getUname(), user.getPass(),user.getType()));
                            break;
                        case "student" :
                            StudentGUI studentGUI = new StudentGUI(new Student(user.getId() ,user.getName(), user.getUname(), user.getPass(),user.getType()));
                            break;
                    }
                    dispose();
                }
            }
        });

        btn_student_register.addActionListener(e -> {
            RegisterGui registerGui = new RegisterGui();
        });
    }

    public static void main(String[] args) {
        Helper.setLayout();
        LoginGUI login = new LoginGUI();
    }
}
