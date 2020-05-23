package Models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class Product {

    private Integer id;
    private String name;
    private String color;
    private Integer price;
    private Integer stockBalance;

//    private HashSet<Integer> ids = new HashSet<>();

    public Product(Integer id, String name, String color, Integer price, Integer stockBalance) {//с цветом
        if (id <= 0 || price <= 0 || stockBalance <= 0 ){
            System.out.println("ай ай ай");
        }

        this.id = id;
        this.name = name;
        this.color = color;
        this.price = price;
        this.stockBalance = stockBalance;
    }

    public Product(Integer id, String name, Integer price, Integer stockBalance) {//без цвета
        if (id <= 0 || price <= 0 || stockBalance <= 0 ){
            System.out.println("ай ай ай");
        }

        this.id = id;
        this.name = name;
        this.color = "";
        this.price = price;
        this.stockBalance = stockBalance;
    }

//    public void addIds (Integer id) {
//        ids.add(id);
//
//    }

    public void setStockBalance(Integer stockBalance) {
        this.stockBalance = stockBalance;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getStockBalance() {
        return stockBalance;
    }


    public String getProductToString() {
        return id +
                ";" + name +
                ";" + color +
                ";";
    }

    @Override
    public String toString() {
        return "Models.Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", color=" + color +
                ", price=" + price +
                ", stockBalance=" + stockBalance +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (!getId().equals(product.getId())) return false;
        if (!getName().equals(product.getName())) return false;
        if (!getColor().equals(product.getColor())) return false;
        return getPrice().equals(product.getPrice());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getColor().hashCode();
        result = 31 * result + getPrice().hashCode();
        return result;
    }

}
