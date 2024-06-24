package lk.ijse.AutoCare_hub.repository;
import lk.ijse.AutoCare_hub.db.DbConnection;
import lk.ijse.AutoCare_hub.model.Inventory_item;
import lk.ijse.AutoCare_hub.model.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Inventory_itemRepo {
    public static List<Inventory_item> getAll() throws SQLException {
        String sql = "SELECT * FROM Inventory_item";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Inventory_item> inventory_itemList = new ArrayList<>();
        while (resultSet.next()) {
            String Item_id = resultSet.getString(1);
            String  Description = resultSet.getString(2);
            String Price = resultSet.getString(3);
            String Qty_On_Hand = resultSet.getString(4);


            Inventory_item  inventory_item = new Inventory_item(Item_id, Description,Price, Qty_On_Hand);
            inventory_itemList.add(inventory_item);
        }
        return inventory_itemList;
    }

    public static boolean delete(String Item_id) throws SQLException {
        String sql = "DELETE FROM inventory_item WHERE Item_id=?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, Item_id );

        return pstm.executeUpdate()>0;
    }

    public static boolean save(Inventory_item inventory_item) throws SQLException {
        String sql = "INSERT INTO inventory_item VALUES(?, ?, ?, ?)";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1,  inventory_item.getItem_id());
        pstm.setObject(2,  inventory_item.getDescription());
        pstm.setObject(3,  inventory_item.getPrice());
        pstm.setObject(4,  inventory_item.getQty_On_Hand());


        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Inventory_item inventory_item) throws SQLException {
        String sql = "UPDATE inventory_item SET Description=?, Price=?,Qty_On_Hand=? WHERE Item_id=?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1,  inventory_item.getDescription());
        pstm.setObject(2,  inventory_item.getPrice());
        pstm.setObject(3,  inventory_item.getQty_On_Hand());
        pstm.setObject(4,  inventory_item.getItem_id());



        return pstm.executeUpdate() > 0;
    }

    public static ResultSet searchItem(String itemId) {
        try {
            PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("select * from inventory_item where Item_id=?");
            pstm.setObject(1,itemId);
            return pstm.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
