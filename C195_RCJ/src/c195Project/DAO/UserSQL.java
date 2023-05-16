package c195Project.DAO;

import c195Project.model.User;
import c195Project.util.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserSQL {

    /**
     * This will get all users by completing the query and contacting the data base - this returns a listing of users to populate a combo box
     * and to verify login in information for the existing users
     * @return
     * @throws SQLException
     */
    public static ObservableList<User> getAllUsers() throws SQLException {
        ObservableList<User> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM users";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int userID = resultSet.getInt("user_ID");
            String userName = resultSet.getString("User_Name");
            User user = new User(userID, userName);
            list.add(user);
        }
        return list;
    }

    /**
     *
     * @param ID
     * @return
     * @throws SQLException
     */
    public static User getUser(int ID) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_ID = ?";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1, ID);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            String userName = resultSet.getString("User_Name");
            User user = new User(ID, userName);
            return user;
        }
        return null;
    }

    /**
     *
     * this validates the users log in information for the application
     * @param username
     * @param password
     * @return
     * @throws SQLException
     */
    public static User validateUser(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_name = ? and password = ?";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int ID = resultSet.getInt("user_ID");
            User user = new User(ID, username);
            return user;
        }
        return null;
    }
}
