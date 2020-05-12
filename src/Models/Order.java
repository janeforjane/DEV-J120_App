package Models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Order {

    private Integer orderId;
    private LocalDate orderDate;
    private Person contactPerson;
    private Status statusOfOrder;

    private List <OrderItem> orderItems = new ArrayList<>();

//    private HashMap <Long, Models.Product> orderList;

    // реализовать проверку по hashcode и  equals  для OrderItems


    public Order(LocalDate orderDate, Person contactPerson,  Status statusOfOrder) {
        this.orderId = IdGenerator();
        this.orderDate = orderDate;
        this.contactPerson = contactPerson;
        this.statusOfOrder = statusOfOrder;
    }

    public void addItem (OrderItem orderItem) {
        orderItems.add(orderItem);

    }

    public void addItem (Product product, Short count) {
        // проверка количества товаров поле stockBalance у Product

        OrderItem orderItem = new OrderItem(product, count, product.getPrice());
        orderItems.add(orderItem);
    }

    public void deleteOrderItem (OrderItem orderItem) {
        orderItems.remove(orderItem);

    }

    public Integer IdGenerator () {
        Random random = new Random();
        Integer x = (random.nextInt(9999 - 1001) + 1001);
        return x;

    }


}
