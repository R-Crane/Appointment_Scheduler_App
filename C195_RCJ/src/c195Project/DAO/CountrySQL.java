package c195Project.DAO;

import c195Project.model.Countries;
import c195Project.util.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountrySQL {

    /**
     * returns the countries to create a listing in the countries combo box to allocate to a customers location when creating one
     * @return
     * @throws SQLException
     */
    public static ObservableList<Countries> getCountries() throws SQLException {

        ObservableList<Countries> filteredCountries = FXCollections.observableArrayList();
        String sql = "select * from countries";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

            int countryID = resultSet.getInt("Country_ID");
            String countryName = resultSet.getString("Country");
            Countries countrySQL = new Countries(countryID, countryName);
            filteredCountries.add(countrySQL);
        }
        return filteredCountries;
    }
}
