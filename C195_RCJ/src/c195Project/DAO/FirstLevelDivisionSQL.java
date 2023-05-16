package c195Project.DAO;

import c195Project.model.FirstLevelDivisions;
import c195Project.util.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FirstLevelDivisionSQL  {

    /**
     * This method was overwritten I believe and the query above is the only one called after instructor assistance
     * @return
     * @throws SQLException
     */
    public static ObservableList<FirstLevelDivisions> getFirstLevelDivisions() throws SQLException {

        ObservableList<FirstLevelDivisions> filteredDivisions = FXCollections.observableArrayList();
        String sql = "select * from first_level_divisions";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){

            int divisionID = resultSet.getInt("Division_ID");
            String divisionName = resultSet.getString("Division");
            int countryID = resultSet.getInt("Country_ID");
            FirstLevelDivisions firstLevelDivisions = new FirstLevelDivisions(divisionID, divisionName,countryID);
            filteredDivisions.add(firstLevelDivisions);
        }
            return filteredDivisions;
    }

    /**
     * gets all first level divisions and joins on countries table to create combo boxes found in the add and edit customer pages
     * @param c_ID
     * @return
     * @throws SQLException
     */
    public static ObservableList<FirstLevelDivisions> getFirstLevelDivisionsByCountry(int c_ID) throws SQLException {

        ObservableList<FirstLevelDivisions> filteredDivisions = FXCollections.observableArrayList();
        String sql = "select * from first_level_divisions as d INNER JOIN Countries as c ON d.Country_ID = c.Country_ID and c.Country_ID = ?";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1,c_ID );
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){

            int divisionID = resultSet.getInt("Division_ID");
            String divisionName = resultSet.getString("Division");
            int countryID = resultSet.getInt("Country_ID");
            FirstLevelDivisions firstLevelDivisions = new FirstLevelDivisions(divisionID, divisionName,countryID);
            filteredDivisions.add(firstLevelDivisions);
        }
        return filteredDivisions;
    }

    /**
     * This method was overwritten I believe and the query above is the only one called after instructor assistance
     * @param divID
     * @return
     * @throws SQLException
     */
    public static FirstLevelDivisions getFirstLevelDivision(int divID) throws SQLException {

        String sql = "select * from first_level_divisions WHERE Division_ID = ?";
        PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);
        preparedStatement.setInt(1,divID );
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()){

            int divisionID = resultSet.getInt("Division_ID");
            String divisionName = resultSet.getString("Division");
            int countryID = resultSet.getInt("Country_ID");
            FirstLevelDivisions firstLevelDivisions = new FirstLevelDivisions(divisionID, divisionName,countryID);
            return firstLevelDivisions;
        }
        return null;
    }
}
