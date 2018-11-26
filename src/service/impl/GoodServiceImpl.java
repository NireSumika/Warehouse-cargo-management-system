package service.impl;

import dao.GoodDAO;
import dao.filestore.DaoByFile;
import entity.Good;
import service.GoodService;

import java.io.File;
import java.util.List;

public class GoodServiceImpl implements GoodService {

    private GoodDAO dao;
    private File file;

    public GoodServiceImpl() {
        this(new DaoByFile());
    }

    public GoodServiceImpl(GoodDAO dao) {
        this.dao = dao;
    }

    public void load(File FILE_PATH){
        file = FILE_PATH;
        dao.load(FILE_PATH);
    }

    public void load(){
        file = new File("./Good");
        dao.load(file);
    }

    public void save(){
        dao.save(file);
    }

    public List<Good> getAll(){
        return dao.getAll();
    }

    @Override
    public void add(Good good) throws IllegalArgumentException, NullPointerException {
        dao.add(good);
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
    public boolean delete(int id) throws IllegalArgumentException{
        Good goodT = search(id);
        if(goodT == null) throw new IllegalArgumentException();
        else return dao.delete(goodT);

    }

    @Override
    public boolean deleteAll() {
        return dao.deleteAll();
    }
}
