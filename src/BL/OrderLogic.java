package BL;

import IO.OrderIOFile;
import Lists.OrderList;
import Models.Order;
import Models.Product;
import Models.Status;

public class OrderLogic {


    public boolean checkStockBalance (Product product, Integer deleteFromStockBalance) {

        if (product.getStockBalance() < deleteFromStockBalance) {
            return false;
        }
        else return true;
    }

    public void changeStockBalance (Product product, Integer deleteFromStockBalance) {

        product.setStockBalance(product.getStockBalance() - deleteFromStockBalance);

    }

    public void addItemInOrder (Order order, Product product, Integer count) {


        if (order.getStatusOfOrder().equals(Status.SHIP) || order.getStatusOfOrder().equals(Status.CANC)){//проверка статуса заказа

            System.out.println("Статус заказа не позволяет его редактировать");

        }
        else {//**
            if (order.getOrderItems().containsKey(product.getId())) {//проверка наличия добавляемого товара в заказе

                System.out.println("Товар уже есть в заказе в количестве " + order.getOrderItems().get(product.getId()).getCount() + " шт. Количество будет увеличено на " + count);

                int newCount = order.getOrderItems().get(product.getId()).getCount() + count;
                int newPrice = order.getOrderItems().get(product.getId()).getProduct().getPrice() * newCount;

                order.getOrderItems().get(product.getId()).setCount(newCount);
                order.getOrderItems().get(product.getId()).setPrice(newPrice);

            }
            else {

                order.addItem(product,count);
//                OrderItem orderItem = new OrderItem(product, count);
//
//                order.getOrderItems().put(orderItem.getProduct().getId(), orderItem);
//                System.out.println("nn");
            }
        }
    }

    public void saveOrder (Order order) {
        //сделать проверку что количество всех товаров не будет отрицательным
        //сделать уменьшение StockBalance по каждому товару

//        if (this.checkStockBalance(order.getOrderItems().) ) {
//
//        }
//        else {
            order.setStatusOfOrder(Status.SHIP);
            OrderList orderList = new OrderList(order);
            OrderIOFile orderIOFile = new OrderIOFile();

            orderIOFile.writeOrdersInFile(orderList);
//        }

    }


}
