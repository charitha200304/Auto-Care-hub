package lk.ijse.AutoCare_hub.repository;
import lk.ijse.AutoCare_hub.db.DbConnection;
import lk.ijse.AutoCare_hub.model.Payment;
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
@AllArgsConstructor
@Data
public class PaymentRepo {
    private String Payment_id;
    private String Amount;
    private String Date;
    private String Payment_methods;
    private String Cus_id;

    public static List<Payment> getAll() throws SQLException {
        String sql = "SELECT * FROM payment";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Payment> paymentList = new ArrayList<>();
        while (resultSet.next()) {
            String Payment_id = resultSet.getString(1);
            String Amount = resultSet.getString(2);
            String Date = resultSet.getString(3);
            String Payment_methods = resultSet.getString(4);
            String Cus_id = resultSet.getString(5);


            Payment  payment = new Payment(Payment_id, Amount,Date, Payment_methods,Cus_id);
            paymentList.add(payment);
        }
        return paymentList;
    }

    public static boolean delete(String payment_id) throws SQLException {
        String sql = "DELETE FROM payment WHERE Payment_id=?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, payment_id);

        return pstm.executeUpdate()>0;
    }

    public static boolean save(Payment payment) throws SQLException {
        String sql = "INSERT INTO payment VALUES(?, ?, ?, ?,?)";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1,  payment.getPayment_id());
        pstm.setObject(2,  payment.getAmount());
        pstm.setObject(3,  payment.getDate());
        pstm.setObject(4,  payment.getPayment_methods());
        pstm.setObject(5,  payment.getCus_id());


        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Payment payment) throws SQLException {
        String sql = "UPDATE payment SET  Amount=?,Date=?, Payment_methods=?,Cus_id =? WHERE Payment_id=?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1,  payment.getAmount());
        pstm.setObject(2,  payment.getDate());
        pstm.setObject(3,  payment.getPayment_methods());
        pstm.setObject(4,  payment.getCus_id());
        pstm.setObject(5,  payment.getPayment_id());






        return pstm.executeUpdate() > 0;
    }
}
