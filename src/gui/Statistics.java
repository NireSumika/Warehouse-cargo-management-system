package gui;

import entity.Good;
import service.GoodService;
import service.impl.GoodServiceImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.List;

class Statistics extends JFrame {
    //获取屏幕大小的工具
    private static Toolkit kit = Toolkit.getDefaultToolkit();

    private GoodService GS;

    void setGSI(GoodService GS){
        this.GS = GS;
        sort();
    }

    //表格组件
    private JScrollPane tablePane = new JScrollPane();
    private JTable table = new JTable();
    Statistics(){
        //窗口基本属性
        setTitle("统计并排序");
        setSize(900, 463);
        int x = (kit.getScreenSize().width - getWidth()) / 2;
        int y = (kit.getScreenSize().height - getHeight()) / 2;
        setLocation(x, y);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.component();
    }

    private void component() {
        //取消布局
        setLayout(null);

        //表格
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(true);
        table.setEnabled(false);
        tablePane.setBounds(10,50,880,333);
        tablePane.setViewportView(table);
        add(tablePane);

        //表格设置
        table.setRowHeight(30);
        table.getTableHeader().setPreferredSize(new Dimension(0,30));
        table.getTableHeader().setFont(new Font("仿宋",Font.BOLD,13));
    }

    private void getAllGoods(List<Good> list){
        //表格头
        String[] thead = {"货品号/id","货品名","库存","单价","供应商","生产商","进货日期","库存总价"};
        String[][] tbody = list2Array(list);
        TableModel dataModel = new DefaultTableModel(tbody,thead);
        table.setModel(dataModel);
    }

    private String[][] list2Array(List<Good> list){
        String[][] tbody = new String[list.size()][8];
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
            tbody[i][7] = String.format("%.1f",good.getValue());
        }
        return tbody;
    }

    private void sort(){
        List<Good> list = GS.getAll();
        list.sort(new Comparator<Good>() {
            @Override
            public int compare(Good good, Good t1) {
                return -Double.compare(good.getValue(),t1.getValue());
            }
        });
        getAllGoods(list);
    }



}
