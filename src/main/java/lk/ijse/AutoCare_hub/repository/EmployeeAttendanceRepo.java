package lk.ijse.AutoCare_hub.repository;
import lk.ijse.AutoCare_hub.db.DbConnection;
import lk.ijse.AutoCare_hub.model.Customer;
import lk.ijse.AutoCare_hub.model.EmployeeAttendance;
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
public class EmployeeAttendanceRepo {
    private String Attendance_id;
    private String Employee_id;
    private String Date;
    private String In_time;
    private String Out_time;

    public static List<EmployeeAttendance> getAll() throws SQLException {
        String sql = "SELECT * FROM Attendance";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<EmployeeAttendance> employeeAttendanceList = new ArrayList<>();
        while (resultSet.next()) {
            String Attendance_id = resultSet.getString(1);
            String Employee_id = resultSet.getString(2);
            String Date = resultSet.getString(3);
            String In_time = resultSet.getString(4);
            String Out_time = resultSet.getString(5);


            EmployeeAttendance  employeeAttendance = new EmployeeAttendance( Attendance_id,Employee_id,Date, In_time,Out_time);
            employeeAttendanceList.add(employeeAttendance);
        }
        return employeeAttendanceList;
    }

    public static boolean delete(String attendance_id) throws SQLException {
        String sql = "DELETE FROM attendance WHERE Attendane_id=?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, attendance_id);

        return pstm.executeUpdate()>0;
    }

    public static boolean save(EmployeeAttendance employeeAttendance) throws SQLException {
        String sql = "INSERT INTO attendance VALUES(?, ?, ?, ?,?)";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1,  employeeAttendance.getAttendance_id());
        pstm.setObject(2,  employeeAttendance.getEmployee_id());
        pstm.setObject(3,  employeeAttendance.getDate());
        pstm.setObject(4,  employeeAttendance.getIn_time());
        pstm.setObject(5,  employeeAttendance.getOut_time());


        return pstm.executeUpdate() > 0;
    }

    public static boolean update(EmployeeAttendance employeeAttendance) throws SQLException {
        String sql = "UPDATE attendance SET Employee_id=?,Date=?, In_time=?,Out_time=? WHERE Attendance_id=?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1,  employeeAttendance.getEmployee_id());
        pstm.setObject(2,  employeeAttendance.getDate());
        pstm.setObject(3,  employeeAttendance.getIn_time());
        pstm.setObject(4,  employeeAttendance.getOut_time());
        pstm.setObject(5,  employeeAttendance.getAttendance_id());





        return pstm.executeUpdate() > 0;
    }


    public static List<String> getIds() throws SQLException {
        String sql = "SELECT Employee_id FROM employee";

        Connection connection = DbConnection.getInstance().getConnection();
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        List<String> idList = new ArrayList<>();

        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;
    }

    public int countemployeeattendance() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT COUNT(Employee_id) FROM attendance";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            int idd = Integer.parseInt(resultSet.getString(1));
            return idd;
        }
        return Integer.parseInt(null);
    }
    }

