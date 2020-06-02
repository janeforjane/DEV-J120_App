package IO;

import Lists.OrderList;
import Models.OrderItem;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class OrderIOFile implements OrderIO {

    private String pathToFile;

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }


    @Override
    public void writeOrdersInFile(OrderList orderList) throws IOException {

        StringBuilder sb = new StringBuilder();

        FileOutputStream fileOutputStream = new FileOutputStream(pathToFile);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);


        for(int i = 0; i < orderList.getOrders().size(); i++) {//цикл проходится по Order из OrderList
//            System.out.println(" В элементе списка List " + i + " лежит " + orderList.getOrders().get(i));

            String id = String.valueOf(orderList.getOrders().get(i).getOrderId());
            String date = String.valueOf(orderList.getOrders().get(i).getOrderDate());
            String person = orderList.getOrders().get(i).getContactPerson().toString();

            sb.append(i+1).append(" - ");
            sb.append(id).append("-");
            sb.append(date).append("-");
            sb.append(person);

            sb.append("\n");


            for (Map.Entry<Integer, OrderItem> entry : orderList.getOrders().get(i).getOrderItems().entrySet()) { //цикл проходится по OrderItems каждого Order из OrderList

                Integer key = entry.getKey();
                OrderItem value = entry.getValue(); //получаю объект строки заказа
                String orderItem = value.getOrderItemToString();

                sb.append(orderItem);

            }

            sb.append("Разделитель заказов ---------").append("\n");


        }

        String s = sb.toString();
        System.out.println("В файл с заказами будет записано :\n" + s);

        bufferedOutputStream.write(s.getBytes());
        bufferedOutputStream.close();






    }
}
