package lk.ijse.AutoCare_hub.repository;

import lk.ijse.AutoCare_hub.db.DbConnection;
import lk.ijse.AutoCare_hub.model.Service;
import lk.ijse.AutoCare_hub.model.Tm.ItemTm;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceRepo {
    public static boolean saveService(Service service) {
        try {
            PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO service values (?,?,?,?)");
            pstm.setObject(1,service.getServiceId());
            pstm.setObject(2,service.getCost());
            pstm.setObject(3,service.getDate());
            pstm.setObject(4,service.getVehicleId());

            return pstm.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean saveServiceDetails(ItemTm itemTm, String serviceId) {
        try {
            PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement("INSERT INTO Inventory_Service_Details values (?,?,?,?)");
            pstm.setObject(1,serviceId);
            pstm.setObject(2,itemTm.getItemId());
            pstm.setObject(3,itemTm.getTotal());
            pstm.setObject(4,itemTm.getQty());

            return pstm.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String fintLastServiceId() throws SQLException {
        String sql = "select * from service order by Service_id desc limit 1";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        String value = null;

        if (resultSet.next()) {
            value = resultSet.getString(1);
        }
        return value;
    }
}
