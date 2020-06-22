package IO;

import Lists.PriceList;
import Models.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.RandomAccess;
import java.util.Scanner;

public class ProductIOFromFile implements ProductIO {

    private String pathToFile;
    List<Product> products = new ArrayList<>();//возможно создать коллекцию products в main или BL

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public List<Product> readProductsFrom() throws FileNotFoundException {

//        List<Product> products = new ArrayList<>(); //коллекция будет создана при создании объекта ProductIOFromFile

        FileInputStream fileInputStream = new FileInputStream(pathToFile);
        Scanner scanner = new Scanner(fileInputStream);
        scanner.useDelimiter(";");
        while (scanner.hasNextLine()) {

            Integer id = scanner.nextInt();
            String name = scanner.next();
            String color = scanner.next();
            Integer price = scanner.nextInt();
            Integer stockBalance = scanner.nextInt();

            Product product = new Product(id, name,color, price, stockBalance);
            products.add(product);

            scanner.nextLine();

        }
        return products;
    }

    @Override
    public void changeStockBalanceIn(Product product, Integer deleteFromStockBalance) throws IOException {

        FileInputStream fileInputStream = new FileInputStream(pathToFile);
        BufferedInputStream bufferedInputStream = new BufferedInputStream( fileInputStream);

//        Writer writer = new FileWriter("PriceListFile");
//        RandomAccessFile randomAccessFile = new RandomAccessFile();

        StringBuilder sb = new StringBuilder();


        Scanner scanner = new Scanner(bufferedInputStream);
        scanner.useDelimiter(";");

        while (scanner.hasNextLine()) {

            Integer id = scanner.nextInt();
            String name = scanner.next();
            String color = scanner.next();
            Integer price = scanner.nextInt();
            Integer stockBalance = scanner.nextInt();


            if (id.equals(product.getId())){

                sb.append(id).append(";");
                sb.append(name).append(";");
                sb.append(color).append(";");
                sb.append(price).append(";");

                    sb.append(stockBalance - deleteFromStockBalance).append(";");
                    sb.append("\n");
                    scanner.nextLine();
            }
            else {

            sb.append(id).append(";");
            sb.append(name).append(";");
            sb.append(color).append(";");
            sb.append(price).append(";");
            sb.append(stockBalance).append(";");

            sb.append("\n");
            scanner.nextLine();}

        }

        String s = sb.toString();

        FileOutputStream fileOutputStream = new FileOutputStream(pathToFile);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

        bufferedOutputStream.write(s.getBytes());
        bufferedOutputStream.close();
//        bufferedOutputStream.flush();

    }
}