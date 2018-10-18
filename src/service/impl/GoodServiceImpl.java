package service.impl;

import dao.filestore.DaoByFile;
import entity.Good;
import service.GoodService;

import java.util.List;

public class GoodServiceImpl implements GoodService {
    private DaoByFile dao = new DaoByFile();

    public boolean load(){
        return dao.load();
    }

    public boolean save(){
        return dao.save();
    }

    @Override
    public boolean add(Good good) {
        return dao.add(good);
    }

    @Override
    public Good search(int id) {
        List<Good> list = dao.getAll();
        for (Good good : list) {
            if(id == good.getId()){
                return good;
            }
        }
        return null;
    }

    @Override
    public Good search(String name) {
        List<Good> list = dao.getAll();
        for (Good good : list) {
            if(good.getName().equals(name)){
                return good;
            }
        }
        return null;
    }

    @Override
    public boolean delete(int id) {
        Good goodT = search(id);
        return dao.delete(goodT);
    }

    @Override
    public Good modify(int id, Good good) {
        return null;
    }
}
