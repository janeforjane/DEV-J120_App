package IO;

import Models.Product;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductIOFromDB implements ProductIO {

    List<Product> products = new ArrayList<>();

    @Override
    public List<Product> readProductsFrom() throws FileNotFoundException, SQLException {
// TODO: check fro products is empty, return cached

        if (products.size() < 1) {
            Connection connection = DriverManager.getConnection
                    ("jdbc:postgresql://localhost:5432/my_database", "user", "passw0rd");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id,name, color, price, stockBalance from new_schema.pricelist");


            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String color = resultSet.getString("color");
                int price = resultSet.getInt("price");
                int stockBalance = resultSet.getInt("stockBalance");
//            System.out.println("id: " + id + ", name: " + name + " color: " + color + " price: " + price);

                Product product = new Product(id, name, color, price, stockBalance);
                products.add(product);
            }

            resultSet.close();
            statement.close();
            connection.close();
        }
        return products;
    }

    @Override
    public void changeStockBalanceIn(Product product, Integer deleteFromStockBalance) throws IOException, SQLException {


        Connection connection = DriverManager.getConnection
                ("jdbc:postgresql://localhost:5432/my_database", "user", "passw0rd");

        PreparedStatement updateStatement = connection.prepareStatement("UPDATE new_schema.pricelist SET stockbalance = ? WHERE id = ?");
        updateStatement.setInt(1, product.getStockBalance() - deleteFromStockBalance);
        updateStatement.setInt(2, product.getId());

        int count = updateStatement.executeUpdate();
//        System.out.println("updated: " + count);

        updateStatement.close();
        connection.close();




    }
}
