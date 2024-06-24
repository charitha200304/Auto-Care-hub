package lk.ijse.AutoCare_hub.model;

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
public class Service_History {
    private String SH_id;
    private String Description;
    private String V_id;

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT SH_id FROM service_history";

        Connection connection = DbConnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }
    }

