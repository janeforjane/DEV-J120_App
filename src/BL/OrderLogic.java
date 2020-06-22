package BL;

import IO.OrderIO;
import IO.ProductIO;
import Lists.OrderList;
import Models.Order;
import Models.OrderItem;
import Models.Product;
import Models.Status;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class OrderLogic {

    private ProductIO productIO;

    private OrderList orderList = new OrderList();
    private OrderIO orderIO;


    public OrderIO getOrderIO() {
        return orderIO;
    }

    public void setOrderIO(OrderIO orderIO) {
        this.orderIO = orderIO;
    }

    public void setProductIO(ProductIO productIO) {
        this.productIO = productIO;
    }

    public OrderList getOrderList() {
        return orderList;
    }

    public boolean checkStockBalance(Product product, Integer deleteFromStockBalance) {

        if (product.getStockBalance() < deleteFromStockBalance) {
            return false;
        } else return true;
    }

    public void changeStockBalance(Product product, Integer deleteFromStockBalance) {

        product.setStockBalance(product.getStockBalance() - deleteFromStockBalance);

    }

    public boolean checkStockBalanceInOrder(HashMap<Integer, OrderItem> orderItems) {

        boolean result = true;

        for (Map.Entry<Integer, OrderItem> entry : orderItems.entrySet()) { //цикл проходится по OrderItems из Order

            OrderItem value = entry.getValue();

            if (checkStockBalance(value.getProduct(), value.getCount())) {// проверка что на складе есть достаточное количество
//                System.out.println("На складе доступно " + value.getProduct().getStockBalance() + " шт товара - " + value.getProduct().getName() + " цвета " + value.getProduct().getColor());
//                System.out.println("Заказ " + value.getCount() + " шт доступен");
            } else {
                System.out.println("Продукт с ID = " + value.getProduct().getId() + " не может быть заказан.");
                System.out.println("В заказе указано количество " + value.getCount() + " шт.");
                System.out.println("На складе осталось только " + value.getProduct().getStockBalance() + " шт.");

                result = false;

            }
        }

        return result;
    }

    public void changeStockBalanceInOrder(HashMap<Integer, OrderItem> orderItems, ProductIO productIO) throws IOException, SQLException {

        for (Map.Entry<Integer, OrderItem> entry : orderItems.entrySet()) { //цикл проходится по OrderItems из Order

            OrderItem value = entry.getValue();

            changeStockBalance(value.getProduct(), value.getCount());// меняю количество у каждого Product
            productIO.changeStockBalanceIn(value.getProduct(), value.getCount());// меняю количество в файле

        }
    }

    public void addItemInOrder(Order order, Product product, Integer count) {


        if (order.getStatusOfOrder().equals(Status.SHIP) || order.getStatusOfOrder().equals(Status.CANC)) {//проверка статуса заказа

            System.out.println("Статус заказа не позволяет его редактировать");

        } else {//**
            if (order.getOrderItems().containsKey(product.getId())) {//проверка наличия добавляемого товара в заказе

                System.out.println("Товар уже есть в заказе в количестве " + order.getOrderItems().get(product.getId()).getCount() + " шт. Количество будет увеличено на " + count);

                int newCount = order.getOrderItems().get(product.getId()).getCount() + count;
                int newPrice = order.getOrderItems().get(product.getId()).getProduct().getPrice() * newCount;

                order.getOrderItems().get(product.getId()).setCount(newCount);
                order.getOrderItems().get(product.getId()).setPrice(newPrice);

            } else {

                order.addItem(product, count);
//                OrderItem orderItem = new OrderItem(product, count);
//
//                order.getOrderItems().put(orderItem.getProduct().getId(), orderItem);
//                System.out.println("nn");
            }
        }
    }

    public void saveOrder(Order order) throws IOException, SQLException {
        // OrderList должен создаваться один на всю сессию и хранить заказы

        //сделать проверку что количество всех товаров не будет отрицательным
        //в цикле пробежаться по коллекции со всеми product (искать и сравнивать по equals )
        //сравнить количество методом checkStockBalance
        //сделать уменьшение StockBalance по каждому товару
        //уменьшает в файле - метод changeStockBalanceInFile у ProductIOFromFile
        //уменьшает в коллекции List<Product> products у ProductIOFromFile путем перечитывания файла

        if (order.getStatusOfOrder().equals(Status.SHIP) || order.getStatusOfOrder().equals(Status.CANC)) {//проверка статуса заказа

            System.out.println("Статус заказа не позволяет его сохранить");

        } else {

            if (checkStockBalanceInOrder(order.getOrderItems())) {//проверка что количество у всех товаров не отрицательное

                order.setStatusOfOrder(Status.SHIP);
                changeStockBalanceInOrder(order.getOrderItems(), productIO);//уменьшение количества в файле и у Product

                orderList.addOrder(order);//запись заказа в коллекцию

                orderIO.writeOrdersAndOrderItemsIn(orderList);//запись заказа в файл
            } else {

                System.out.println("Поменяй количество в заказе " + order.getOrderId() + " и сохрани заново");
            }
        }
    }

    public void saveOrderAndItems(Order order) throws IOException, SQLException {

        if (order.getStatusOfOrder().equals(Status.SHIP) || order.getStatusOfOrder().equals(Status.CANC)) {//проверка статуса заказа

            System.out.println("Статус заказа не позволяет его сохранить");

        } else {

            if (checkStockBalanceInOrder(order.getOrderItems())) {//проверка что количество у всех товаров не отрицательное

                order.setStatusOfOrder(Status.SHIP);
                changeStockBalanceInOrder(order.getOrderItems(), productIO);//уменьшение количества в хранилище и у Product

                orderList.addOrder(order);//запись заказа в коллекцию

                orderIO.writeOrdersAndOrderItemsIn(orderList);//запись заказа в файл
            } else {

                System.out.println("Поменяй количество в заказе " + order.getOrderId() + " и сохрани заново");
            }
        }
    }
}
