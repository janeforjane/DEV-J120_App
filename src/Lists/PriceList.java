package Lists;

import Models.Order;
import Models.Product;

import java.util.ArrayList;
import java.util.List;

public class PriceList {

    private List<Product> products;

    public PriceList (List<Product> pr) {

        products = pr;
    }

    public void addProduct (Product product) {
        products.add(product);

    }

    public void deleteProduct (Product product) {
        products.remove(product);

    }

    public List <Product> showProductList () {// дублирует - не нужен
        return this.products;
    }

    public List<Product> getProducts() { //нужен ли этот метод
        return products;
    }
}
