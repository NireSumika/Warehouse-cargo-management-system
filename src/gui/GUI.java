package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.awt.*;
import javax.swing.JOptionPane;

import entity.Good;
import service.impl.GoodServiceImpl;

import java.util.List;

public class GUI extends JDialog{

    private GoodServiceImpl GSI = new GoodServiceImpl();

    //获取屏幕大小的工具
    private static Toolkit kit = Toolkit.getDefaultToolkit();

    //表格组件
    private JScrollPane tablePane = new JScrollPane();
    private JTable table = new JTable();

    //上方操作栏组件
    private JButton homeBtn = new JButton("首页");
    private JButton loadBtn = new JButton("导入文件");
    private JTextField searchNumberText = new JTextField(2);
    private JButton searchBtn = new JButton("通过id/名称查找");
    private JButton deleteBtn = new JButton("通过id删除");

    //下方操作栏组件
    private JButton statisticsAllBtn = new JButton("统计所有货品库存总价并排序");
    private JTextField statisticsText = new JTextField(2);
    private JButton statisticsLessBtn = new JButton("统计库存量小于该值的货品");

    //添加功能组件
    private JTextField addNumberText = new JTextField(2);
    private JTextField addNameText = new JTextField(6);
    private JTextField addPriceText = new JTextField(4);
    private JTextField addstockText = new JTextField(4);
    private JTextField addsupplierText = new JTextField(10);
    private JTextField addmanufactorText = new JTextField(10);
    private JButton addBtn = new JButton("添加货品");//添加按钮


    public GUI(){
        //窗口基本属性
        setTitle("仓库货物管理系统");
        setIconImage(kit.createImage("title.png"));
        setSize(800, 560);
        int x = (kit.getScreenSize().width - getWidth()) / 2;
        int y = (kit.getScreenSize().height - getHeight()) / 2;
        setLocation(x, y);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        GSI.load();
        getAllGoods();
        this.component();
        this.listener();
    }

    //窗口组件
    private void component(){
        //取消布局
        setLayout(null);

        //表格
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(true);
        table.setEnabled(false);
        tablePane.setBounds(50,70,700,333);
        tablePane.setViewportView(table);
        add(tablePane);

        //表格设置
        table.setRowHeight(30);
        table.getTableHeader().setPreferredSize(new Dimension(0,30));
        table.getTableHeader().setFont(new Font("仿宋",Font.BOLD,13));

        //按钮
        add(homeBtn).setBounds(50,25,90,25);
        add(loadBtn).setBounds(160,25,90,25);
        add(searchBtn).setBounds(490,25,130,25);
        add(deleteBtn).setBounds(640,25,108,25);
        add(addBtn).setBounds(660,425,88,25);
        add(statisticsAllBtn).setBounds(50,470,250,25);
        add(statisticsLessBtn).setBounds(550,470,200,25);

        //输入框
        add(searchNumberText).setBounds(350,26,120,25);
        add(addNumberText).setBounds(55,425,90,25);
        add(addNameText).setBounds(155,425,90,25);
        add(addstockText).setBounds(255,425,90,25);
        add(addPriceText).setBounds(355,425,90,25);
        add(addsupplierText).setBounds(455,425,90,25);
        add(addmanufactorText).setBounds(555,425,90,25);
        add(statisticsText).setBounds(455,470,80,25);

    }

    //监听器
    private void listener(){
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showConfirmDialog(GUI.this,"是否保存?","",JOptionPane.YES_NO_OPTION);
                if(confirm == JOptionPane.YES_OPTION) GSI.save();

            }
        });

        homeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                getAllGoods();
            }
        });

        loadBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int confirm = JOptionPane.showConfirmDialog(GUI.this, "是否保存当前文件?", "", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) GSI.save();
                load();
            }
        });

        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                search();
                searchNumberText.setText("");
            }
        });

        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                delete();
            }
        });

        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                add();
                addNumberText.setText("");
                addNameText.setText("");
                addstockText.setText("");
                addPriceText.setText("");
                addsupplierText.setText("");
                addmanufactorText.setText("");
            }
        });

        statisticsAllBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                staticticsAll();
            }
        });

        statisticsLessBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                staticticsLess();
                statisticsText.setText("");
            }
        });


    }

    private void load(){
        JFileChooser jfc = new JFileChooser();
        jfc.setMultiSelectionEnabled(false);
        jfc.showOpenDialog(this);
        File file = jfc.getSelectedFile();
        if (file == null) return;
        GSI.load(file);
    }

    private void add(){
        //数据合法性检测
        int addNumber;
        try {
            addNumber = Integer.parseInt(addNumberText.getText());
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this,"请输入正确的货品id！");
            return;
        }

        String addName = addNameText.getText();
        for (int i = addName.length();--i>=-1;) {
            if (i < 0) {
                JOptionPane.showMessageDialog(this,"货品名不能为纯数字！");
                return;
            }
            if (!Character.isDigit(addName.charAt(i))) {
                break;
            }
        }

        int addStock;
        try {
            addStock = Integer.parseInt(addstockText.getText());
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(this,"请输入正确的库存量！");
            return;
        }

        double addPrice;
        try{
            addPrice = Double.parseDouble(addPriceText.getText());
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(this,"请输入正确的价格！");
            return;
        }
        String addSupplier = addsupplierText.getText();
        String addManufactor = addmanufactorText.getText();
        Good good = new Good(addNumber, addName, addStock, addPrice, addSupplier, addManufactor,new Date());
        try{
            GSI.add(good);
            getAllGoods();
        }catch (IllegalArgumentException e){
            JOptionPane.showMessageDialog(this,"该id的货品已存在，请勿重复添加！");
        }

    }

    private void search(){
        //数据合法性检测
        String name;
        name = searchNumberText.getText();
        int id;
        try{
            id = Integer.parseInt(name);
            Good good = GSI.search(id);
            if(good == null) {
                JOptionPane.showMessageDialog(this,"货品不存在!");
            } else {
                JOptionPane.showMessageDialog(this,good.toString());
            }
        }catch (NumberFormatException e){
            Good good = GSI.search(name);
            if (good == null){
                JOptionPane.showMessageDialog(this,"货品不存在!");
            } else {
                JOptionPane.showMessageDialog(this,good.toString());
            }
        }
    }

    private void delete(){
        boolean t;
        try{
            t = GSI.delete(Integer.parseInt(searchNumberText.getText()));
        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(this,"请输入正确的id!");
            return;
        }
        if(!t) JOptionPane.showMessageDialog(this,"货品不存在!");
        getAllGoods();
        searchNumberText.setText("");
    }

    private void staticticsAll(){
        Statistics st = new Statistics();
        st.setGSI(GSI);
        st.setVisible(true);



    }

    private void staticticsLess(){
        try{
            getLessGoods(Integer.parseInt(statisticsText.getText()));
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this,"请输入正确的整数!");
        }

    }

    private void getAllGoods(){
        //表格头
        String[] thead = {"货品号/id","货品名","库存","单价","供应商","生产商","进货日期"};
        List<Good> list = GSI.getAll();
        String[][] tbody = list2Array(list);
        TableModel dataModel = new DefaultTableModel(tbody,thead);
        table.setModel(dataModel);
    }

    private void getLessGoods(int less){
        //表格头
        String[] thead = {"货品号/id","货品名","库存","单价","供应商","生产商","进货日期"};
        List<Good> list = GSI.getAll();
        List<Good> lesslist = new ArrayList<>();
        for (Good good : list) {
            if (good.getStock() < less) {
                lesslist.add(good);
            }
        }
        String[][] tbody = list2Array(lesslist);
        TableModel dataModel = new DefaultTableModel(tbody,thead);
        table.setModel(dataModel);
    }

    private String[][] list2Array(List<Good> list){
        String[][] tbody = new String[list.size()][7];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        for(int i = 0;i<list.size();i++) {
            Good good = list.get(i);
            tbody[i][0] = good.getId()+"";
            tbody[i][1] = good.getName();
            tbody[i][2] = good.getStock()+"";
            tbody[i][3] = good.getPrice() + "";
            tbody[i][4] = good.getSupplier();
            tbody[i][5] = good.getManufactor();
            tbody[i][6] = sdf.format(good.getTime());
        }
        return tbody;
    }

}
