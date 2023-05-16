package c195Project.DAO;

import c195Project.model.Appointment;
import c195Project.model.Customer;
import c195Project.model.ReportItemTotal;
import c195Project.util.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportMenuSQL {

    /**
     *
     * @return
     * @throws SQLException
     */
    public static ObservableList<Customer> getUSCustomer() throws SQLException {
        ObservableList<Customer> list = FXCollections.observableArrayList();
        String sql = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, customers.Postal_Code, customers.Phone, customers.Division_ID, first_level_divisions.Division, Countries.country from customers INNER JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID INNER JOIN countries on first_level_divisions.Country_ID = Countries.Country_ID AND Countries.country = \"U.S\"";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        ObservableList<Customer> customerUSObservableList = FXCollections.observableArrayList();

        while (resultSet.next()) {
            int customerID = resultSet.getInt("Customer_ID");
            String customerName = resultSet.getString("Customer_Name");
            String customerAddress = resultSet.getString("Address");
            String customerPostal = resultSet.getString("Postal_Code");
            String customerPhone = resultSet.getString("Phone");
            int divisionID = resultSet.getInt("Division_ID");
            String divisionName = resultSet.getString("Division");
            String countryName = resultSet.getString("country");

            Customer customer = new Customer(customerID, customerName, customerAddress, customerPostal, customerPhone, divisionName, divisionID, countryName);
            customerUSObservableList.add(customer);

        }
        return customerUSObservableList;
    }

    /**
     * This will retun a listing that is filtered through the query that shows appointments by Type
     * @return
     * @throws SQLException
     */
    public static ObservableList<ReportItemTotal> getByTypeReport() throws SQLException {
        ObservableList<ReportItemTotal> list = FXCollections.observableArrayList();
        String sql = "SELECT Type, Count(type) as cnt FROM Appointments GROUP BY Type ORDER BY Type";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String type = resultSet.getString("Type");
            int count = resultSet.getInt("cnt");

            ReportItemTotal typeTotal = new ReportItemTotal(type, count);
            list.add(typeTotal);
        }
        return list;
    }

    /**
     *
     * This will retun a listing that is filtered through the query that shows appointments by Month
     * @return
     * @throws SQLException
     */
    public static ObservableList<ReportItemTotal> getByMonth() throws SQLException {
        ObservableList<ReportItemTotal> monthlist = FXCollections.observableArrayList();
        String sql = "SELECT monthname(Start) as Month, count(*) as total from appointments group by monthname(Start)";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            String month = resultSet.getString("Month");
            int count = resultSet.getInt("total");

            ReportItemTotal monthTotal = new ReportItemTotal(month, count);
            monthlist.add(monthTotal);
        }
        return monthlist;
    }
}
