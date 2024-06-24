package lk.ijse.AutoCare_hub.repository;

import javafx.collections.ObservableList;
import lk.ijse.AutoCare_hub.db.DbConnection;
import lk.ijse.AutoCare_hub.model.Customer;
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
public class CustomerRepo {
    private String Cus_id;
    private String Name;
    private String Date;
    private String Contact_number;

    public static List<Customer> getAll() throws SQLException {
        String sql = "SELECT * FROM customer";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Customer> customerList = new ArrayList<>();
        while (resultSet.next()) {
            String Cus_id = resultSet.getString(1);
            String Name = resultSet.getString(2);
            String Date = resultSet.getString(3);
            String Contact_number = resultSet.getString(4);


            Customer  customer = new Customer(Cus_id, Name,Date, Contact_number);
            customerList.add(customer);
        }
        return customerList;
    }

    public static boolean delete(String cus_id) throws SQLException {
        String sql = "DELETE FROM customer WHERE Cus_id=?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, cus_id);

        return pstm.executeUpdate()>0;
    }

    public static boolean save(Customer customer) throws SQLException {
        String sql = "INSERT INTO customer VALUES(?, ?, ?, ?)";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1,  customer.getCus_id());
        pstm.setObject(2,  customer.getName());
        pstm.setObject(3,  customer.getDate());
        pstm.setObject(4,  customer.getContact_number());


        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Customer customer) throws SQLException {
        String sql = "UPDATE customer SET name=?,Date=?, Contact_number=? WHERE Cus_id=?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1,  customer.getName());
        pstm.setObject(2,  customer.getDate());
        pstm.setObject(3,  customer.getContact_number());
        pstm.setObject(4,  customer.getCus_id());





        return pstm.executeUpdate() > 0;
    }

    public static Customer searchById(String customerId) throws SQLException {
        String sql = "SELECT * FROM customer WHERE Cus_id=?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1,customerId);
        ResultSet resultSet = pstm.executeQuery();

        Customer customer = null;

        if (resultSet.next()) {
            String cus_id = resultSet.getString(1);
            String customer_name = resultSet.getString(2);
            String date = resultSet.getString(3);
            String customer_contact_number = resultSet.getString(4);


            customer = new Customer( cus_id, customer_name,date,customer_contact_number);
        }
        return customer;
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

    public static Customer searchByContact(String contact) throws SQLException {
        String sql = "SELECT * FROM customer WHERE Contact_number=?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1, contact);
        ResultSet resultSet = pstm.executeQuery();
        Customer customer = null;

        if (resultSet.next()) {
            String cus_id = resultSet.getString(1);
            String customer_name = resultSet.getString(2);
            String date = resultSet.getString(3);
            String customer_contact_number = resultSet.getString(4);
            customer = new Customer( cus_id, customer_name,date,customer_contact_number);
        }
        return customer;
    }

    public int countDailyCustomer() throws SQLException {
        Connection connection = DbConnection.getInstance().getConnection();
        String sql = "SELECT COUNT(Cus_id) FROM customer";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            int idd = Integer.parseInt(resultSet.getString(1));
            return idd;
        }
        return Integer.parseInt(null);
    }
    }



