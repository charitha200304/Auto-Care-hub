package lk.ijse.AutoCare_hub.repository;

import javafx.collections.ObservableList;
import lk.ijse.AutoCare_hub.db.DbConnection;
import lk.ijse.AutoCare_hub.model.Customer;
import lk.ijse.AutoCare_hub.model.Service_History;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class Service_HistoryRepo {

    private String SH_id;
    private String Description;
    private String V_id;

    public static Service_History searchById(String serviceHistoryId) throws SQLException {
        String sql = "SELECT * FROM service_history WHERE SH_id=?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, serviceHistoryId);
        ResultSet resultSet = pstm.executeQuery();

        Service_History service_history = null;


        if (resultSet.next()) {
            String SH_id = resultSet.getString(1);
            String Description = resultSet.getString(2);
            String V_id = resultSet.getString(3);


            service_history = new Service_History(SH_id, Description, V_id);
        }
        return service_history;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT Cus_id FROM customer";

        Connection connection = DbConnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }

    public static List<Service_History> getAll() throws SQLException {
        String sql = "SELECT * FROM service_history";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Service_History> service_historyList = new ArrayList<>();
        while (resultSet.next()) {
            String SH_id = resultSet.getString(1);
            String Description = resultSet.getString(2);
            String V_id = resultSet.getString(3);


            Service_History service_history = new Service_History(SH_id, Description, V_id);
            service_historyList.add(service_history);
        }
        return service_historyList;
    }
    public static boolean delete(String sh_id) throws SQLException {
        String sql = "DELETE FROM service_history WHERE SH_id=?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, sh_id);

        return pstm.executeUpdate()>0;
    }

    public static boolean save(Service_History service_history) throws SQLException {
        String sql = "INSERT INTO service_history VALUES(?, ?, ?)";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1,  service_history.getSH_id());
        pstm.setObject(2,  service_history.getDescription());
        pstm.setObject(3,  service_history.getV_id());


        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Service_History service_history) throws SQLException {
        String sql = "UPDATE service_history SET Description=?,V_id=? WHERE SH_id=?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1,  service_history.getSH_id());
        pstm.setObject(2,  service_history.getDescription());
        pstm.setObject(3,  service_history.getV_id());





        return pstm.executeUpdate() > 0;
    }

}
