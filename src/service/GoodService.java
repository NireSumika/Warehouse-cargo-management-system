package service;

import entity.Good;

public interface GoodService {
    boolean add(Good good);
    Good search(int id);
    Good search(String name);
    boolean delete(int id);
    Good modify(int id, Good good);

}
