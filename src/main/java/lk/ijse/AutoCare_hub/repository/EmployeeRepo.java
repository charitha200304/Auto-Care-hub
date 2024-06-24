package lk.ijse.AutoCare_hub.repository;
import lk.ijse.AutoCare_hub.db.DbConnection;
import lk.ijse.AutoCare_hub.model.Customer;
import lk.ijse.AutoCare_hub.model.Employee;
import lk.ijse.AutoCare_hub.model.Vehicle;
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
public class EmployeeRepo {
    private String Employee_id;
    private String Name;
    private String Address;
    private String Contact_number;

    public static List<Employee> getAll() throws SQLException {
        String sql = "SELECT * FROM employee";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Employee> employeeList = new ArrayList<>();
        while (resultSet.next()) {
            String Enployee_id = resultSet.getString(1);
            String Name = resultSet.getString(2);
            String Address = resultSet.getString(3);
            String Contact_number = resultSet.getString(4);


            Employee  employee = new Employee(Enployee_id, Name,Address, Contact_number);
            employeeList.add(employee);
        }
        return employeeList;
    }

    public static boolean delete(String Employee_id) throws SQLException {
        String sql = "DELETE FROM employee WHERE Employee_id=?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, Employee_id);

        return pstm.executeUpdate()>0;
    }

    public static boolean save(Employee employee) throws SQLException {
        String sql = "INSERT INTO employee VALUES(?, ?, ?, ?)";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1,  employee.getEmployee_id());
        pstm.setObject(2,  employee.getName());
        pstm.setObject(3,  employee.getAddress());
        pstm.setObject(4,  employee.getContact_number());


        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Employee employee) throws SQLException {
        String sql = "UPDATE employee SET Name=?, Address=?,Contact_number=? WHERE Employee_id=?;";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1,  employee.getName());
        pstm.setObject(2,  employee.getAddress());
        pstm.setObject(3,  employee.getContact_number());
        pstm.setObject(4,  employee.getEmployee_id());





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
}

