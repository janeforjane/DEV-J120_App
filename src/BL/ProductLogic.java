package BL;

import Models.Product;

public class ProductLogic {

    void incrementBalance(Product product, Integer amount){
        product.setStockBalance(product.getStockBalance() + amount);

        // productio -> getByid ->
    }

    void decrementBalance(Product product, Integer amount) {}

}
