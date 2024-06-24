package lk.ijse.AutoCare_hub.repository;

import lk.ijse.AutoCare_hub.db.DbConnection;
import lk.ijse.AutoCare_hub.model.Customer;
import lk.ijse.AutoCare_hub.model.Feedback;
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
public class FeedbackRepo {
private String F_id;
private String Description;
private String Cus_id;

    public static List<Feedback> getAll() throws SQLException {
        String sql = "SELECT * FROM feedback";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Feedback> feedbackList = new ArrayList<>();
        while (resultSet.next()) {
            String F_id = resultSet.getString(1);
            String Description = resultSet.getString(2);
            String Cus_id = resultSet.getString(3);


            Feedback  feedback = new Feedback(F_id, Description,Cus_id);
            feedbackList.add(feedback);
        }
        return feedbackList;
    }

    public static boolean delete(String f_id) throws SQLException {
        String sql = "DELETE FROM feedback WHERE F_id=?";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, f_id);

        return pstm.executeUpdate()>0;
    }

    public static boolean save(Feedback feedback) throws SQLException {
        String sql = "INSERT INTO feedback VALUES(?, ?, ?)";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1,  feedback.getF_id());
        pstm.setObject(2,  feedback.getDescription());
        pstm.setObject(3,  feedback.getCus_id());


        return pstm.executeUpdate() > 0;
    }

    public static boolean update(Feedback feedback) throws SQLException {
        String sql = "UPDATE feedback SET Description=?,Cus_id=? WHERE F_id=?";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        pstm.setObject(1,  feedback.getDescription());
        pstm.setObject(2,  feedback.getCus_id());
        pstm.setObject(3,  feedback.getF_id());






        return pstm.executeUpdate() > 0;
    }
}
