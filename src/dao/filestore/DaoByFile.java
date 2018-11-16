package dao.filestore;

import dao.GoodDAO;
import entity.Good;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DaoByFile implements GoodDAO {

    private static ArrayList<Good> goodArrayList = new ArrayList<>();

    @Override
    public boolean add(Good good) throws IllegalArgumentException{
        for(Good g:goodArrayList){
            if(g.getId()==good.getId()){
                throw new IllegalArgumentException();
            }
        }
        return goodArrayList.add(good);
    }

    @Override
    public boolean delete(Good good) {
        return goodArrayList.remove(good);
    }

    @Override
    public List<Good> getAll() {
        return new ArrayList<>(goodArrayList);
    }


    @SuppressWarnings("unchecked")
    @Override
    public boolean load(File FILE_PATH) {
        try (
                FileInputStream fileInputStream = new FileInputStream(FILE_PATH);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)
        ) {
            //从文件获取信息
            goodArrayList = (ArrayList<Good>) objectInputStream.readObject();
        } catch (FileNotFoundException f) {
            JOptionPane.showMessageDialog(null,"文件不存在！");
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null,"文件打开失败！");
            throw new RuntimeException(e.getClass() + ":" + e.getMessage());
        }
        return true;
    }

    @Override
    public boolean save(File FILE_PATH) {
        try (
                FileOutputStream fileOutputStream = new FileOutputStream(FILE_PATH);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)
        ){
            //储存到文件
            objectOutputStream.writeObject(goodArrayList);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return true;
    }

}
