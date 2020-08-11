package Models;

public class OrderItem {

    private Product product;
    private Integer count;
    private Integer price;

    // реализовать hashcode и  equals  для проверки уникальности строки в Order


    public OrderItem(Product product, Integer count) {
        this.product = product;
        this.count = count;
        this.price = count * product.getPrice();
    }

    public String getOrderItemToString() {
        return product.getProductToString() +
                count +
                ";" + price ;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "product=" + product +
                ", count=" + count +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderItem orderItem = (OrderItem) o;

        return getProduct().equals(orderItem.getProduct());
    }

    @Override
    public int hashCode() {
        return getProduct().hashCode();
    }

    public Product getProduct() {
        return product;
    }

    public Integer getCount() {
        return count;
    }

    public Integer getPrice() {
        return price;
    }

    public void setCount(Integer count) {
        this.count = count;
        this.price = count * product.getPrice();
    }

    public void setPrice(Integer price) {

        this.price = price;
    }
}
