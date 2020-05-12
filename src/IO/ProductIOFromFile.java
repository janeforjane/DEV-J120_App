package IO;

import Lists.PriceList;
import Models.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.RandomAccess;
import java.util.Scanner;

public class ProductIOFromFile implements ProductIO {

//    обратиться к файлу ххх
//    прочитать строку файла (в которой есть String name, Integer price, Integer stockBalance)
//    сделать объекты product (передав необх даные в конструктор)
//    сделать объект PriceList
//    передать объекты Product в коллекцию объекта PriceList

    public List<Product> readProducts() throws FileNotFoundException {

        List<Product> products = new ArrayList<>();

        FileInputStream fileInputStream = new FileInputStream("PriceListFile");
        Scanner scanner = new Scanner(fileInputStream);
        scanner.useDelimiter(";");
        while (scanner.hasNextLine()) {

            String name = scanner.next();
            Integer price = scanner.nextInt();
            Integer stockBalance = scanner.nextInt();

            Product product = new Product(name, price, stockBalance);
            products.add(product);

            scanner.nextLine();

        }
        return products;
    }

    @Override
    public void changeProduct(Product product, Integer deleteFromStockBalance) throws IOException {

//        FileOutputStream fileOutputStream = new FileOutputStream("PriceListFile");
        Writer writer = new FileWriter("PriceListFile");
        RandomAccessFile randomAccessFile = new RandomAccessFile();


        //надо
        //найти нужную строку с Product который был передан в метод,
        //прочитать значение stockBalance в этой строке,
        //вычесть из прочитанного то что передано в метод (Integer deleteFromStockBalance),
        //записать результат в значение stockBalance в этой строке

//        String s = "nhnhhjj";
//
//        fileOutputStream.write(s.getBytes());


    }
}