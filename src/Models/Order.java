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

    //конструктор для создания заказов при чтении файла с заказами
    public Order(Integer orderId, LocalDate orderDate, Status statusOfOrder, Person contactPerson) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.contactPerson = contactPerson;
        this.statusOfOrder = statusOfOrder;
    }

    public void setStatusOfOrder(Status statusOfOrder) {
        if (this.statusOfOrder == Status.PREP) {
            this.statusOfOrder = statusOfOrder;
        } else {
            System.out.println("Статус заказа не может быть изменён");
        }
    }

    public void addItem (OrderItem orderItem) {
        orderItems.put(orderItem.getProduct().getId(), orderItem);
        System.out.println("Товар с ID = " + orderItem.getProduct().getName() + " добавлен в заказ в количестве " + orderItem.getCount());

    }

    public void addItem (Product product, Integer count) {

                OrderItem orderItem = new OrderItem(product, count);

                this.orderItems.put(orderItem.getProduct().getId(), orderItem);
//                System.out.println("В заказ " + orderId + " добавлен товар с ID =" + orderItem.getProduct().getId()+ " в количестве " + count);
    }

    public void deleteOrderItem (Product product) {
        if (this.orderItems.containsKey(product.getId())){
            orderItems.remove(product.getId());
            System.out.println("Из заказа " + orderId + " удалён товар с ID " + product.getId());
        } else {
            System.out.println("Такого товара нет в заказе");
        }

    }

    public void changeCountOfOrderItem (Product product, Integer newCount) {
        if (statusOfOrder != Status.SHIP || statusOfOrder != Status.CANC) {
            if (newCount != 0) {

                if (this.orderItems.containsKey(product.getId())) {
                    this.orderItems.get(product.getId()).setCount(newCount);
                } else {
                    System.out.println("Такого товара нет в заказе");
                }
            } else {
                System.out.println("Нельзя поставить количество 0");
            }
        } else {
            System.out.println("Статус заказа " + orderId + " - " + statusOfOrder + ". Заказ корректировать нельзя.");
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
