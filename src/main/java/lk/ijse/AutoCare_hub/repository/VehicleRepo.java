package lk.ijse.AutoCare_hub.repository;

import lk.ijse.AutoCare_hub.db.DbConnection;
import lk.ijse.AutoCare_hub.model.Customer;
import lk.ijse.AutoCare_hub.model.Vehicle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.jfoenix.controls.JFXButton;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class VehicleRepo {
    private String V_id;
    private String Model;
    private String Type;
    private String Cus_id;

    public static List<Vehicle> getAll() throws SQLException {
        String sql = "SELECT * FROM vehicle";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Vehicle> vehicleList = new ArrayList<>();
        while (resultSet.next()) {
            String V_id = resultSet.getString(1);
            String Model = resultSet.getString(2);
            String Type = resultSet.getString(3);
            String Cus_id = resultSet.getString(4);


            Vehicle  vehicle = new Vehicle(V_id, Model,Type, Cus_id);
            vehicleList.add(vehicle);
        }
        return vehicleList;
    }

    public static boolean delete(String v_id) throws SQLException {
        String sql = "DELETE FROM vehicle WHERE V_id=?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, v_id);

        return pstm.executeUpdate()>0;
    }

    public static boolean save(Vehicle vehicle) throws SQLException {
        String sql = "INSERT INTO vehicle VALUES(?, ?, ?, ?)";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1,  vehicle.getV_id());
        pstm.setObject(2,  vehicle.getModel());
        pstm.setObject(3,  vehicle.getType());
        pstm.setObject(4,  vehicle.getCus_id());


        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Vehicle vehicle) throws SQLException {
        String sql = "UPDATE vehicle SET Model=?, Type=?,Cus_id=? WHERE V_id=?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1,  vehicle.getModel());
        pstm.setObject(2,  vehicle.getType());
        pstm.setObject(3,  vehicle.getCus_id());
        pstm.setObject(4,  vehicle.getV_id());





        return pstm.executeUpdate() > 0;
    }

    public static Vehicle searchById(String id) throws SQLException {
        String sql = "SELECT * FROM vehicle WHERE V_id=?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1,id);
        ResultSet resultSet = pstm.executeQuery();

        Vehicle vehicle = null;




        if (resultSet.next()) {
            String V_id = resultSet.getString(1);
            String Model = resultSet.getString(2);
            String Type = resultSet.getString(3);
            String Cus_id = resultSet.getString(4);


            vehicle = new Vehicle( V_id, Model,Type,Cus_id);
        }
        return vehicle;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT V_id FROM vehicle";

        Connection connection = DbConnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }
}
