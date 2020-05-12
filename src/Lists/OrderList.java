package Lists;

import Models.Order;
import Models.OrderItem;
import Models.Product;

import java.util.ArrayList;
import java.util.List;

public class OrderList {

    private List<Order> orders = new ArrayList<>();

    public OrderList (Order order) {
        orders.add(order);
    }

    public void addOrder (Order order) {

        orders.add(order);
    }

    public void deleteOrder (Order order) {
        orders.remove(order);

    }

    public List<Order> getOrders() {
        return orders;
    }
}
