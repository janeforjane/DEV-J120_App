package IO;

import Lists.OrderList;
import Models.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.Map;

public class OrderIOFromDB implements OrderIO {


    @Override
    public void writeOrdersAndOrderItemsIn(OrderList orderList) throws SQLException {

        Connection connection = DriverManager.getConnection
                ("jdbc:postgresql://localhost:5432/my_database", "user", "passw0rd");


        for(int i = 0; i < orderList.getOrders().size(); i++) {//цикл проходится по Order из OrderList


            PreparedStatement preparedStatementOrder = connection.prepareStatement
                    ("SELECT OrderId from my_database.new_schema.orderList WHERE OrderId = ?;");

            preparedStatementOrder.setInt(1, orderList.getOrders().get(i).getOrderId());

            ResultSet resultSetOrder = preparedStatementOrder.executeQuery();
            int tmp = 0;

            while (resultSetOrder.next()) {
                tmp = resultSetOrder.getInt("OrderId");
            }

            if (orderList.getOrders().get(i).getOrderId() != tmp) {

                Integer id = orderList.getOrders().get(i).getOrderId();
                String date = String.valueOf(orderList.getOrders().get(i).getOrderDate());
                int idPerson = 0;

                    PreparedStatement preparedStatementPerson = connection.prepareStatement
                            ("SELECT id from my_database.new_schema.persons where name =?;");

                    preparedStatementPerson.setString(1, String.valueOf(orderList.getOrders().get(i).getContactPerson().getName()));

                    ResultSet resultSetPerson = preparedStatementPerson.executeQuery();

                    while (resultSetPerson.next()) {
                        idPerson = resultSetPerson.getInt("id");
                    }

                int status = 0;

                    PreparedStatement preparedStatementStatus = connection.prepareStatement
                            ("SELECT id from my_database.new_schema.status where status =?;");

                    preparedStatementStatus.setString(1, String.valueOf(orderList.getOrders().get(i).getStatusOfOrder()));

                    ResultSet resultSetStatus = preparedStatementStatus.executeQuery();

                    while (resultSetStatus.next()) {
                        status = resultSetStatus.getInt("id");
                    }

                PreparedStatement updateStatement = connection.prepareStatement
                        ("INSERT INTO new_schema.orderList  (OrderID, orderdate, contactPerson,status) VALUES (?,?,?,?);");

                updateStatement.setInt(1, id);
                updateStatement.setString(2, date);
                updateStatement.setInt(3, idPerson);
                updateStatement.setInt(4, status);

//                System.out.println(" В табл OrderList " + id + " добавлен - " + updateStatement.executeUpdate());

                resultSetStatus.close();
                resultSetPerson.close();
                preparedStatementStatus.close();
                preparedStatementPerson.close();
                updateStatement.close();

                for (Map.Entry<Integer, OrderItem> entry : orderList.getOrders().get(i).getOrderItems().entrySet()) { //цикл проходится по OrderItems каждого Order из OrderList

//                Integer key = entry.getKey();
                    OrderItem value = entry.getValue(); //получаю объект строки заказа
//                String orderItem = value.getOrderItemToString();

                    Integer orderId = orderList.getOrders().get(i).getOrderId();
                    Integer ProductId = value.getProduct().getId();
                    Integer count = value.getCount();
                    Integer price = value.getPrice();

                    PreparedStatement updateStatementForOrderItems = connection.prepareStatement
                            ("INSERT INTO new_schema.orderItemList  (OrderId, ProductId, count, price) VALUES (?,?,?,?);");

                    updateStatementForOrderItems.setInt(1, orderId);
                    updateStatementForOrderItems.setInt(2, ProductId);
                    updateStatementForOrderItems.setInt(3, count);
                    updateStatementForOrderItems.setInt(4, price);

//                    System.out.println("В табл OrderItems " + orderId + " " + ProductId + "добавлен - " + updateStatementForOrderItems.executeUpdate());

                    updateStatementForOrderItems.close();

                }

            } else {
//                System.out.println("Заказ " + orderList.getOrders().get(i).getOrderId() + " уже есть в БД");
            }

            preparedStatementOrder.close();
            resultSetOrder.close();
        }
        connection.close();
    }

    @Override
    public void readOrdersFrom(OrderList orderList) throws SQLException {

        Connection connection = DriverManager.getConnection
                ("jdbc:postgresql://localhost:5432/my_database", "user", "passw0rd");

        Statement statementForOrderList = connection.createStatement();
        ResultSet resultSetForOrderList = statementForOrderList.executeQuery("SELECT OrderID, orderdate, contactPerson from new_schema.orderList;");

        while (resultSetForOrderList.next()) {

            int orderId = resultSetForOrderList.getInt("OrderID");

            String orderDate1 = resultSetForOrderList.getString("orderdate");
                LocalDate.parse(orderDate1);
                LocalDate date = LocalDate.parse(orderDate1);

            int idContactPerson =  resultSetForOrderList.getInt("contactPerson");
            Person person = null;

                PreparedStatement preparedStatementForPerson = connection.prepareStatement("SELECT name, numberphone, deliveryaddress from new_schema.persons where id = ?;");
                preparedStatementForPerson.setInt(1, idContactPerson);
                ResultSet resultSetForPerson = preparedStatementForPerson.executeQuery();

                while (resultSetForPerson.next()) {
                    String name = resultSetForPerson.getString("name");
                    String numberPhone = resultSetForPerson.getString("numberphone");
                    String deliveryAddress = resultSetForPerson.getString("deliveryaddress");

                    person = new Person(name, numberPhone, deliveryAddress);

                }

            Order order = new Order(orderId, date, Status.SHIP, person);

            orderList.addOrder(order);

            preparedStatementForPerson.close();
            resultSetForPerson.close();
        }

        statementForOrderList.close();
        resultSetForOrderList.close();

        Statement statementForOrderItemList = connection.createStatement();
        ResultSet resultSetForOrderItemList = statementForOrderItemList.executeQuery("SELECT OrderId, count, price, ProductId from new_schema.orderItemList;");


        while (resultSetForOrderItemList.next()) {

            int idOfOrder = resultSetForOrderItemList.getInt("OrderId");
                int countOfProducts = resultSetForOrderItemList.getInt("count");// количество продуктов в заказе
                int commonPriceOfProducts = resultSetForOrderItemList.getInt("price"); // стоимость
            int priceOfProduct = commonPriceOfProducts/countOfProducts;// цена продукта на момент заказа
            int productId = resultSetForOrderItemList.getInt("ProductId");
            Product product = null;
                PreparedStatement preparedStatementForProduct = connection.prepareStatement("SELECT id,name, color from new_schema.pricelist where id = ?;");
                preparedStatementForProduct.setInt(1,productId);
                ResultSet resultSetForProduct = preparedStatementForProduct.executeQuery();

                while (resultSetForProduct.next()) {
                    String nameOfProduct = resultSetForProduct.getString("name");
                    String color = resultSetForProduct.getString("color");

                    product = new Product(productId, nameOfProduct, color, priceOfProduct);

                }
            for (int i = 0; i < orderList.getOrders().size(); i++) {
                //цикл пробегает по коллекции и добавляет товары в нужные заказы
                if (orderList.getOrders().get(i).getOrderId().equals(idOfOrder)) {
                    orderList.getOrders().get(i).addItem(product,countOfProducts);
                }else {

                }
            }

            preparedStatementForProduct.close();
            resultSetForProduct.close();
        }
        statementForOrderItemList.close();
        resultSetForOrderItemList.close();
        connection.close();
    }
}
