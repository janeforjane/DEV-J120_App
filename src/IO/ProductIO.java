package IO;

import Models.Product;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ProductIO {

    List<Product> readProductsFrom() throws FileNotFoundException, SQLException;

    void changeStockBalanceIn(Product product, Integer stockBalance) throws IOException, SQLException;

}
