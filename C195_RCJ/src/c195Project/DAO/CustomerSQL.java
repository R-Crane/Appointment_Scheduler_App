package c195Project.DAO;

import c195Project.model.Customer;
import c195Project.util.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerSQL {

    /**
     * returns all customers listed in the data base to populate combo boxes found within the project
     * @return
     * @throws SQLException
     */
    public static ObservableList<Customer> getAllCustomer() throws SQLException {
        ObservableList<Customer> list = FXCollections.observableArrayList();
        String sql = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, customers.Phone, customers.Division_ID, first_level_divisions.Division, Countries.country from customers INNER JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID INNER JOIN countries on first_level_divisions.Country_ID = Countries.Country_ID";

        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();

        while (resultSet.next()){
            int customerID = resultSet.getInt("Customer_ID");
            String customerName = resultSet.getString("Customer_Name");
            String customerAddress = resultSet.getString("Address");
            String customerPostal = resultSet.getString("Postal_Code");
            String customerPhone = resultSet.getString("Phone");
            int divisionID = resultSet.getInt("Division_ID");
            String divisionName = resultSet.getString("Division");
            String countryName = resultSet.getString("Country");

            Customer customer = new Customer(customerID, customerName, customerAddress, customerPostal, customerPhone, divisionName, divisionID, countryName);
            customerObservableList.add(customer);
        }
        return customerObservableList;
        }
    }



