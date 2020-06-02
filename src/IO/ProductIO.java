package IO;

import Models.Product;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface ProductIO {

    List<Product> readProductsFromFile() throws FileNotFoundException;

    void changeStockBalanceInFile(Product product, Integer stockBalance) throws IOException;

}
