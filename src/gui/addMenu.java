package gui;

import javax.swing.*;

public class addMenu extends JFrame {
    public addMenu(){
        setTitle("添加货品");
        setBounds(500,250,700,500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel mainMenu = new JPanel();
        JButton add = new JButton();
        JButton search = new JButton();
        JTextField enterId = new JTextField();
        JTextField enterName = new JTextField();
        JTextField enterStock = new JTextField();
        JTextField enterPrice = new JTextField();
        JOptionPane seleteTime = new JOptionPane();
        JTextField enterManufactor = new JTextField();
        JTextField enterSupplier = new JTextField();
        add.setText("添加货品");
        search.setText("查找货品");

        mainMenu.add(add);
        mainMenu.add(search);
        add(mainMenu);


        setVisible(true);
    }
}
