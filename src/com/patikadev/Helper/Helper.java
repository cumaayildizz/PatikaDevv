package com.patikadev.Helper;

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.text.TableView;
import javax.swing.text.TableView.TableRow;
import java.awt.*;

public class Helper {

    //Cikan kutucugun tipini belirleyen fonksiyon
    public static void setLayout(){
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
            if ("Nimbus".equals(info.getName())){
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;
            }
        }
    }


    //Pencereyi ekranin ortasina yerlestirme metodu
    public static int screenCenterPoint(String axis , Dimension size){
        int point;
        switch (axis) {
            case "x" :
                point = (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
                break;
            case "y" :
                point = (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
                break;
            default :
                point = 0;
                break;
        }
        return point;
    }

    //Kutucuklarin icinin dolulugunu sorgulama metodu
    public static boolean isFieldEmpty(JTextField field){
        return field.getText().trim().isEmpty();
        //return field.getText().trim().length() == 0 ;
    }

    public static boolean isCmbBoxEmpty(JComboBox cmbBox){
        return cmbBox.getSelectedItem().equals(0);
        //return field.getText().trim().length() == 0 ;
    }

    //Pencere hata mesaji metodu
    public static void showMessage(String str){
        optionPageTR();
        String message;
        String title;
        switch (str){
            case "fill":
                message = "Please fill in all fields!";
                title = "Error";
                break;
            case "done" :
                message = "Transaction Successful";
                title = "Result";
                break;
            case "error" :
                message = "Incorrect operation";
                title = "Error!";
                break;
            default:
                message = str;
                title = "Message";
        }
        JOptionPane.showMessageDialog(null , message , title ,JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirm(String str){
        optionPageTR();
        String message;
        switch (str){
            case "sure" :
                message = "Are you sure you want to do this?";
                break;
            default:
                message = str;
        }
        return JOptionPane.showConfirmDialog(null , message , "Is it your final decision?" , JOptionPane.YES_NO_OPTION) == 0;
    }

    public static int getTableSelectedRow(JTable table , int index){
        return  Integer.parseInt(table.getValueAt(table.getSelectedRow(),index).toString());
    }

    //Pencere hata mesajindaki onaylama tusunu yapilandiran metod(Mevcutta Ok olarak gelir) ok --> OK
    public static void optionPageTR(){
        UIManager.put("OptionPane.okButtonText" , "OK");
        UIManager.put("OptionPane.yesButtonText" , "YES");
        UIManager.put("OptionPane.noButtonText" , "NO");
    }



}
