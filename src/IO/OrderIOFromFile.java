package IO;

import Lists.OrderList;
import Models.*;

import java.io.*;
import java.time.LocalDate;
import java.util.Map;
import java.util.Scanner;

public class OrderIOFromFile implements OrderIO {

    private String pathToFileOrders;
    private String pathToFileOrderItems;
    //**
    

    public void setPathToFileOrders(String pathToFile) {
        this.pathToFileOrders = pathToFile;
    }
    public void setPathToFileOrderItems(String pathToFile) {
        this.pathToFileOrderItems = pathToFile;
    }


    @Override
    // использован в старом saveOrder
//    public void writeOrdersIn(OrderList orderList) throws IOException {
//
//        StringBuilder sb = new StringBuilder();
//
//        FileOutputStream fileOutputStream = new FileOutputStream(pathToFileOrders);
//        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
//
//
//        for(int i = 0; i < orderList.getOrders().size(); i++) {//цикл проходится по Order из OrderList
////            System.out.println(" В элементе списка List " + i + " лежит " + orderList.getOrders().get(i));
//
//            String id = String.valueOf(orderList.getOrders().get(i).getOrderId());
//            String date = String.valueOf(orderList.getOrders().get(i).getOrderDate());
//            String person = orderList.getOrders().get(i).getContactPerson().toString();
//
////            sb.append(i+1).append("\n");
//            sb.append(id).append(";");
//            sb.append(date).append(";");
//            sb.append(person).append(";");
//
//            sb.append("\n");
//
//
//            for (Map.Entry<Integer, OrderItem> entry : orderList.getOrders().get(i).getOrderItems().entrySet()) { //цикл проходится по OrderItems каждого Order из OrderList
//
//                Integer key = entry.getKey();
//                OrderItem value = entry.getValue(); //получаю объект строки заказа
//                String orderItem = value.getOrderItemToString();
//
//                sb.append("Строка заказа").append(";").append(orderItem).append(";\n");
//
//
//
//            }
//
////            sb.append("\n");
//
//
//        }
//
//        String s = sb.toString();
//
//        bufferedOutputStream.write(s.getBytes());
//        bufferedOutputStream.close();
//
//    }

    public void writeOrdersAndOrderItemsIn(OrderList orderList) throws IOException {

        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();

        FileOutputStream fileOutputStream = new FileOutputStream(pathToFileOrders);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

        FileOutputStream fileOutputStream2 = new FileOutputStream(pathToFileOrderItems);
        BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(fileOutputStream2);


        for(int i = 0; i < orderList.getOrders().size(); i++) {//цикл проходится по Order из OrderList


            String id = String.valueOf(orderList.getOrders().get(i).getOrderId());
            String date = String.valueOf(orderList.getOrders().get(i).getOrderDate());
            String person = orderList.getOrders().get(i).getContactPerson().toString();

//            sb.append(i+1).append("\n");
            sb.append(id).append(";");
            sb.append(date).append(";");
            sb.append(person).append(";");

            sb.append("\n");


            for (Map.Entry<Integer, OrderItem> entry : orderList.getOrders().get(i).getOrderItems().entrySet()) { //цикл проходится по OrderItems каждого Order из OrderList

                Integer key = entry.getKey();
                OrderItem value = entry.getValue(); //получаю объект строки заказа
                String orderItem = value.getOrderItemToString();

                sb2.append(orderList.getOrders().get(i).getOrderId()).append(";").append(orderItem).append(";\n");



            }

//            sb.append("\n");


        }

        String s = sb.toString();
        System.out.println(s);
        String s2 = sb2.toString();
        System.out.println(s2);

        bufferedOutputStream.write(s.getBytes());
        bufferedOutputStream2.write(s2.getBytes());
        bufferedOutputStream.close();


        bufferedOutputStream2.close();

    }

    public void readOrdersFrom(OrderList orderList) throws FileNotFoundException {

        FileInputStream fileInputStream = new FileInputStream(pathToFileOrders);
        Scanner scanner = new Scanner(fileInputStream);
        scanner.useDelimiter(";");

        FileInputStream fileInputStream2 = new FileInputStream(pathToFileOrderItems);
        Scanner scanner2 = new Scanner(fileInputStream2);
        scanner2.useDelimiter(";");

        while (scanner.hasNextLine()) {


            Integer orderId = scanner.nextInt();
            String orderDate1 = scanner.next();


            LocalDate.parse(orderDate1);

            LocalDate date = LocalDate.parse(orderDate1);

            String name = scanner.next();
            String numberPhone = scanner.next();
            String deliveryAddress = scanner.next();


            LocalDate orderDate = LocalDate.now();

            Person person = new Person(name, numberPhone, deliveryAddress);
            Order order = new Order(orderId, date, Status.SHIP, person);

            orderList.addOrder(order);

            scanner.nextLine();
        }

            while (scanner2.hasNextLine()) {

//                String s = scanner.next();
                Integer idOfOrder = scanner2.nextInt();
                Integer id = scanner2.nextInt();
                String nameOfProduct = scanner2.next();
                String color = scanner2.next();
                Integer countOfProducts = scanner2.nextInt();
                Integer commonPriceOfProducts = scanner2.nextInt();
                Integer priceOfProduct = commonPriceOfProducts/countOfProducts;

                Product product = new Product(id, nameOfProduct, color, priceOfProduct );

                for (int i = 0; i < orderList.getOrders().size(); i++) {
                    //цикл пробегает по коллекции и добавляет товары в нужные заказы
                    if (orderList.getOrders().get(i).getOrderId().equals(idOfOrder)) {
                        orderList.getOrders().get(i).addItem(product,countOfProducts);
                    }else {

                    }
                }

                scanner2.nextLine();
            }

    }

    public void readOrd (OrderList orderList) throws FileNotFoundException {

        FileInputStream fileInputStream = new FileInputStream(pathToFileOrders);
        Scanner scanner = new Scanner(fileInputStream);
        scanner.useDelimiter(";");
        scanner.next();

        while (scanner.hasNextLine()) {

//            String е = scanner.next();

            String tmp = scanner.next();
            System.out.println(tmp);

//            Integer orderId = scanner.nextInt();
            Integer orderId = Integer.parseInt(tmp);
            String orderDate1 = scanner.next();

//            LocalDate.parse(orderDate1);

            String name = scanner.next();
            String numberPhone = scanner.next();
            String deliveryAddress = scanner.next();


            LocalDate orderDate = LocalDate.now();

            Person person = new Person(name, numberPhone, deliveryAddress);
            Order order = new Order(orderId, orderDate,Status.SHIP, person);

            orderList.addOrder(order);

            scanner.nextLine();

            while (scanner.next().equals("Строка заказа")) {

//                String s = scanner.next();
                Integer id = scanner.nextInt();
                String nameOfProduct = scanner.next();
                String color = scanner.next();
                Integer countOfProducts = scanner.nextInt();
                Integer commonPriceOfProducts = scanner.nextInt();
                Integer priceOfProduct = commonPriceOfProducts/countOfProducts;

                Product product = new Product(id, nameOfProduct, color, priceOfProduct );

                order.addItem(product, countOfProducts);

                scanner.nextLine();



            }


        }


    }
}
