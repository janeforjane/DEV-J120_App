package UI;

import BL.OrderLogic;
import Lists.OrderList;
import Lists.PriceList;
import Models.Product;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.plaf.TableHeaderUI;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GeneralFrame {

    public GeneralFrame(java.util.List<Product> products, OrderList orderList, OrderLogic orderLogic) {

        JFrame frame = new JFrame();
        frame.setTitle("Диванчики и стулья");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        frame.setSize( 900, 600);

        frame.setLayout(new BorderLayout());

        //headers for the table
        String[] columns = new String[] {
                "Id", "Name", "Color", "Price"
        };

        //actual data for the table in a 2d array

        Object[][] data = getArrayPriceList(products);

        JTable table = new JTable(data, columns);

        JScrollPane scrollPane = new JScrollPane(table);

        frame.add(scrollPane, BorderLayout.NORTH);

        // panel and buttons on general frame

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        frame.add(buttonPanel, BorderLayout.CENTER);

        JButton newOrderButton = new JButton("Создать новый заказ");
        newOrderButton.setBackground(new Color(209, 238, 238  ));
        JButton orderListButton = new JButton("Показать список заказов");
        orderListButton.setBackground(new Color(209, 238, 238 ));

        buttonPanel.add(newOrderButton);
        buttonPanel.add(orderListButton);

        newOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                OrderFrame orderFrame = new OrderFrame(products, orderLogic);
            }
        });

        orderListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                OrderListFrame orderListFrame = new OrderListFrame(orderList);
            }
        });


        frame.setVisible(true);



    }

    public static Object [][] getArrayPriceList (java.util.List<Product> products) {
        //***

        Object[][] data = new Object[products.size()][4];

        for (int i =0; i < products.size(); i++) {

            data [i][0] = products.get(i).getId();
            data [i][1] = products.get(i).getName();
            data [i][2] = products.get(i).getColor();
            data [i][3] = products.get(i).getPrice();

        }
        return data;
    }

    static class TableMouseListener extends MouseAdapter {
        private JTable table;

        public TableMouseListener(JTable table) {
            this.table = table;
        }
//нажатие на строку в таблице товаров
        @Override
        public void mousePressed(MouseEvent event) {
            // selects the row at which point the mouse is clicked
            Point point = event.getPoint();
            int currentRow = table.rowAtPoint(point);
//            System.out.println(table.rowAtPoint(point));//v
//            System.out.println(table.getValueAt(currentRow, 1).toString());//v
            table.setRowSelectionInterval(currentRow, currentRow);
        }
    }




}
