package IO;

import Lists.OrderList;
import Models.Order;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public interface OrderIO {

//    public void writeOrdersIn(OrderList orderList) throws IOException;

    public void writeOrdersAndOrderItemsIn(OrderList orderList) throws IOException, SQLException;

    public void readOrdersFrom(OrderList orderList) throws FileNotFoundException, SQLException;
}
