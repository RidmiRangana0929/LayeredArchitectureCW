package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.SQLUtil;
import lk.ijse.pos.dao.custom.OrderDAO;
import lk.ijse.pos.dto.OrderDTO;
import lk.ijse.pos.entity.Order;
import lk.ijse.pos.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {
    @Override
    public ArrayList<Order> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Order entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO orders VALUES(?,?,?)", entity.getId(), entity.getDate(), entity.getCustomerId());
    }

    @Override
    public boolean update(Order dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public Order search(String s) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean exist(String oid) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeQuery("SELECT OrderID FROM Orders WHERE OrderID=?", oid).next();
    }

    @Override
    public boolean delete(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT OrderID FROM Orders ORDER BY OrderID DESC LIMIT 1;");
        return rst.next() ? String.format("OR%03d", (Integer.parseInt(rst.getString("OrderID").replace("OR", "")) + 1)) : "OR001";
    }
}
