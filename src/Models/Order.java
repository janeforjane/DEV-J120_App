package Models;

import java.time.LocalDate;
import java.util.*;

public class Order {

    private Integer orderId;
    private LocalDate orderDate;
    private Person contactPerson;
    private Status statusOfOrder;

    private HashMap<Integer, OrderItem> orderItems = new HashMap<>();
    private HashSet <Integer> ids = new HashSet<>();

//    private HashMap <Long, Models.Product> orderList;


    public Integer getOrderId() {
        return orderId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public Person getContactPerson() {
        return contactPerson;
    }

    public Status getStatusOfOrder() {
        return statusOfOrder;
    }

    public HashMap<Integer, OrderItem> getOrderItems() {  //для проверки (в логике не участвует)
        return orderItems;
    }

    public Order(Person contactPerson) {
        this.orderId = IdGenerator();
        this.orderDate = LocalDate.now();
        this.contactPerson = contactPerson;
        this.statusOfOrder = Status.PREP;
    }

    //*******

    public void setStatusOfOrder(Status statusOfOrder) {
        this.statusOfOrder = statusOfOrder;
    }

    public void addItem (OrderItem orderItem) {
        orderItems.put(orderItem.getProduct().getId(), orderItem);
        System.out.println("Продукт " + orderItem.getProduct().getName() + " добавлен в заказ в количестве " + orderItem.getCount());

    }

    public void addItem (Product product, Integer count) {

                OrderItem orderItem = new OrderItem(product, count);

                this.orderItems.put(orderItem.getProduct().getId(), orderItem);
                System.out.println("В заказ добавлен товар." + orderItem.getProduct().getId());
    }

    public void deleteOrderItem (Product product) {
        if (this.orderItems.containsKey(product.getId())){
            orderItems.remove(product.getId());
        }
        System.out.println("Такого товара нет в заказе");

    }

    public void changeCountOfOrderItem (Product product, Integer newCount) {
        if (newCount != 0) {

            if (this.orderItems.containsKey(product.getId())) {
                this.orderItems.get(product.getId()).setCount(newCount);
            } else {
                System.out.println("Такого товара нет в заказе");
            }
        } else {
            System.out.println("Нельзя поставить количество 0");
        }

    }



    public Integer IdGenerator () {
        Random random = new Random();
        Integer x = (random.nextInt(9999 - 1001) + 1001);
        while (ids.contains(x) ) { //проверка id на уникальность
            x = (random.nextInt(9999 - 1001) + 1001);
        }
        return x;

    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", orderDate=" + orderDate +
                ", contactPerson=" + contactPerson +
                ", statusOfOrder=" + statusOfOrder +
                ", orderItems=" + orderItems +
                '}';
    }
}
