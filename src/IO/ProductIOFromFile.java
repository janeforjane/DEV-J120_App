package IO;

import Lists.PriceList;
import Models.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.RandomAccess;
import java.util.Scanner;

public class ProductIOFromFile implements ProductIO {

    public List<Product> readProductsFromFile() throws FileNotFoundException {

        List<Product> products = new ArrayList<>();

        FileInputStream fileInputStream = new FileInputStream("PriceListFile");
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
    public void changeStockBalanceInFile(Product product, Integer deleteFromStockBalance) throws IOException {

        FileInputStream fileInputStream = new FileInputStream("PriceListFile");
        BufferedInputStream bufferedInputStream = new BufferedInputStream( fileInputStream);

        FileOutputStream fileOutputStream = new FileOutputStream("OutPriceListFile",true);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);

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

                sb.append(id).append("!");
                sb.append(name).append("!");
                sb.append(color).append("!");
                sb.append(price).append("!");

                    sb.append(stockBalance - deleteFromStockBalance).append("!");
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

        System.out.println(s);

        bufferedOutputStream.write(s.getBytes());
        bufferedOutputStream.close();
//        bufferedOutputStream.flush();

    }
}