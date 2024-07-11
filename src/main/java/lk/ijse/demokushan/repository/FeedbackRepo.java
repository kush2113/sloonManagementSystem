package lk.ijse.demokushan.repository;

import javafx.scene.control.Alert;
import lk.ijse.demokushan.db.DbConnection;
import lk.ijse.demokushan.model.Feedback;
import lk.ijse.demokushan.model.Supplier;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FeedbackRepo {

    public static String getCurrentId() throws SQLException {
        String sql = "SELECT feedbackId FROM  feedback ORDER BY feedbackId DESC LIMIT 1";
        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String feedbackId = resultSet.getString(1);
            return feedbackId;
        }
        return null;
    }


    public static boolean save(Feedback feedback) throws SQLException {
        String sql = "INSERT INTO feedback VALUES(?, ?, ?)";

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, feedback.getFeedbackId());
        pstm.setObject(2, feedback.getComment());
        pstm.setObject(3, feedback.getAppointmentId());

        return pstm.executeUpdate() > 0;
    }


    public static List<Feedback> getAll() throws SQLException{
        String sql = "SELECT * FROM feedback";

        PreparedStatement pstm = DbConnection.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Feedback> feedbackList = new ArrayList<>();

        while (resultSet.next()) {
            String feedbackId = resultSet.getString(1);
            String comment = resultSet.getString(2);
            String appointmentId = resultSet.getString(3);

            Feedback feedback = new Feedback(feedbackId, comment, appointmentId );
            feedbackList.add(feedback);
        }
        return feedbackList;
    }
}
