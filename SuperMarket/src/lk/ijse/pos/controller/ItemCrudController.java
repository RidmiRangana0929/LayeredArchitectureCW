package lk.ijse.pos.controller;

import lk.ijse.pos.model.Item;
import lk.ijse.pos.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemCrudController {
    public static ArrayList<String> getItemIds() throws SQLException, ClassNotFoundException {
        ResultSet result= CrudUtil.execute("SELECT ItemCode FROM item");
        ArrayList<String> itIds=new ArrayList<>();
        while (result.next()){
            itIds.add(result.getString(1));
        }
        return itIds;
    }

    public static Item getItem(String itemCode) throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT * FROM item WHERE ItemCode=?", itemCode);
        if (result.next()) {
            return new Item(
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getBigDecimal(4),
                    result.getInt(5)
            );
        }
        return null;
    }

}
