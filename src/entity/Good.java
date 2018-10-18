package entity;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author Mr.Zhang
 */
public class Good implements Serializable {
    private int id;
    private String name;
    private int stock;
    private int price;
    private Data time;
    private String manufactor;
    private String supplier;

    public Good() {
    }

    public Good(int id, String name, int stock, int price, Data time, String manufactor, String supplier) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.time = time;
        this.manufactor = manufactor;
        this.supplier = supplier;
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

    public int getPrice() {
        return price;
    }

    public Good setPrice(int price) {
        this.price = price;
        return this;
    }

    public Data getTime() {
        return time;
    }

    public Good setTime(Data time) {
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

    @Override
    public String toString() {
        return "Good{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stock=" + stock +
                ", price=" + price +
                ", time=" + time +
                ", manufactor='" + manufactor + '\'' +
                ", supplier='" + supplier + '\'' +
                '}';
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

