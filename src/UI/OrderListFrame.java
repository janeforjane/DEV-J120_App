package UI;

import Lists.OrderList;

import javax.swing.*;
import java.awt.*;

public class OrderListFrame {

    private JTable tableOfOrders;


    public OrderListFrame(OrderList orderList) {

        //headers for the table
        String[] columns = new String[] {
                "No", "Id", "Status", "Date", "Contact person", "Count of items"
        };

        //actual data for the table in a 2d array

        Object[][] data = getArrayOrderList(orderList);


        JFrame orderListFrame = new JFrame();
        orderListFrame.setTitle("Список заказов");
        orderListFrame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        orderListFrame.setSize( 800, 900);
        orderListFrame.setLayout(new BorderLayout());

        //таблица товаров сверху
        tableOfOrders = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(tableOfOrders);
        tableOfOrders.setBackground(new Color(145, 222, 179 ));
        tableOfOrders.setGridColor(new Color(133, 110, 152 ));
        orderListFrame.add(scrollPane, BorderLayout.CENTER);





        orderListFrame.setVisible(true);

    }

    public static Object [][] getArrayOrderList (OrderList orderList) {

        Object[][] data = new Object[orderList.getOrders().size()][6];

        for (int i =0; i < orderList.getOrders().size(); i++) {

            data [i][0] = i + 1;
            data [i][1] = orderList.getOrders().get(i).getOrderId();
            data [i][2] = orderList.getOrders().get(i).getStatusOfOrder();
            data [i][3] = orderList.getOrders().get(i).getOrderDate();
            data [i][4] = orderList.getOrders().get(i).getContactPerson().getName();
            data [i][5] = orderList.getOrders().get(i).getOrderItems().size();


        }
        return data;
    }

}
