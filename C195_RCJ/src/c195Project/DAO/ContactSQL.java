package c195Project.DAO;

import c195Project.model.Contacts;
import c195Project.util.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContactSQL {

    /**
     * returns all contacts that are found within the data base
     * @return
     * @throws SQLException
     */
    public static ObservableList<Contacts> getAllContact() throws SQLException {
        ObservableList<Contacts> contactsObservableList = FXCollections.observableArrayList();
        String contactsql = " SELECT * FROM contacts";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(contactsql);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            int contactID = resultSet.getInt("Contact_ID");
            String contactName = resultSet.getString("Contact_Name");
            String contactEmail = resultSet.getString("Email");

            Contacts contacts = new Contacts(contactID, contactName, contactEmail);
            contactsObservableList.add(contacts);

        }
        return contactsObservableList;
    }

}
