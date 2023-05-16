package c195Project.controller;

import c195Project.DAO.CountrySQL;
import c195Project.DAO.CustomerSQL;
import c195Project.DAO.FirstLevelDivisionSQL;
import c195Project.model.Countries;
import c195Project.model.Customer;
import c195Project.model.FirstLevelDivisions;
import c195Project.util.JDBC;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class CustomerAddController implements Initializable {

    public ComboBox<FirstLevelDivisions> divisionCombo;
    public TextField postalTXT;
    public TextField phoneTXT;
    public TextField addressTXT;
    public TextField nameTXT;
    public TextField customerIDTXT;
    public Button customerAdd;
    public ComboBox<Countries> countryCombo;

    /**
     * this handles canceling the actions and returning to the main menu
     * @param event
     * @throws IOException
     */
    public void clickCancelButton(ActionEvent event) throws IOException {
        Alert cancel = new Alert(Alert.AlertType.INFORMATION);
        cancel.setTitle("Cancel Changes");
        cancel.setContentText("About to cancel - Do you wish to continue?");
        Optional<ButtonType> choice = cancel.showAndWait();
        if (choice.isPresent() && choice.get() == ButtonType.OK) {
            Parent customerAdd = (Parent) FXMLLoader.load(this.getClass().getResource("../view/MainMenu.fxml"));
            Scene scene = new Scene(customerAdd);
            Stage stageAddCustomer = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stageAddCustomer.setTitle("C195 Application");
            stageAddCustomer.setScene(scene);
            stageAddCustomer.show();
        }
    }

    /** this handles saving the customer record information to the database
     *
     * @param actionEvent
     */
    public void onCustomerAdd(ActionEvent actionEvent) {
        try {

            if (nameTXT.getText().isEmpty()){
                this.AlertMessage(2);
                return;
            }

            if (addressTXT.getText().isEmpty()){
                this.AlertMessage(2);
                return;
            }

            if (postalTXT.getText().isEmpty()){
                this.AlertMessage(2);
                return;
            }

            if (divisionCombo.getSelectionModel().isEmpty()) {
                this.AlertMessage(3);
                return;
            }

            String addStatement = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(addStatement);

            preparedStatement.setString(1,nameTXT.getText());
            preparedStatement.setString(2,addressTXT.getText());
            preparedStatement.setString(3,postalTXT.getText());
            preparedStatement.setString(4, phoneTXT.getText());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(6, "USER");
            preparedStatement.setTimestamp(7,Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(8,"USER");
            preparedStatement.setInt(9, getDivisionID(divisionCombo.getValue()));
            preparedStatement.executeUpdate();

            Parent customerAdd = (Parent) FXMLLoader.load(this.getClass().getResource("../view/MainMenu.fxml"));
            Scene scene = new Scene(customerAdd);
            Stage stageAddCustomer = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            stageAddCustomer.setTitle("C195 Application");
            stageAddCustomer.setScene(scene);
            stageAddCustomer.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param division
     * @return
     * @throws SQLException
     */
    public int getDivisionID(FirstLevelDivisions division) throws SQLException {
        int divisionID = -1;

        Statement statement = JDBC.getConnection().createStatement();

        String divisionSQL = "Select Division_ID FROM first_level_divisions WHERE first_level_divisions.Division = '" + division + "'";

        ResultSet resultSet = statement.executeQuery(divisionSQL);

        while (resultSet.next()){
            divisionID = resultSet.getInt("Division_ID");
        }
        return divisionID;
    }

    public void onCountryChange(ActionEvent actionEvent) {
        Countries countries = countryCombo.getValue();

        try {
            divisionCombo.setItems(FirstLevelDivisionSQL.getFirstLevelDivisionsByCountry(countries.getCountryID()));
            divisionCombo.setValue(null);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * this is the list of alert messages to handle error exceptions
     * @param message
     */
    private void AlertMessage(int message) {
        Alert error;
        if (message == 2) {
            error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Exception!!");
            error.setContentText("FIELD CAN NOT BE LEFT BLANK");
            error.showAndWait();
        }

        if (message == 3) {
            error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Exception!!");
            error.setContentText("YOU MUST CHOOSE A STATE");
            error.showAndWait();
        }

    }

    /**
     * this initializes the country combo box before a first level division can be chosen
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            countryCombo.setItems(CountrySQL.getCountries());
            countryCombo.setPromptText("Choose A Country");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
