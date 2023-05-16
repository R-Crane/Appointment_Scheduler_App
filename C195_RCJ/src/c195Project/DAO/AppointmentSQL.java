package c195Project.DAO;

import c195Project.model.Appointment;
import c195Project.model.Contacts;
import c195Project.model.User;
import c195Project.util.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class AppointmentSQL {

    /**
     * this returns a listing of all appointments that are existing
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        ObservableList<Appointment> list = FXCollections.observableArrayList();
        String sql = "select * from appointments";

        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<Appointment> appointmentObservableList = FXCollections.observableArrayList();

        while (resultSet.next()){

            int appointmentID = resultSet.getInt("Appointment_ID");
            String appointmentTitle = resultSet.getString("Title");
            String appointmentDesc = resultSet.getString("Description");
            String appointmentLoc = resultSet.getString("Location");
            String appointmentType = resultSet.getString("Type");
            LocalDateTime begin = resultSet.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = resultSet.getTimestamp("End").toLocalDateTime();
            int customerID = resultSet.getInt("Customer_ID");
            int userID = resultSet.getInt("User_ID");
            int contactID = resultSet.getInt("Contact_ID");

            Appointment appointment = new Appointment(appointmentID, appointmentTitle, appointmentDesc, appointmentLoc, appointmentType,
                    begin, end, customerID, userID, contactID);
            appointmentObservableList.add(appointment);
        }
        return appointmentObservableList;
    }

    /**
     *  this was created to return the contacts into a listing to populate the contacts combo box in the add and edit appointments panels
     * @param contactID
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointment> getByContact(int contactID) throws SQLException {
        ObservableList<Appointment> list = FXCollections.observableArrayList();
        for (Appointment c : getAllAppointments()) {
            if (c.getContactID() == contactID){
                list.add(c);
            }
        }
        return list;
    }


    /**
     * this is called when the login form is checking to see if a user has an upcoming appointment within 15 minutes of their log in time
     * @param userID
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointment> getUpcomingAppointments(int userID) throws SQLException {
        ObservableList<Appointment> list = FXCollections.observableArrayList();
        String sql = "select * from appointments WHERE user_ID = ? AND Start Between Now() AND Date_Add(Now(), Interval 15 Minute)";

        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, userID);
        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<Appointment> appointmentObservableList = FXCollections.observableArrayList();

        while (resultSet.next()){

            int appointmentID = resultSet.getInt("Appointment_ID");
            String appointmentTitle = resultSet.getString("Title");
            String appointmentDesc = resultSet.getString("Description");
            String appointmentLoc = resultSet.getString("Location");
            String appointmentType = resultSet.getString("Type");
            LocalDateTime begin = resultSet.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = resultSet.getTimestamp("End").toLocalDateTime();
            int customerID = resultSet.getInt("Customer_ID");
//            int userID = resultSet.getInt("User_ID");
            int contactID = resultSet.getInt("Contact_ID");

            Appointment appointment = new Appointment(appointmentID, appointmentTitle, appointmentDesc, appointmentLoc, appointmentType,
                    begin, end, customerID, userID, contactID);
            appointmentObservableList.add(appointment);
        }
        return appointmentObservableList;
    }

    /**
     * instructor assisted - this is used to get all appointments and check customer ID to prevent overlapping of appointments
     * @param CustID
     * @return
     * @throws SQLException
     */
    public static ObservableList<Appointment> getAllAppointmentsByCustomer(int CustID) throws SQLException {
        ObservableList<Appointment> list = FXCollections.observableArrayList();
        String sql = "select * from appointments WHERE customer_ID = ?";

        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, CustID);
        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<Appointment> appointmentObservableList = FXCollections.observableArrayList();

        while (resultSet.next()){

            int appointmentID = resultSet.getInt("Appointment_ID");
            String appointmentTitle = resultSet.getString("Title");
            String appointmentDesc = resultSet.getString("Description");
            String appointmentLoc = resultSet.getString("Location");
            String appointmentType = resultSet.getString("Type");
            LocalDateTime begin = resultSet.getTimestamp("Start").toLocalDateTime();
            LocalDateTime end = resultSet.getTimestamp("End").toLocalDateTime();
            int customerID = resultSet.getInt("Customer_ID");
            int userID = resultSet.getInt("User_ID");
            int contactID = resultSet.getInt("Contact_ID");

            Appointment appointment = new Appointment(appointmentID, appointmentTitle, appointmentDesc, appointmentLoc, appointmentType,
                    begin, end, customerID, userID, contactID);
            appointmentObservableList.add(appointment);
        }
        return appointmentObservableList;
    }



}
