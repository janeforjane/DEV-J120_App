package IO;

import Lists.OrderList;
import Models.Order;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface OrderIO {

    public void writeOrdersInFile(OrderList orderList) throws IOException;
}
