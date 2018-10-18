package dao;

import entity.Good;

import java.util.List;

public interface GoodDAO {
    boolean add(Good good);

    boolean delete(Good good);

    List<Good> getAll();

    boolean load();

    boolean save();

}
