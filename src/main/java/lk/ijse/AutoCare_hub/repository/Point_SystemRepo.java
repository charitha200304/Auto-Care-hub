package lk.ijse.AutoCare_hub.repository;

import lk.ijse.AutoCare_hub.db.DbConnection;
import lk.ijse.AutoCare_hub.model.Customer;
import lk.ijse.AutoCare_hub.model.Point_System;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Point_SystemRepo {
    private String Point_id;
    private String Total_point;
    private String Cus_id;
    private String SH_id;

    public static List<Point_System> getAll() throws SQLException {
        String sql = "SELECT * FROM point_system";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Point_System> point_systemList = new ArrayList<>();
        while (resultSet.next()) {
            String Point_id = resultSet.getString(1);
            String Total_point = resultSet.getString(2);
            String Cus_id = resultSet.getString(3);
            String SeviceHistory_id = resultSet.getString(4);


            Point_System  point_system = new Point_System(Point_id, Total_point,Cus_id,SeviceHistory_id);
            point_systemList.add(point_system);
        }
        return point_systemList;
    }

    public static boolean delete(String point_id) throws SQLException {
        String sql = "DELETE FROM point_system WHERE Point_id=?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, point_id);

        return pstm.executeUpdate()>0;
    }

    public static boolean save(Point_System point_system) throws SQLException {
        String sql = "INSERT INTO point_system VALUES(?, ?, ?, ?)";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1,  point_system.getPoint_id());
        pstm.setObject(2,  point_system.getTotal_point());
        pstm.setObject(3,  point_system.getCus_id());
        pstm.setObject(4,  point_system.getSh_id());


        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Point_System point_system) throws SQLException {
        String sql = "UPDATE point_system SET Total_point=?, Cus_id=?,SH_id = ? WHERE Point_id =?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1,  point_system.getTotal_point());
        pstm.setObject(2,  point_system.getCus_id());
        pstm.setObject(3,  point_system.getSh_id());
        pstm.setObject(4,  point_system.getPoint_id());





        return pstm.executeUpdate() > 0;
    }

    public static Point_System search(String cus_id) throws SQLException {
        String sql = "SELECT * FROM point_system WHERE Cus_id=?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, cus_id);
        ResultSet resultSet = pstm.executeQuery();
        Point_System value = null;

        if (resultSet.next()) {
            String Point_id = resultSet.getString(1);
            String Total_point = resultSet.getString(2);
            String Cus_id = resultSet.getString(3);
            String SH_id = resultSet.getString(4);
            value=new Point_System(Point_id, Total_point, Cus_id, SH_id);
        }
        return value;
    }

    public static String fintlastPoinId() throws SQLException {
        String sql = "select * from point_system order by Point_id desc limit 1";

        PreparedStatement pstm = DbConnection.getInstance().getConnection().prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        String value = null;

        if (resultSet.next()) {
            value = resultSet.getString(1);
        }
        return value;
    }
}
