package IO;

import Lists.OrderList;
import Models.Order;

import java.io.FileNotFoundException;

public interface OrderIO {

    public void writeOrdersInFile(OrderList orderList) throws FileNotFoundException;
}
