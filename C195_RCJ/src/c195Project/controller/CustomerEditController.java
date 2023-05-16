package c195Project.controller;

import c195Project.DAO.CountrySQL;
import c195Project.DAO.FirstLevelDivisionSQL;
import c195Project.model.Countries;
import c195Project.model.Customer;
import c195Project.model.FirstLevelDivisions;
import c195Project.util.JDBC;
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

public class CustomerEditController implements Initializable {


    public ComboBox<FirstLevelDivisions> divisonStateCombo;
    public TextField customerPostalTXT;
    public TextField customerPhoneTXT;
    public TextField customerAddressTXT;
    public TextField customerNameTXT;
    public TextField customerIDTXT;
    public Button completeEdit;
    public Button cancel;
    public ComboBox<Countries> countryCombo;
    private Customer customerSelected;

    /**
     * handles the edit button when pressed and updates customer record information
     * @param actionEvent
     */
    public void onEditCustomer(ActionEvent actionEvent) {

        try {

            if (customerNameTXT.getText().isEmpty()) {
                this.AlertMessage(2);
                return;
            }

            if (customerAddressTXT.getText().isEmpty()) {
                this.AlertMessage(2);
                return;
            }

            if (customerPostalTXT.getText().isEmpty()) {
                this.AlertMessage(2);
                return;
            }

            if (divisonStateCombo.getSelectionModel().isEmpty()) {
                this.AlertMessage(3);
                return;
            }

            String update = "UPDATE customers SET Customer_ID = ?, Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Create_Date = ?, Created_By = ?, " +
                    "Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(update);

            preparedStatement.setInt(1, Integer.parseInt(customerIDTXT.getText()));
            preparedStatement.setString(2, customerNameTXT.getText());
            preparedStatement.setString(3, customerAddressTXT.getText());
            preparedStatement.setString(4, customerPostalTXT.getText());
            preparedStatement.setString(5, customerPhoneTXT.getText());
            preparedStatement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(7, "USER");
            preparedStatement.setTimestamp(8, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(9, "USER");
            preparedStatement.setInt(10, getDivisionID(divisonStateCombo.getValue()));
            preparedStatement.setInt(11, Integer.parseInt(customerIDTXT.getText()));
            preparedStatement.executeUpdate();

            Parent customerAdd = (Parent) FXMLLoader.load(this.getClass().getResource("../view/MainMenu.fxml"));
            Scene scene = new Scene(customerAdd);
            Stage stageEditCustomer = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stageEditCustomer.setTitle("C195 Application");
            stageEditCustomer.setScene(scene);
            stageEditCustomer.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * handles the cancel function and returns to the main menu
     * @param actionEvent
     * @throws IOException
     */
    public void clickCancelButton(ActionEvent actionEvent) throws IOException {
        Alert cancel = new Alert(Alert.AlertType.INFORMATION);
        cancel.setTitle("Cancel Changes");
        cancel.setContentText("About to cancel - Do you wish to continue?");
        Optional<ButtonType> choice = cancel.showAndWait();
        if (choice.isPresent() && choice.get() == ButtonType.OK) {
            Parent customerAdd = (Parent) FXMLLoader.load(this.getClass().getResource("../view/MainMenu.fxml"));
            Scene scene = new Scene(customerAdd);
            Stage stageAddCustomer = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stageAddCustomer.setTitle("C195 Application");
            stageAddCustomer.setScene(scene);
            stageAddCustomer.show();
        }
    }

    /**
     * this function provides ability to tie first level disions to the combo box and match with its country
     * @param division
     * @return
     * @throws SQLException
     */
    public int getDivisionID(FirstLevelDivisions division) throws SQLException {
        int divisionID = -1;

        Statement statement = JDBC.getConnection().createStatement();

        String divisionSQL = "Select Division_ID FROM first_level_divisions WHERE first_level_divisions.Division = '" + division + "'";

        ResultSet resultSet = statement.executeQuery(divisionSQL);

        while (resultSet.next()) {
            divisionID = resultSet.getInt("Division_ID");
        }
        return divisionID;
    }

    /**
     * this is handling the errors before updating a customer record
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
     * pulls the records in the data base and populates the text fields to edit
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.customerSelected = MainMenuController.getCustomerEdit();

        this.customerIDTXT.setText(String.valueOf(this.customerSelected.getCustomerID()));
        this.customerNameTXT.setText(String.valueOf(this.customerSelected.getCustomerName()));
        this.customerAddressTXT.setText(String.valueOf(this.customerSelected.getCustomerAddress()));
        this.customerPhoneTXT.setText(String.valueOf(this.customerSelected.getCustomerPhone()));
        this.customerPostalTXT.setText(String.valueOf(this.customerSelected.getCustomerPostal()));


        try {
            countryCombo.setItems(CountrySQL.getCountries());
//            countryCombo.setPromptText("Choose A Country");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * this prevents the first level division combo from populating before a country is chosen
     * @param actionEvent
     */
    public void onCountryChange(ActionEvent actionEvent) {
        Countries countries = countryCombo.getValue();
        try {
            divisonStateCombo.setItems(FirstLevelDivisionSQL.getFirstLevelDivisionsByCountry(countries.getCountryID()));
            divisonStateCombo.setValue(null);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
