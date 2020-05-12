package Models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class Product {

    private Integer id;
    private String name;
    private Integer price;
    private Integer stockBalance;

    private HashSet<Integer> ids = new HashSet<>();

    public Product(String name, Integer price, Integer stockBalance) {
//        if (id <= 0 || price <= 0 ){
//            System.out.println("ай ай ай");
//        }

        this.id = IdGenerator();
        this.name = name;
        this.price = price;
        this.stockBalance = stockBalance;
    }

    public void addIds (Integer id) {
        ids.add(id);

    }

    public Integer IdGenerator () { //вынести в отдельный метод
        Random random = new Random();
        Integer x = (random.nextInt(999 - 101) + 101);
//            ids.add(x);
        if (ids.contains(x) == true) {
            IdGenerator();
        }
        ids.add(x);
        return x;

    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getStockBalance() {
        return stockBalance;
    }

    @Override
    public String toString() {
        return "Models.Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
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
        return getPrice().equals(product.getPrice());
    }

    @Override
    public int hashCode() {
        int result = getId().hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getPrice().hashCode();
        return result;
    }
}
