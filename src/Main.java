import BL.OrderLogic;
import IO.OrderIO;
import IO.OrderIOFile;
import IO.ProductIOFromFile;
import Lists.PriceList;
import Models.Order;
import Models.Person;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

//        checkOrderMethods();

        //загрузка товаров из файла в коллекцию readProductsFromFile

        System.out.println("Чтение из файла и запись в коллекцию");

        PropertyService service = new FilePropertyService();

        ProductIOFromFile productIOFromFile = new ProductIOFromFile();


        productIOFromFile.setPathToFile(service.getByName("ProductFilePath"));

        System.out.println("-------" + service.getByName("ProductFilePath"));


        PriceList priceList = new PriceList(productIOFromFile.readProductsFromFile());

        System.out.println("******");
        System.out.println("Создание заказов");

        //создать persons, создать их заказы, накидать товары в заказы

        Person personWinnie = new Person("Winnie the Pooh", "123-45-67","A hole under an old oak tree");
        Person personPiglet = new Person("Piglet", "777-55-66","Little tree house on pine");
        Person personRabbit = new Person("Rabbit", "111-22-33","Nice house on the meadow");

        Order orderOfWinnie = new Order(personWinnie);
        Order orderOfPiglet = new Order(personPiglet);

        System.out.println("******");
        System.out.println("Добавление в заказ продукта Винни");

        orderOfWinnie.addItem(priceList.getProducts().get(0),1);
        System.out.println("Заказ Винни:"+ orderOfWinnie.toString());

        System.out.println("******");
        System.out.println("Добавление в заказ продукта Пиглет");

        orderOfPiglet.addItem(priceList.getProducts().get(1),4);
        System.out.println("Заказ Пиглет:"+ orderOfPiglet.toString());

        System.out.println("колво на складе товара Винни " + priceList.getProducts().get(0).getName() + priceList.getProducts().get(0).getColor() + priceList.getProducts().get(0).getStockBalance());
        System.out.println("колво на складе товара Пиглет " + priceList.getProducts().get(1).getName() + priceList.getProducts().get(1).getColor() + priceList.getProducts().get(1).getStockBalance());

        orderOfPiglet.changeCountOfOrderItem(priceList.getProducts().get(1), 0);

        System.out.println("Заказ Пиглет:"+ orderOfPiglet.toString());


        OrderLogic orderLogic = new OrderLogic();
        orderLogic.setProductIO(productIOFromFile);
        OrderIOFile orderIO = new OrderIOFile();

        orderLogic.setOrderIO(orderIO);

        orderIO.setPathToFile(service.getByName("OrdersFilePath"));


        System.out.println("******");
        System.out.println("Сохранение заказов");

        orderLogic.saveOrder(orderOfWinnie);


        System.out.println("колво на складе товара Винни после сохранения заказа" + priceList.getProducts().get(0).getName() + priceList.getProducts().get(0).getStockBalance());


        orderLogic.saveOrder(orderOfPiglet);

        System.out.println("колво на складе товара Пиглета после сохранения заказа" + priceList.getProducts().get(1).getName() + priceList.getProducts().get(1).getStockBalance());

//        Order orderOfRabbit = new Order(personRabbit);
//
//        System.out.println("******");
//        System.out.println("Добавление в заказ продукта Кролика");
//
//        orderOfRabbit.addItem(priceList.getProducts().get(0),1);
//        orderOfRabbit.addItem(priceList.getProducts().get(2),2);
//        System.out.println("Заказ Кролика:"+ orderOfWinnie.toString());





        //указать слишком большое количество в заказе
        //удалить товары из заказы
        //поменять количество в заказе
        //
        //сохранить заказы

    }

    public static void checkIOFromFile() throws IOException {
        // проверка записи перезаписи в файл;корр кол-ва в файле;пустого цвета



        ProductIOFromFile productIOFromFile = new ProductIOFromFile();

        PriceList priceList = new PriceList(productIOFromFile.readProductsFromFile());

        System.out.println(priceList.showProductList());

        productIOFromFile.changeStockBalanceInFile(priceList.getProducts().get(1), 1);

        System.out.println("color is" + priceList.getProducts().get(2).getColor() + "!");

//        System.out.println(priceList.showProductList());
    }
}
