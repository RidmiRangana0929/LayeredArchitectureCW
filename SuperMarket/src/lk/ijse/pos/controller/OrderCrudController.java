package lk.ijse.pos.controller;

import lk.ijse.pos.model.Order;
import lk.ijse.pos.model.OrderDetails;
import lk.ijse.pos.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderCrudController {

    public boolean saveOrder(Order order) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO orders VALUES(?,?,?)",
                order.getId(), order.getDate(), order.getCustomerId());
    }

    private boolean updateQty(String itemCode, int qty) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE item SET QtyOnHand=QtyOnHand-? WHERE ItemCode=?", qty,itemCode);
    }

    public boolean saveOrderDetails(ArrayList<OrderDetails> details) throws SQLException, ClassNotFoundException {
        for (OrderDetails det:details
        ) {
            boolean isDetailsSaved=CrudUtil.execute("INSERT INTO OrderDetail VALUES(?,?,?,?)",
                    det.getOrderId(),det.getItemCode(),det.getOrderQty(),det.getDiscount());
            if (isDetailsSaved){
                if (!updateQty(det.getItemCode(), det.getOrderQty())){
                    return false;
                }
            }else{
                return false;
            }
        }
        return true;
    }

    public static String getOrderId(int column) throws SQLException, ClassNotFoundException {
        ResultSet set = CrudUtil.execute("SELECT OrderID FROM orders ORDER BY OrderID DESC LIMIT 1");

        if (set.next()) {
            String id = set.getString(column);
            String[] splitted = id.split("(OR)");
            return String.format("OR%03d", (Integer.valueOf(splitted[1]) + 1));
        }
        return "OR001";
    }

}
