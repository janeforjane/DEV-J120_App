import BL.OrderLogic;
import IO.ProductIOFromFile;
import Lists.PriceList;
import Models.Order;
import Models.Person;
import Models.Product;
import Models.Status;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {

        checkOrderMethods();


    }

    public static void checkIOFromFile() throws IOException { // проверка записи перезаписи в файл;корр кол-ва в файле;пустого цвета



        ProductIOFromFile productIOFromFile = new ProductIOFromFile();

        PriceList priceList = new PriceList(productIOFromFile.readProductsFromFile());

        System.out.println(priceList.showProductList());

        productIOFromFile.changeStockBalanceInFile(priceList.getProducts().get(1), 1);

        System.out.println("color is" + priceList.getProducts().get(2).getColor() + "!");

//        System.out.println(priceList.showProductList());
    }

    public static void checkOrderMethods() {
//Form1 form = new Form1();
//form.
        Person personWinnie = new Person("Winnie the Pooh", "123-45-67","A hole under an old oak tree");
        Order orderOfWinnie = new Order(personWinnie);
        Product product1 = new Product(88881, "Jar of honey", "Yellow",350,10);
        Product product2 = new Product(88882, "Jar of jam", "Red",250,8);
        Product product3 = new Product(88883, "Bunch of carrots", 210,4);
        Product product4 = new Product(88884, "Cup of raspberries", "Green/Red",100,3);
        Product product5 = new Product(88885, "Bunch of carrots", 210,4);

        OrderLogic orderLogic = new OrderLogic();

        orderLogic.addItemInOrder(orderOfWinnie,product1,2);
        orderLogic.addItemInOrder(orderOfWinnie, product2,2);
        orderLogic.addItemInOrder(orderOfWinnie,product3,5);
        orderLogic.addItemInOrder(orderOfWinnie, product4,8);
        orderLogic.addItemInOrder(orderOfWinnie, product2,3);




        System.out.println(orderOfWinnie.getOrderItems().values());

        orderOfWinnie.deleteOrderItem(product5);

        System.out.println(orderOfWinnie.getOrderItems().values());


//        orderProducts.add(orderOfWinnie.getOrderItems().)



        //сделать вывод коллекции в строку
        //проверить как отработает с плохим статусом
        //проверить как отработает с уже сущ товаром в
        //

        // проверить удаление из строки заказа из заказа (если есть и если нету)
        //

        //






    }
}
