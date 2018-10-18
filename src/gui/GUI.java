package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUI extends JFrame {
    public GUI(){
        setTitle("仓库货物管理系统");
        setBounds(500,250,700,500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel mainMenu = new JPanel();
        JLabel add = new JLabel();
        JLabel search = new JLabel();
        JLabel delete = new JLabel();
        JLabel statistic = new JLabel();
        add.setText("添加货品");
        search.setText("查找货品");
        delete.setText("删除货品");
        statistic.setText("货品统计");
        mainMenu.add(add);
        mainMenu.add(search);
        mainMenu.add(delete);
        mainMenu.add(statistic);
        add(mainMenu);
        add.setBounds(0,0,10,4);
        add.setLocation(0,0);

        add.setSize(10,5);
        search.setBounds(10,0,10,4);
        search.setLocation(10,0);
        search.setSize(10,5);
        add.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                new addMenu();
            }
        });









        setVisible(true);
    }
    private void addMenu(){

    }

}
