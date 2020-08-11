package UI;

import BL.OrderLogic;
import Models.Order;
import Models.Person;
import Models.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import static UI.GeneralFrame.getArrayPriceList;
//*****

public class OrderFrame extends JFrame {

    private JTable tableOfProducts;
    private JPopupMenu popupMenu;
    private JMenuItem menuItemAddInOrder;

    public JMenuItem getMenuItemAddInOrder() {
        return menuItemAddInOrder;
    }

    public JTable getTableOfProducts() {
        return tableOfProducts;
    }

    public OrderFrame(java.util.List<Product> products, OrderLogic orderLogic) {


        JFrame frame2 = new JFrame();
        frame2.setTitle("Форма заказа");
        frame2.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frame2.setSize( 800, 900);

//        frame2.setLayout(new BorderLayout());
        frame2.getContentPane().setLayout(new BoxLayout(frame2.getContentPane(), BoxLayout.Y_AXIS));



        //tableOfProducts

            //headers for the table
        String[] columns = new String[] {
                "Id", "Name", "Color", "Price"
        };
            //actual data for the table in a 2d array
        Object[][] data = getArrayPriceList(products);

        JPanel panelTableOfProducts = new JPanel();
        panelTableOfProducts.setBorder(BorderFactory.createTitledBorder("Table of Products"));


        tableOfProducts = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(tableOfProducts);
        panelTableOfProducts.add(scrollPane);
        tableOfProducts.setBackground(new Color(176, 224, 230 ));
        tableOfProducts.setGridColor(new Color(70, 130, 180 ));
        tableOfProducts.setSelectionBackground(new Color(255, 218, 185));

//        frame2.add(scrollPane, BorderLayout.CENTER);


        // контекстное меню таблицы товаров
        /*popupMenu = new JPopupMenu();
        menuItemAddInOrder = new JMenuItem("Добавить товар в заказ");


//        menuItemAddInOrder.addActionListener(this);
        menuItemAddInOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JMenuItem menu = (JMenuItem) e.getSource();
                if (menu == menuItemAddInOrder) {
                    System.out.println("gogogog");
                    addItemInOrder();
                }
            }
        });


        popupMenu.add(menuItemAddInOrder);

        tableOfProducts.setComponentPopupMenu(popupMenu);
        tableOfProducts.addMouseListener(new GeneralFrame.TableMouseListener(tableOfProducts));

         */


        //панель с полями ввода для person******
        JPanel panelForPersonInfo = new JPanel();
        panelForPersonInfo.setBorder(BorderFactory.createTitledBorder("Enter your info"));
        panelForPersonInfo.setLayout(new GridLayout(4,6, 4,18));


        JTextField textPhone = new JTextField(10);
        JTextField textName = new JTextField(10);
        JTextField textAddress = new JTextField(10);


        JLabel labelPhone = new JLabel("Enter number phone:");
            labelPhone.setFont(new Font("Arial", 6,12));
        JLabel labelName = new JLabel("Enter your name:");
            labelName.setFont(new Font("Arial", 6,12));
        JLabel labelAdd = new JLabel("Enter your address:");
            labelAdd.setFont(new Font("Arial", 6,12));

        labelPhone.setLabelFor(textPhone);
        labelName.setLabelFor(textName);
        labelAdd.setLabelFor(textAddress);

        panelForPersonInfo.add(labelName);
        panelForPersonInfo.add(textName);

        panelForPersonInfo.add(labelPhone);
        panelForPersonInfo.add(textPhone);

        panelForPersonInfo.add(labelAdd);
        panelForPersonInfo.add(textAddress);


        //панель с кнопкой удаления
        JPanel panelForSave = new JPanel();
        panelForSave.setBorder(BorderFactory.createTitledBorder(" "));
        panelForSave.setLayout(new BorderLayout());


        JButton saveOrderButton = new JButton("Save the order");
        saveOrderButton.setFont(new Font("Arial", 6,12));

        panelForSave.add(saveOrderButton);

        //панель с вводом количества и добавлением товара

        JPanel panelForAddProduct = new JPanel();
        panelForAddProduct.setBorder(BorderFactory.createTitledBorder("Choose the product, enter the quantity and add"));
        panelForAddProduct.setLayout(new GridLayout(1,3, 4,18));

        JTextField countOfProduct = new JTextField(10);
        countOfProduct.setText("0");

        JLabel labelCount = new JLabel("Enter count");
            labelCount.setFont(new Font("Arial", 6,12));

        labelCount.setLabelFor(countOfProduct);

        JButton addItemInOrderButton = new JButton("Add");
        addItemInOrderButton.setFont(new Font("Arial", 6,12));

        panelForAddProduct.add(labelCount);
        panelForAddProduct.add(countOfProduct);
        panelForAddProduct.add(addItemInOrderButton);


        //панель с кнопкой удаления
        JPanel panelForDel = new JPanel();
        panelForDel.setBorder(BorderFactory.createTitledBorder("Choose product and delete"));
        panelForDel.setLayout(new BorderLayout());


        JButton delItemInOrderButton = new JButton("Delete");
            delItemInOrderButton.setFont(new Font("Arial", 6,12));

        panelForDel.add(delItemInOrderButton, BorderLayout.EAST);

        //        addItemInOrderButton.setEnabled(false);

//        frame2.add(panelForPersonInfo, BorderLayout.NORTH);




        // table of Order

        Object[][] data3 = null;

        String[] columns2 = new String[] {
                "Id", "Name", "Color", "Price", "Count"
        };

        DefaultTableModel tableModel3 = new DefaultTableModel(data3, columns2);
        JTable tableOfOrder = new JTable(tableModel3);
        tableOfOrder.setBackground(new Color(135, 206, 250 ));
        tableOfOrder.setGridColor(new Color(30, 144, 255));
        tableOfOrder.setSelectionBackground(new Color(220, 220, 220));
        JScrollPane scrollPaneOrderTable = new JScrollPane(tableOfOrder);
        tableOfOrder.setToolTipText("Your order");

//        frame2.add(scrollPaneOrderTable, BorderLayout.SOUTH);



        // actionListeners****

        addItemInOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow = tableOfProducts.getSelectedRow();

                if (selectedRow < 0 ) { //проверка на то что что-то выбрано

                    WarnWindow warnWindow = new WarnWindow("Choose something");

                    System.out.println("ничего не выбрано");

                } else {

                    String id = tableOfProducts.getValueAt(selectedRow, 0).toString();
                    String col1 = tableOfProducts.getValueAt(selectedRow, 1).toString();
                    String col2 = tableOfProducts.getValueAt(selectedRow, 2).toString();
                    String col3 = tableOfProducts.getValueAt(selectedRow, 3).toString();
                    String col4 = countOfProduct.getText();
                    int countValue = Integer.parseInt(col4);

                    int countOfRows = tableOfOrder.getRowCount();

                    int checkContainsItem = checkItemInTable(tableOfOrder, countOfRows, id);

                    if (checkContainsItem > 0) { //проверка на наличие товара в корзине

//                        ***Integer countOfItemInSelectedRow = Integer.parseInt(tableOfOrder.getValueAt(selectedRow,4).toString());

                            int numOfRowOfItemInOrderTable = numOfRowOfItemInTable(tableOfOrder, countOfRows, id);

                            Integer newCount = countValue + Integer.parseInt(tableOfOrder.getValueAt(numOfRowOfItemInOrderTable, 4).toString());
                            String newCol4 = newCount.toString();

                            tableModel3.removeRow(numOfRowOfItemInOrderTable);

                            String[] rowData = new String[]{id, col1, col2, col3, newCol4};
                            tableModel3.addRow(rowData);

                    } else {

                        if (countValue == 0)   { //проверка на нулевое количество

                            WarnWindow warnWindow = new WarnWindow("Enter count of items");
                            System.out.println("Указано количество 0");

                        }else {

                            System.out.println(textName.getText());

                            String[] rowData = new String[]{id, col1, col2, col3, col4};
                            tableModel3.addRow(rowData);
                        }
                    }
                }
            }
        });

        delItemInOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int selectedRow = tableOfOrder.getSelectedRow();

                if (selectedRow < 0) {
                    WarnWindow warnWindow = new WarnWindow("Choose what you want to delete");
                    System.out.println("ничего не выбрано");

                } else {

                    String id = tableOfOrder.getValueAt(selectedRow, 0).toString();

                    int countOfRows = tableOfOrder.getRowCount();

                    int checkContainsItem = checkItemInTable(tableOfOrder, countOfRows, id);

                    if (checkContainsItem > 0) {

                        int numOfRowOfItemInOrderTable = numOfRowOfItemInTable(tableOfOrder,countOfRows,id);

                        tableModel3.removeRow(numOfRowOfItemInOrderTable);

                    } else {
                        WarnWindow warnWindow = new WarnWindow("Такого товара нет в заказе");
                        System.out.println("такого товара нет в заказе");
                    }
                }
            }
        });

        saveOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int countOfRows = tableOfOrder.getRowCount();

                String name = textName.getText();
                String address = textAddress.getText();
                String phone = textPhone.getText();


                if (countOfRows < 1 || name.equals("")|| address.equals("") || phone.equals("")) {
                    WarnWindow warnWindow = new WarnWindow("Choose items, enter your info");
                    System.out.println("Ничего не выбрано в заказе  или не указана информация заказчика");
                } else {

                    Person person = new Person(name, phone, address);
                    Order order = new Order(person);

                    for (int i = 0; i < countOfRows; i++) {

                       Integer idOfProductInRow = Integer.parseInt(tableOfOrder.getValueAt(i, 0).toString());
                       Integer countOfProductInRow = Integer.parseInt(tableOfOrder.getValueAt(i, 4).toString());

                        for (int j = 0; j < products.size(); j++) {

                            if (idOfProductInRow.equals(products.get(j).getId())) {

                                order.addItem(products.get(j), countOfProductInRow);

                            }

                        }

                    }

                    try {
                        orderLogic.saveOrderAndItems(order);
                        frame2.setVisible(false);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }




                }


            }
        });


        frame2.getContentPane().add(scrollPane);
        frame2.getContentPane().add(panelForAddProduct);
        frame2.getContentPane().add(scrollPaneOrderTable);
        frame2.getContentPane().add(panelForDel);
        frame2.getContentPane().add(panelForPersonInfo);
        frame2.getContentPane().add(panelForSave);

        frame2.setVisible(true);

    }


    private String addItemInOrder () {
        int selectedRow = tableOfProducts.getSelectedRow();
        int selectedColumn = tableOfProducts.getSelectedColumn();
        String s= tableOfProducts.getValueAt(selectedRow, 0).toString();
        System.out.println(s);
        System.out.println("nfhfhfhfhfhfh");
        return s;
    }

    private int checkItemInTable (JTable table, int countOfRows, String addId) {
        int counter = 0;

        for (int i = 0; i < countOfRows; i++) {
            String row = table.getValueAt(i,0).toString();
            if (row.equals(addId)) {
                counter++;
            }
        }
        return counter;
    }

    private int numOfRowOfItemInTable (JTable table, int countOfRows, String addId) {
        int counter = -1;

        for (int i = 0; i < countOfRows; i++) {
            String row = table.getValueAt(i,0).toString();
            if (row.equals(addId)) {
                counter = i;
            }
        }
        return counter;
    }

}

