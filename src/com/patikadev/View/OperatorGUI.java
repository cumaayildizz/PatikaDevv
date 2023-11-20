package com.patikadev.View;

import com.patikadev.Helper.*;
import com.patikadev.Model.*;


import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class OperatorGUI extends JFrame {
    private JPanel wrapper;
    private JTabbedPane pnl_contents_list;
    private JLabel lbl_welcome;
    private JButton btn_logout;
    private JPanel pnl_user_list;
    private JScrollPane scrl_user_list;
    private JTable tbl_user_list;
    private JPanel pnl_user_form;
    private JTextField fld_user_name;
    private JTextField fld_user_uname;
    private JTextField fld_user_pass;
    private JComboBox cmb_user_type;
    private JButton btn_user_add;
    private JTextField fld_user_id;
    private JButton btn_user_delete;
    private JTextField fld_sh_user_name;
    private JTextField fld_sh_user_uname;
    private JComboBox cmb_sh_usr_type;
    private JButton btn_user_sh;
    private JPanel pnl_patika_list;
    private JScrollPane scrl_patika_list;
    private JTable tbl_patika_list;
    private JPanel pnl_patika_add;
    private JTextField fld_patika_name;
    private JButton btn_patika_add;
    private JScrollPane scrl_course_list;
    private JTable tbl_course_list;
    private JPanel pnl_course_list;
    private JPanel pnl_course_right;
    private JTextField fld_course_name;
    private JTextField fld_course_language;
    private JComboBox cmb_course_patika;
    private JComboBox cmb_course_user;
    private JButton btn_course_add;
    private JPanel welcome;
    private JScrollPane scrl_contents_list;
    private JTable tbl_contents_list;
    private JTextField fld_contents_delete;
    private JButton btn_contents_delete;
    private JPanel pnl_quiz_list;
    private JScrollPane scrl_quiz_list;
    private JTable tbl_quiz_list;
    private JTextField fld_quiz_delete;
    private JButton btn_quiz_delete;
    private JTextField fld_patika_delete;
    private JButton btn_patika_delete;
    private JTextField fld_course_delete;
    private JButton btn_course_delete;
    private DefaultTableModel mdl_user_list;
    private Object[] row_user_list;
    private DefaultTableModel mdl_patika_list;
    private Object[] row_patika_list;
    private JPopupMenu patikaPopupMenu;
    private DefaultTableModel mdl_course_list;
    private Object[] row_course_list;
    private DefaultTableModel mdl_contents_list;
    private Object[] row_contents_list;
    private DefaultTableModel mdl_quiz_list;
    private Object[] row_quiz_list;

    private final Operator operator;


    public OperatorGUI(Operator operator){
        this.operator = operator;

        add(wrapper);
        setSize(1000,500);
        int x = Helper.screenCenterPoint("x" , getSize());
        int y = Helper.screenCenterPoint("y" , getSize());
        setLocation(x , y);
        //Ekranı kapatma
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle(Config.PROJECT_TITLE);
        //Gorunurlugunu acık yapıyorum
        setVisible(true);

        lbl_welcome.setText( "Welcome  " + operator.getName());

        //ModelUserList
        mdl_user_list = new DefaultTableModel(){
            //Bu metod sayesinde tablomuzdaki ID degerlerinde tablo uzarinden degisiklik yapilamaz
            @Override
            public boolean isCellEditable(int row, int column) { //colon esit 0 ise disardan mudahale = false
                if (column == 0)
                    return false;

                return super.isCellEditable(row, column);
            }
        };


        Object[] col_user_list = {"ID" , "Name Surname" , "User Name" , "Password" , "User Type"};
        mdl_user_list.setColumnIdentifiers(col_user_list);
        //satirlarimi bu dizinin icerisinde olusacak. Dizi boyutu bizim kolon sayisi kadar olacak
        row_user_list = new Object[col_user_list.length];
        loadUserModel(); //Eleman eklemek icin olusturdugumuz metod
        tbl_user_list.setModel(mdl_user_list);
        tbl_user_list.getTableHeader().setReorderingAllowed(false); //sutunun siralamasinin degismesine izin verme!!
        tbl_user_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_user_list.getColumnModel().getColumn(0).setMinWidth(30);

        tbl_user_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                //tablomun istedigim noktasindaki kordinatini cagirmak icin kullandigimiz kod(getValueAt)
                //Cok boyutlu array mantigi ile calisir. Bana satirdaki 0 index degerli kolonumu getir ve yazdir!!
                String select_user_id = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow(), 0).toString();
                //Silme islemi yaptiktan sonra tablo yenilenmediginden program tablodaki silenen satirda takili kalip bulamiyor ve hata veriyor
                //Bunun onune gecmek icin try catch icine alip programin patlamasini engelliyoruz
                fld_user_id.setText(select_user_id);
            }catch (Exception exception){
                //System.out.println(exception.getMessage());
            }
        });


        //Tablo uzerindeki degisiklikleri veri tabanina kaydetmek icin yazdigimiz metod
        tbl_user_list.getModel().addTableModelListener(e -> {
            if(e.getType() == TableModelEvent.UPDATE){
                int user_id = Integer.parseInt(tbl_user_list.getValueAt(tbl_user_list.getSelectedRow() , 0).toString());
                String user_name = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow() , 1).toString();
                String user_uname = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow() , 2).toString();
                String user_pass = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow() , 3).toString();
                String user_type = tbl_user_list.getValueAt(tbl_user_list.getSelectedRow() , 4).toString();

                if (User.update(user_id,user_name,user_uname,user_pass,user_type)){
                    Helper.showMessage("done");
                }
                loadUserModel();
                loadEducatorCmbBox();
                loadCourseModel();
            }

        });
        //ModelUserList########################

        //ModelPatikaList
        patikaPopupMenu = new JPopupMenu();
        JMenuItem updateMenu = new JMenuItem("Update");
        JMenuItem deleteMenu = new JMenuItem("Delete");
        patikaPopupMenu.add(updateMenu);
        patikaPopupMenu.add(deleteMenu);

        updateMenu.addActionListener(e -> {
            int select_id = Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow() , 0).toString());
            UpdatePatikaGUI updateGUI = new UpdatePatikaGUI(Patika.getFetch(select_id));
            //Update islemi yaptiktan sonra patika tablosunu guncelledimiz metod override
            updateGUI.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) { //pencere kapandiktan sonra bu islemi yap
                    loadPatikaModel();
                    loadPatikaCmbBox();
                    loadCourseModel();
                }
            });
        });

        deleteMenu.addActionListener(e -> {
            if (Helper.confirm("sure")){
                int patika_id = Integer.parseInt(tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(), 0).toString());
                if (Patika.delete(patika_id)){
                    Helper.showMessage("done");
                    Course.deleteByPatikaID(patika_id);
                    Contents.deleteByCourseID(Course.getFetchByPatikaID(patika_id).getId());
                    Quiz.deleteByContentsID(Contents.getFetchByCourseID(Course.getFetchByPatikaID(patika_id).getId()).getId());
                    loadContentsModel();
                    loadPatikaModel();
                    loadQuizModel();
                    loadPatikaCmbBox();
                }else {
                    Helper.showMessage("error");
                }
            }

        });



        mdl_patika_list = new DefaultTableModel();
        Object[] col_patika_list = {"ID" , "Patika Adi"};
        mdl_patika_list.setColumnIdentifiers(col_patika_list);
        row_patika_list = new Object[col_patika_list.length];
        loadPatikaModel();
        tbl_patika_list.setModel(mdl_patika_list);
        tbl_patika_list.setComponentPopupMenu(patikaPopupMenu);
        tbl_patika_list.getTableHeader().setReorderingAllowed(false); // sutunlarin yerini oynatmaya izin vermiyoruz
        tbl_patika_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_patika_list.getColumnModel().getColumn(0).setMinWidth(30);

        tbl_patika_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String select_patika_id = tbl_patika_list.getValueAt(tbl_patika_list.getSelectedRow(), 0).toString();
                fld_patika_delete.setText(select_patika_id);
            }catch (Exception exception){
                //System.out.println(exception.getMessage());
            }
        });

        //Patika menusunde sag tikladigimiz yerin mavi yanmasi icin yaptigimiz ayarlama
        tbl_patika_list.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point point = e.getPoint();
                int selected_row = tbl_patika_list.rowAtPoint(point);
                tbl_patika_list.setRowSelectionInterval(selected_row,selected_row);
            }
        });
        //ModelPatikaList########################

        //ModelCourseList
        mdl_course_list = new DefaultTableModel();
        Object[] col_course_list = {"ID" , "Course Name" ,"Course Lenguage" , "Patika" , "Educator"};
        mdl_course_list.setColumnIdentifiers(col_course_list);
        row_course_list = new Object[col_course_list.length];
        loadCourseModel();
        tbl_course_list.setModel(mdl_course_list);
        tbl_course_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_course_list.getColumnModel().getColumn(0).setMinWidth(30);
        tbl_course_list.getTableHeader().setReorderingAllowed(false);
        tbl_course_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String select_course_id = tbl_course_list.getValueAt(tbl_course_list.getSelectedRow(), 0).toString();
                fld_course_delete.setText(select_course_id);
            }catch (Exception exception){
                //System.out.println(exception.getMessage());
            }
        });

        loadPatikaCmbBox();
        loadEducatorCmbBox();
        //ModelCourseList##########

        //ModelContentsList
        mdl_contents_list = new DefaultTableModel();
        Object[] col_contents_list = {"ID" , "Course Name" ,"Course Title" , "Explanation" , "YouTube Link"};
        mdl_contents_list.setColumnIdentifiers(col_contents_list);
        row_contents_list = new Object[col_contents_list.length];
        loadContentsModel();
        tbl_contents_list.setModel(mdl_contents_list);
        tbl_contents_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_contents_list.getColumnModel().getColumn(0).setMinWidth(30);
        tbl_contents_list.getTableHeader().setReorderingAllowed(false);

        tbl_contents_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String select_user_id = tbl_contents_list.getValueAt(tbl_contents_list.getSelectedRow(), 0).toString();
                fld_contents_delete.setText(select_user_id);
            }catch (Exception exception){
                //System.out.println(exception.getMessage());
            }
        });
        //ModelContentsList##########

        //ModelQuizList
        mdl_quiz_list = new DefaultTableModel();
        Object[] col_quiz_list = {"ID" , "Course Name" ,"Contents Title" , "Question" , "Answer"};
        mdl_quiz_list.setColumnIdentifiers(col_quiz_list);
        row_quiz_list = new Object[col_quiz_list.length];
        loadQuizModel();
        tbl_quiz_list.setModel(mdl_quiz_list);
        tbl_quiz_list.getColumnModel().getColumn(0).setMaxWidth(75);
        tbl_quiz_list.getColumnModel().getColumn(0).setMinWidth(30);
        tbl_quiz_list.getTableHeader().setReorderingAllowed(false);

        tbl_quiz_list.getSelectionModel().addListSelectionListener(e -> {
            try {
                String select_user_id = tbl_quiz_list.getValueAt(tbl_quiz_list.getSelectedRow(), 0).toString();
                fld_quiz_delete.setText(select_user_id);
            }catch (Exception exception){
                //System.out.println(exception.getMessage());
            }
        });
        //ModelQuizList##########

        btn_user_delete.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_user_id)){
                Helper.showMessage("fill");
            }else {
                if(Helper.confirm("sure")){
                    int user_id = Integer.parseInt(fld_user_id.getText());
                    if (User.delete(user_id)){
                        Helper.showMessage("done");
                        Course.deleteByEducatorID(user_id);
                        Contents.deleteByCourseID(Course.getFetchByEducatorID(user_id).getId());
                        Quiz.deleteByCourseID(Course.getFetchByEducatorID(user_id).getId());
                        loadUserModel(); //tablouu refreshledik
                        loadEducatorCmbBox();
                        loadCourseModel();
                        loadQuizModel();
                        loadContentsModel();
                        fld_user_id.setText(null);
                    }else {
                        Helper.showMessage("error");
                    }
                }
            }
        });

        btn_patika_delete.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_patika_delete)){
                Helper.showMessage("fill");
            }else {
                if(Helper.confirm("sure")){
                    int patika_id = Integer.parseInt(fld_patika_delete.getText());
                    if (Patika.delete(patika_id)){
                        Helper.showMessage("done");
                        Course.deleteByPatikaID(patika_id);
                        Contents.deleteByCourseID(Course.getFetchByPatikaID(patika_id).getId());
                        Quiz.deleteByContentsID(Contents.getFetchByCourseID(Course.getFetchByPatikaID(patika_id).getId()).getId());
                        loadContentsModel();
                        loadPatikaModel();
                        loadQuizModel();
                        loadPatikaCmbBox();
                        fld_contents_delete.setText(null);
                    }else {
                        Helper.showMessage("error");
                    }
                }
            }
        });

        btn_course_delete.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_course_delete)){
                Helper.showMessage("fill");
            }else {
                if(Helper.confirm("sure")){
                    int course_id = Integer.parseInt(fld_course_delete.getText());
                    if (Course.delete(course_id)){
                        Helper.showMessage("done");
                        Contents.deleteByCourseID(course_id);
                        Quiz.deleteByCourseId(course_id);
                        loadContentsModel();
                        loadQuizModel();
                        fld_course_delete.setText(null);
                    }else {
                        Helper.showMessage("error");
                    }
                }
            }
        });

        btn_contents_delete.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_contents_delete)){
                Helper.showMessage("fill");
            }else {
                if(Helper.confirm("sure")){
                    int contents_id = Integer.parseInt(fld_contents_delete.getText());
                    if (Contents.delete(contents_id)){
                        Helper.showMessage("done");
                        Quiz.deleteByContentsID(contents_id);
                        loadContentsModel();
                        loadQuizModel();
                        fld_contents_delete.setText(null);
                    }else {
                        Helper.showMessage("error");
                    }
                }
            }
        });

        btn_quiz_delete.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_quiz_delete)){
                Helper.showMessage("fill");
            }else {
                if(Helper.confirm("sure")){
                    int quiz_id = Integer.parseInt(fld_quiz_delete.getText());
                    if (Quiz.delete(quiz_id)){
                        Helper.showMessage("done");
                        loadQuizModel();
                        fld_quiz_delete.setText(null);
                    }else {
                        Helper.showMessage("error");
                    }
                }
            }
        });

        //Arama Butonu
        btn_user_sh.addActionListener(e -> {
            String name = fld_sh_user_name.getText();
            String uname = fld_sh_user_uname.getText();
            String type = cmb_sh_usr_type.getSelectedItem().toString();
            String query = User.searchQuery(name,uname,type);
            ArrayList<User> searchingUser = User.searchUserList(query);
            loadUserModel(searchingUser);
            //loadUserModel(User.searchUserList(query));
        });

        //LOGOUT tusuna basinca cikis yapabilmek icin yaptigimiz islem.
        btn_logout.addActionListener(e -> {
            dispose();
            LoginGUI loginGUI = new LoginGUI();
        });

        //Patika eklemek icin olusturdugumuz butonun ayarlari
        btn_patika_add.addActionListener(e -> {
            if (Helper.isFieldEmpty(fld_patika_name)){
                Helper.showMessage("fiil");
            }else {
                if(Patika.add(fld_patika_name.getText())){
                    Helper.showMessage("done");
                    loadPatikaModel();
                    loadPatikaCmbBox();
                    fld_patika_name.setText(null);
                }else {
                    Helper.showMessage("error");
                }
            }
        });
        btn_course_add.addActionListener(e -> {
            Item patikaItem = (Item) cmb_course_patika.getSelectedItem();
            Item userItem = (Item) cmb_course_user.getSelectedItem();
            if (Helper.isFieldEmpty(fld_course_name) || Helper.isFieldEmpty(fld_course_language)){
                Helper.showMessage("fill");
            }else {
                if(Course.add(userItem.getKey(), patikaItem.getKey() , fld_course_name.getText(), fld_course_language.getText())){
                    Helper.showMessage("done");
                    loadCourseModel();
                    fld_course_name.setText(null);
                    fld_course_language.setText(null);
                }else {
                    Helper.showMessage("error");
                }
            }
        });

        btn_user_add.addActionListener(e -> {
            if(Helper.isFieldEmpty(fld_user_name) || Helper.isFieldEmpty(fld_user_uname) ||
                    Helper.isFieldEmpty(fld_user_pass)){

                Helper.showMessage("fill");
            }else {
                if(User.add(fld_user_name.getText(),fld_user_uname.getText(),
                        fld_user_pass.getText(),cmb_user_type.getSelectedItem().toString())){
                    Helper.showMessage("done");
                    loadUserModel();
                    loadEducatorCmbBox();
                    fld_user_name.setText(null);
                    fld_user_uname.setText(null);
                    fld_user_pass.setText(null);
                }else{
                    Helper.showMessage("error");
                }
            }
        });

    }

    private void loadContentsModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_contents_list.getModel();
        clearModel.setRowCount(0);

        int i;
        for (Contents obj : Contents.getList()){
            i = 0;
            row_contents_list[i++] = obj.getId();
            row_contents_list[i++] = obj.getCourse().getName();
            row_contents_list[i++] = obj.getTitle();
            row_contents_list[i++] = obj.getExplanation();
            row_contents_list[i++] = obj.getYoutube_link();
            mdl_contents_list.addRow(row_contents_list);
        }
    }

    private void loadQuizModel() {
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

    private void loadCourseModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_course_list.getModel();
        clearModel.setRowCount(0);

        int i ;
        for (Course obj : Course.getList()){
            i = 0;
            row_course_list[i++] = obj.getId();
            row_course_list[i++] = obj.getName();
            row_course_list[i++] = obj.getLanguage();
            row_course_list[i++] = obj.getPatika().getName();
            row_course_list[i++] = obj.getEducator().getName();

            mdl_course_list.addRow(row_course_list);

        }
    }

    //PatikaTableRefresh
    private void loadPatikaModel() {
        DefaultTableModel clearModel = (DefaultTableModel) tbl_patika_list.getModel();
        clearModel.setRowCount(0);
        int i ;
        for (Patika obj : Patika.getList()){
            i = 0;
            row_patika_list[i++] = obj.getId();
            row_patika_list[i++] = obj.getName();
            mdl_patika_list.addRow(row_patika_list);

        }
    }

    public void loadUserModel(){
        //Asagidaki ilk kod ile tablomu getirdim. Ikinci kod ilede tablomu sifirladim. En son for loop ile yeni elemani ekledim
        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);
        int i;
        for ( User obj : User.getList()){
            i = 0;
            row_user_list[i++] = obj.getId();
            row_user_list[i++] = obj.getName();
            row_user_list[i++] = obj.getUname();
            row_user_list[i++] = obj.getPass();
            row_user_list[i++] = obj.getType();
            mdl_user_list.addRow(row_user_list);
        }
    }

    public void loadUserModel(ArrayList<User> list){
        //Asagidaki ilk kod ile tablomu getirdim. Ikinci kod ilede tablomu sifirladim. En son for loop ile yeni elemani ekledim
        DefaultTableModel clearModel = (DefaultTableModel) tbl_user_list.getModel();
        clearModel.setRowCount(0);
        for ( User obj : list){
            int i = 0;
            row_user_list[i++] = obj.getId();
            row_user_list[i++] = obj.getName();
            row_user_list[i++] = obj.getUname();
            row_user_list[i++] = obj.getPass();
            row_user_list[i++] = obj.getType();
            mdl_user_list.addRow(row_user_list);
        }
    }

    public void loadPatikaCmbBox(){
        cmb_course_patika.removeAllItems(); //bu box icerisindeki tum verileri kaldiriyoruz
        for ( Patika obj : Patika.getList()){
            cmb_course_patika.addItem(new Item(obj.getId(), obj.getName()));
        }
    }

    public void loadEducatorCmbBox(){
        cmb_course_user.removeAllItems(); //bu box icerisindeki tum verileri kaldiriyoruz
        for (User obj : User.getList()){
            if (obj.getType().equals("educator")){
                cmb_course_user.addItem(new Item(obj.getId() , obj.getName()));
            }
        }
    }

    /*
    public void loadEducatorCmbBoxx(){
        cmb_course_user.removeAllItems(); //bu box icerisindeki tum verileri kaldiriyoruz
        for (User obj : User.getListOnlyEducator()){
            cmb_course_user.addItem(new Item(obj.getId() , obj.getName()));
        }
    }
    */


    public static void main(String[] args) {
        Helper.setLayout();
        Operator op = new Operator();
        op.setId(1);
        op.setName("Mustafa Çetındağ");
        op.setPass("1234");
        op.setType("operator");
        op.setUname("mustafa");


        OperatorGUI opGUI = new OperatorGUI(op);


    }
}
