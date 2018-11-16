package service;

import entity.Good;

import java.util.List;

public interface GoodService {
    void add(Good good);
    List<Good> getAll();
    Good search(int id);
    Good search(String name);
    boolean delete(int id);

}
