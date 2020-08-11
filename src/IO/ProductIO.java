package IO;

import Models.Product;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ProductIO {

    List<Product> readProductsFrom() throws FileNotFoundException, SQLException;

    void changeStockBalanceIn(Product product, Integer stockBalance) throws IOException, SQLException;

    // Product getById(Long id);
    // Long create(Product product);
    // void modify(Product product);
    // void modify(Long id, String fieldName, Object value);
    // void modifyBalance(Product product, Integer newBalance);
    // List<Product> listAll();
    // void remove(Long id);
    // void remove(Product product);
    // List<Long> create(List<Product> products);

}
