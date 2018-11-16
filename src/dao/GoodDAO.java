package dao;

import entity.Good;

import java.io.File;
import java.util.List;

public interface GoodDAO {
    boolean add(Good good);

    boolean delete(Good good);

    List<Good> getAll();

    boolean load(File FILE_PATH);

    boolean save(File FILE_PATH);

}
