package lk.ijse.pos.bo.custom.impl;

import lk.ijse.pos.bo.custom.ItemBO;
import lk.ijse.pos.dao.DAOFactory;
import lk.ijse.pos.dao.custom.ItemDAO;
import lk.ijse.pos.dto.ItemDTO;
import lk.ijse.pos.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {

    private final ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public ArrayList<ItemDTO> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<Item> all = itemDAO.getAll();
        ArrayList<ItemDTO> allItems= new ArrayList<>();
        for (Item item : all) {
            allItems.add(new ItemDTO(item.getItemCode(),item.getDescription(),item.getPackSize(),item.getUnitPrice(),item.getQtyOnHand()));
        }
        return allItems;
    }

    @Override
    public boolean deleteItem(String code) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(code);
    }

    @Override
    public boolean saveItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.save(new Item(dto.getItemCode(),dto.getDescription(),dto.getPackSize(),dto.getUnitPrice(),dto.getQtyOnHand()));
    }

    @Override
    public boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.update(new Item(dto.getItemCode(),dto.getDescription(),dto.getPackSize(),dto.getUnitPrice(),dto.getQtyOnHand()));
    }

    @Override
    public boolean itemExist(String code) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewItemCode() throws SQLException, ClassNotFoundException {
        return null;
    }
}
