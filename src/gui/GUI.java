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
import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.TableRowSorter;

import entity.Good;
import service.impl.GoodServiceImpl;

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
    private JButton random = new JButton("随机生成");


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
        add(random).setBounds(330,470,100,25);

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
                //
            }
        });

        deleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                delete();
                searchNumberText.setText("");
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
                statisticsAll();
            }
        });

        statisticsLessBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                statisticsLess();
                statisticsText.setText("");
            }
        });
        random.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                randomCreate();
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
            if(addNumber <= 0) {
                JOptionPane.showMessageDialog(this,"请输入正确的货品id！");
                return;
            }
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this,"请输入正确的货品id！");
            return;
        }

        String addName = addNameText.getText();
        for (int i = addName.length();--i>=-1;) {
            if (i < 0) {
                JOptionPane.showMessageDialog(this,"请输入货品名或货品名不能为纯数字！");
                return;
            }
            if (!Character.isDigit(addName.charAt(i))) {
                break;
            }
        }

        int addStock;
        try {
            addStock = Integer.parseInt(addstockText.getText());
            if(addStock <= 0) {
                JOptionPane.showMessageDialog(this,"请输入正确的库存量！");
                return;
            }
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(this,"请输入正确的库存量！");
            return;
        }

        double addPrice;
        try{
            addPrice = Double.parseDouble(addPriceText.getText());
            if(addPrice <= 0) {
                JOptionPane.showMessageDialog(this,"请输入正确的价格！");
                return;
            }
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
        }catch (NullPointerException e){
            JOptionPane.showMessageDialog(this,"该名称的货品已存在，请勿重复添加！");
        }

    }

    private void search(){
        //数据合法性检测
        String name;
        name = searchNumberText.getText();
        int id;
        try{
            id = Integer.parseInt(name);
            if(id <= 0) {
                JOptionPane.showMessageDialog(this,"请输入正确的货品id！");
                return;
            }
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
        int id;
        try{
            id = Integer.parseInt(searchNumberText.getText());
            if(id <= 0){
                JOptionPane.showMessageDialog(this,"请输入正确的货品id！");
                return;
            }
            t = GSI.delete(id);
        } catch (NumberFormatException e){
            JOptionPane.showMessageDialog(this,"请输入正确的id!");
            return;
        }
        if(!t) JOptionPane.showMessageDialog(this,"货品不存在!");
        getAllGoods();
        searchNumberText.setText("");
    }

    private void statisticsAll(){
        Statistics st = new Statistics();
        st.setGSI(GSI);
        st.setVisible(true);



    }

    private void statisticsLess(){
        try{
            int num = Integer.parseInt(statisticsText.getText());
            getLessGoods(num);
            if(num <= 0){
                JOptionPane.showMessageDialog(this,"请输入一个正整数！");
                return;
            }
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this,"请输入正确的整数!");
        }

    }

    private void randomCreate(){
        int num = 0;
        try{
            num = Integer.parseInt(statisticsText.getText());
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(this,"请输入正确的正整数!");
            return;
        }
        if(num == 1){
            int id = new Random().nextInt(100+num)+1;
            String name = getRandomString(4);
            int stock = new Random().nextInt(500)+1;
            double price = new Random().nextInt(5000)/10.0;
            String supplier = getRandomString(5);
            String manufactor = getRandomString(6);
            addNumberText.setText(""+id);
            addNameText.setText(name);
            addstockText.setText(""+stock);
            addPriceText.setText(""+price);
            addsupplierText.setText(supplier);
            addmanufactorText.setText(manufactor);

            //GSI.add(new Good(id,name,stock,price,supplier,manufactor,new Date()));
            //getAllGoods();
        }else {
            for (int i = 0; i < num; i++) {
                int id = new Random().nextInt(100 + num) + 1;
                String name = getRandomString(4);
                int stock = new Random().nextInt(500) + 1;
                double price = new Random().nextInt(5000) / 10.0;

                String supplier = getRandomString(5);
                String manufactor = getRandomString(6);
                GSI.add(new Good(id, name, stock, price, supplier, manufactor, new Date()));
                getAllGoods();
            }
        }
    }

    //生成指定长度随机字符串
    private static String getRandomString(int length) {
        //定义一个字符串（A-Z，a-z，0-9）即62位；
        String str = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM";
        //由Random生成随机数
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        //长度为几就循环几次
        for (int i = 0; i < length; ++i) {
            //产生0-61的数字
            int number = random.nextInt(str.length());
            //将产生的数字通过length次承载到sb中
            sb.append(str.charAt(number));
        }
        //将承载的字符转换成字符串
        return sb.toString();
    }

    private void getAllGoods(){
        //表格头
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0);
        tableModel.setColumnIdentifiers(new Object[] { "货品号/id","货品名","库存","单价","供应商","生产商","进货日期" });

        List<Good> list = GSI.getAll();
        String[][] tbody = list2Array(list);
        for(String[] s :tbody){
            tableModel.addRow(s);
        }
        //TableModel dataModel = new DefaultTableModel(tbody,thead);
        Comparator<String> intComparator =  new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                return Integer.parseInt(s)-Integer.parseInt(t1);
            }
        };

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableModel);
        sorter.setComparator(0, intComparator);
        sorter.setComparator(2, intComparator);
        sorter.setComparator(3, new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
               if(Double.parseDouble(s)-Double.parseDouble(t1) > 0)return 1;
               else if(Double.parseDouble(s)-Double.parseDouble(t1) < 0) return -1;
               else return 0;
            }
        });
        sorter.setSortable(1, false);
        sorter.setSortable(4,false);
        sorter.setSortable(5, false);
        sorter.setSortable(6,false);

        table.setAutoCreateRowSorter(true);
        table.setModel(tableModel);
        table.setRowSorter(sorter);

    }

    private void getLessGoods(int less){
        //表格头
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0);
        tableModel.setColumnIdentifiers(new Object[] { "货品号/id","货品名","库存","单价","供应商","生产商","进货日期" });
        List<Good> list = GSI.getAll();
        List<Good> lesslist = new ArrayList<>();
        for (Good good : list) {
            if (good.getStock() < less) {
                lesslist.add(good);
            }
        }
        String[][] tbody = list2Array(lesslist);
        for(String[] s :tbody){
            tableModel.addRow(s);
        }
        Comparator<String> intComparator =  new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                return Integer.parseInt(s)-Integer.parseInt(t1);
            }
        };

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(tableModel);
        sorter.setComparator(0, intComparator);
        sorter.setComparator(2, intComparator);
        sorter.setComparator(3, new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                if(Double.parseDouble(s)-Double.parseDouble(t1) > 0)return 1;
                else if(Double.parseDouble(s)-Double.parseDouble(t1) < 0) return -1;
                else return 0;
            }
        });
        sorter.setSortable(1, false);
        sorter.setSortable(4,false);
        sorter.setSortable(5, false);
        sorter.setSortable(6,false);

        table.setAutoCreateRowSorter(true);
        table.setModel(tableModel);
        table.setRowSorter(sorter);
    }

    private String[][] list2Array(List<Good> list){
        String[][] tbody = new String[list.size()][7];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        for(int i = 0;i<list.size();i++) {
            Good good = list.get(i);
            tbody[i][0] = good.getId()+"";
            tbody[i][1] = good.getName();
            tbody[i][2] = good.getStock()+"";
            tbody[i][3] = String.format("%.1f",good.getPrice());
            tbody[i][4] = good.getSupplier();
            tbody[i][5] = good.getManufactor();
            tbody[i][6] = sdf.format(good.getTime());
        }
        return tbody;
    }

}
