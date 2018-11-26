package service;

import entity.Good;

import java.io.File;
import java.util.List;

public interface GoodService {
    void add(Good good);
    List<Good> getAll();
    Good search(int id);
    Good search(String name);
    boolean delete(int id);
    boolean deleteAll();
    void load();
    void save();
    void load(File file);
}
