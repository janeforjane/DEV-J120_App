package BL;

import IO.OrderIO;
import IO.OrderIOFile;
import IO.ProductIO;
import Lists.OrderList;
import Models.Order;
import Models.OrderItem;
import Models.Product;
import Models.Status;

import java.io.IOException;
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
                System.out.println("На складе " + value.getProduct().getStockBalance() + "шт товара -" + value.getProduct().getName() + " цвета " + value.getProduct().getColor());

            } else {
                System.out.println("Продукт " + value.getProduct().getName() + " не может быть добавлен в заказ.");
                System.out.println("В заказе указано количество " + value.getCount() + " шт.");
                System.out.println("На складе осталось только " + value.getProduct().getStockBalance() + " шт.");

                result = false;

            }
        }

        return result;
    }

    public void changeStockBalanceInOrder(HashMap<Integer, OrderItem> orderItems, ProductIO productIO) throws IOException {

        for (Map.Entry<Integer, OrderItem> entry : orderItems.entrySet()) { //цикл проходится по OrderItems из Order

            OrderItem value = entry.getValue();

            changeStockBalance(value.getProduct(), value.getCount());// меняю количество у каждого Product
            productIO.changeStockBalanceInFile(value.getProduct(), value.getCount());// меняю количество в файле


//            Integer newStockBalance = value.getProduct().getStockBalance() - value.getCount();
//            value.getProduct().setStockBalance(newStockBalance);
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

    public void saveOrder(Order order) throws IOException {
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

                orderIO.writeOrdersInFile(orderList);//запись заказа в файл
            } else {

                System.out.println("Поменяй количество в заказе и сохрани заново");
            }
        }
    }
}
