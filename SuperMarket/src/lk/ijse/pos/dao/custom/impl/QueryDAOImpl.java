package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.SQLUtil;
import lk.ijse.pos.dao.custom.QueryDAO;
import lk.ijse.pos.entity.CustomEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public ArrayList<CustomEntity> searchOrderByOrderID(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("select Orders.OrderID,Orders.OrderDate,Orders.CustID,OrderDetail.ItemCode,OrderDetail.OrderQty,OrderDetails.Discount from Orders inner join OrderDetail on Orders.OrderID=OrderDetail.OrderID where Orders.OrderID=?;", id);
        ArrayList<CustomEntity> orderRecords = new ArrayList();
        while (rst.next()) {
            orderRecords.add(new CustomEntity(rst.getString(1), rst.getString(2), rst.getString(3), rst.getInt(4), rst.getBigDecimal(5), rst.getString(6)));
        }
        return orderRecords;
    }
}
