package entity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Mr.Zhang
 */
public class Good implements Serializable {
    private int id;
    private String name;
    private int stock;
    private double price;
    private Date time;
    private String manufactor;
    private String supplier;
    private double value;

    public Good() {
    }

    public Good(int id, String name, int stock, double price , String supplier, String manufactor, Date time) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.supplier = supplier;
        this.manufactor = manufactor;
        this.time = time;
        this.value = stock * price;
    }

    public int getId() {
        return id;
    }

    public Good setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Good setName(String name) {
        this.name = name;
        return this;
    }

    public int getStock() {
        return stock;
    }

    public Good setStock(int stock) {
        this.stock = stock;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public Good setPrice(int price) {
        this.price = price;
        return this;
    }

    public Date getTime() {
        return time;
    }

    public Good setTime(Date time) {
        this.time = time;
        return this;
    }

    public String getManufactor() {
        return manufactor;
    }

    public Good setManufactor(String manufactor) {
        this.manufactor = manufactor;
        return this;
    }

    public String getSupplier() {
        return supplier;
    }

    public Good setSupplier(String supplier) {
        this.supplier = supplier;
        return this;
    }

    public double getValue() {
        return value;
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override
    public String toString() {
        return  "货品号：" + id +
                ", 货品名：'" + name + '\'' +
                ", 库存：" + stock +
                ", 价格：" + price  +
                ", 供应商：'" + supplier + '\'' +
                ", 生产商：'" + manufactor + '\'' +
                ", 入库时间：'" + sdf.format(getTime()) + '\'';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Good)) return false;
        Good good = (Good) o;
        return id == good.id &&
                stock == good.stock &&
                price == good.price &&
                Objects.equals(name, good.name) &&
                Objects.equals(time, good.time) &&
                Objects.equals(manufactor, good.manufactor) &&
                Objects.equals(supplier, good.supplier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, stock, price, time, manufactor, supplier);
    }


}

