package Models;

public class Person {

    private String name;
    private String numberPhone;
    private String deliveryAddress;

    public Person(String name, String numberPhone, String deliveryAddress) {
        this.name = name;
        this.numberPhone = numberPhone;
        this.deliveryAddress = deliveryAddress;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", numberPhone='" + numberPhone + '\'' +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }
}
