package dao.filestore;

import dao.GoodDAO;
import entity.Good;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DaoByFile implements GoodDAO {

    private static final String FILE_PATH = "./Good";
    private ArrayList<Good> goodArrayList = new ArrayList<>();

    @Override
    public boolean add(Good good) {
        return goodArrayList.add(good);
    }

    @Override
    public boolean delete(Good good) {
        return goodArrayList.remove(good);
    }

    @Override
    public List<Good> getAll() {
        return List.copyOf(goodArrayList);
    }
    @SuppressWarnings("unchecked")
    @Override
    public boolean load() {
        try (
                FileInputStream fileInputStream = new FileInputStream(FILE_PATH);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)
        ) {
            //从文件获取信息
            goodArrayList = (ArrayList<Good>) objectInputStream.readObject();
        } catch (FileNotFoundException ignored) {
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e.getClass() + ":" + e.getMessage());
        }
        return true;
    }

    @Override
    public boolean save() {
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
