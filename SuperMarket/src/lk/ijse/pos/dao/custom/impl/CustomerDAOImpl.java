package lk.ijse.pos.dao.custom.impl;

import lk.ijse.pos.dao.SQLUtil;
import lk.ijse.pos.dao.custom.CustomerDAO;
import lk.ijse.pos.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Customer entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("INSERT INTO Customer VALUES (?,?,?,?,?,?,?)",entity.getCustID(),entity.getCustTitle(),entity.getCustName(),entity.getCustAddress(),entity.getCity(),entity.getProvince(),entity.getPostalCode());
    }

    @Override
    public boolean update(Customer dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("UPDATE customer SET CustTitle=?,CustName=?,CustAddress=?,City=?,Province=?,PostalCode=? WHERE CustID=? ",dto.getCustTitle(),dto.getCustName(),dto.getCustAddress(),dto.getCustName(),dto.getProvince(),dto.getPostalCode(),dto.getCustID());
    }

    @Override
    public Customer search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM Customer WHERE CustID=?", id);
        if (rst.next()) {
            return new Customer(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6), rst.getString(7));
        }
        return null;
    }

    @Override
    public boolean exist(String s) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("DELETE FROM customer WHERE CustID=?",id);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<Customer> getAllCustomersByAddress(String address) throws ClassNotFoundException, SQLException {
        return null;
    }
}
