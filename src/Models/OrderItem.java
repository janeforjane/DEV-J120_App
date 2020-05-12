package Models;

public class OrderItem {

    private Product product;
    private Short count;
    private Integer price;

    // реализовать hashcode и  equals  для проверки уникальности строки в Order


    public OrderItem(Product product, Short count, Integer price) {
        this.product = product;
        this.count = count;
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public Short getCount() {
        return count;
    }

    public Integer getPrice() {
        return price;
    }
}
