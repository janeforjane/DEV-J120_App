import BL.OrderLogic;
import IO.OrderIOFromDB;
import IO.ProductIOFromDB;
import Lists.PriceList;
import Models.Order;
import Models.Person;

import java.io.IOException;
import java.sql.*;

public class Main {

    public static void main(String[] args) throws IOException, SQLException {

        //******13

        // first actions for initiate

//        firstDBLoad(); //первичное создание схемы в БД; создание таблиц

        PropertyService service = new FilePropertyService();//настройки для чтения и записи из файла

        OrderLogic orderLogic = new OrderLogic();

        // for DB

        ProductIOFromDB productIOFromDB = new ProductIOFromDB();
        orderLogic.setProductIO(productIOFromDB); // чтение товаров из БД
        PriceList priceList = new PriceList(productIOFromDB.readProductsFrom());// чтение из БД и запись в коллекцию
        OrderIOFromDB orderIO = new OrderIOFromDB();
        orderLogic.setOrderIO(orderIO); // запись заказов в БД

        // for files

//        ProductIOFromFile productIOFromFile = new ProductIOFromFile(); // чтение из файла
//        productIOFromFile.setPathToFile(service.getByName("ProductFilePath")); // чтение из файла
//        orderLogic.setProductIO(productIOFromFile);// чтение товаров из файла
//        PriceList priceList = new PriceList(productIOFromFile.readProductsFrom()); // чтение из файла
//        OrderIOFromFile orderIO = new OrderIOFromFile();
//        orderLogic.setOrderIO(orderIO); //запись заказов в файл
//        orderIO.setPathToFileOrders(service.getByName("OrdersFilePath"));
//        orderIO.setPathToFileOrderItems(service.getByName("OrderItemFilePath"));

        // loading orders from (DB or files)

        System.out.println("Заказов в коллекции - " + orderLogic.getOrderList().getOrders().size());
        orderIO.readOrdersFrom(orderLogic.getOrderList());// чтение заказов из хранилища и запись в коллекцию
        System.out.println("Уже оформленных заказов в коллекции - " + orderLogic.getOrderList().getOrders().size());



        System.out.println("******");
        System.out.println("Создание заказов");

        // create persons

        Person personWinnie = new Person("Winnie the Pooh", "123-45-67","A hole under an old oak tree");
        Person personPiglet = new Person("Piglet", "777-55-66","Little tree house on pine");
        Person personRabbit = new Person("Rabbit", "111-22-33","Nice house on the meadow");

        // create orders; add products in orders

        Order orderOfWinnie = new Order(personWinnie);
        orderOfWinnie.addItem(priceList.getProducts().get(0),3);
        orderOfWinnie.addItem(priceList.getProducts().get(2),1);

        Order orderOfPiglet = new Order(personPiglet);
        orderOfPiglet.addItem(priceList.getProducts().get(1),4);

        Order orderOfRabbit = new Order(personRabbit);
        orderOfRabbit.addItem(priceList.getProducts().get(2),2);
        orderOfRabbit.addItem(priceList.getProducts().get(1),1);

        // saving orders in storage (files or DB)

        orderLogic.saveOrderAndItems(orderOfWinnie);
        orderLogic.saveOrderAndItems(orderOfPiglet);
        orderLogic.saveOrderAndItems(orderOfRabbit);


        System.out.println("Общее количество заказов - " + orderLogic.getOrderList().getOrders().size());

        getInfoOfOrder(orderLogic,0);
        getInfoOfOrder(orderLogic,1);
        getInfoOfOrder(orderLogic,2);
        getInfoOfOrder(orderLogic,3);
        getInfoOfOrder(orderLogic,4);


    }

    public static void getInfoOfOrder (OrderLogic orderLogic, int indexOfOrder) {
        System.out.println(orderLogic.getOrderList().getOrders().get(indexOfOrder).getContactPerson().getName());
        System.out.println(orderLogic.getOrderList().getOrders().get(indexOfOrder).getOrderId());
        System.out.println(orderLogic.getOrderList().getOrders().get(indexOfOrder).getOrderItems().entrySet() + "\n");
    }

    public static void firstDBLoad () throws SQLException {

        Connection connection = DriverManager.getConnection
                ("jdbc:postgresql://localhost:5432/my_database", "user", "passw0rd");

        Statement createSchemaStatement = connection.createStatement();
        createSchemaStatement.executeUpdate
                ("CREATE SCHEMA new_schema;");

        Statement createStatusTableStatement = connection.createStatement();
        createStatusTableStatement.executeUpdate
                ("CREATE TABLE new_schema.status (ID SERIAL PRIMARY KEY not null, status  CHARACTER VARYING(30));");

        Statement createPricelistTableStatement = connection.createStatement();
        createPricelistTableStatement.executeUpdate
                ("CREATE TABLE new_schema.pricelist (ID SERIAL PRIMARY KEY,name CHARACTER VARYING(30),color CHARACTER VARYING(30),price NUMERIC,stockBalance INTEGER);");

        Statement createPersonsTableStatement = connection.createStatement();
        createPersonsTableStatement.executeUpdate
                ("CREATE TABLE new_schema.persons (ID SERIAL PRIMARY KEY not null,Name CHARACTER VARYING(30),NumberPhone CHARACTER VARYING(10), deliveryAddress CHARACTER VARYING(100));");

        Statement createOrderListTableStatement = connection.createStatement();
        createOrderListTableStatement.executeUpdate
                ("CREATE TABLE new_schema.orderList (OrderId SERIAL PRIMARY KEY,orderDate CHARACTER VARYING(30),contactPerson INTEGER references new_schema.persons (ID),status INTEGER references new_schema.status  (ID));");

        Statement createOrderItemListTableStatement = connection.createStatement();
        createOrderItemListTableStatement.executeUpdate
                ("CREATE TABLE new_schema.orderItemList (OrderId INTEGER references new_schema.orderList (OrderId),ProductId INTEGER references new_schema.pricelist (ID),count INTEGER,price NUMERIC);");


        createOrderItemListTableStatement.close();
        createOrderListTableStatement.close();
        createSchemaStatement.close();
        createPersonsTableStatement.close();
        createPricelistTableStatement.close();
        createStatusTableStatement.close();
        connection.close();

    }
}
