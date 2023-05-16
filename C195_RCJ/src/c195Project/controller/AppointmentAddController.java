package c195Project.controller;

import c195Project.DAO.AppointmentSQL;
import c195Project.DAO.ContactSQL;
import c195Project.DAO.CustomerSQL;
import c195Project.DAO.UserSQL;
import c195Project.model.Appointment;
import c195Project.model.Contacts;
import c195Project.model.Customer;
import c195Project.model.User;
import c195Project.util.JDBC;
import javafx.collections.FXCollections;
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
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentAddController implements Initializable {


    public DatePicker endDateCombo;
    public ComboBox <LocalTime> startTimeCombo;
    public ComboBox <LocalTime> endTimeCombo;
    public ComboBox<Customer> customerIDCombo;
    public ComboBox<User> userIDCombo;
    public TextField typeTXT;
    public TextField locationTXT;
    public TextField descriptionTXT;
    public TextField titleTXT;
    public ComboBox<Contacts> contactCombo;
    public DatePicker startDateCombo;
    public TextField appointmentIDTXT;
    public Button appointmentAdd;
    public ObservableList<Customer> customerObservableList;
    public ObservableList<Contacts> contactsObservableList;
    public ObservableList<User> userObservableList;

    /**
     *
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
            Stage stageAddCustomer = (Stage)((Node)event.getSource()).getScene().getWindow();
            stageAddCustomer.setTitle("C195 Application");
            stageAddCustomer.setScene(scene);
            stageAddCustomer.show();
        }
    }

    /**
     * this sets the combo boxes to choose information when creating an appointment record
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            contactsObservableList = ContactSQL.getAllContact();
            customerObservableList = CustomerSQL.getAllCustomer();
            userObservableList = UserSQL.getAllUsers();

            contactCombo.setItems(contactsObservableList);
            contactCombo.setPromptText("Choose a Contact");

            customerIDCombo.setItems(customerObservableList);
            customerIDCombo.setPromptText("Choose Customer");

            userIDCombo.setItems(userObservableList);
            userIDCombo.setPromptText("Select a User");

            startDateCombo.setValue(LocalDate.now());
            endDateCombo.setValue(LocalDate.now());

            startTimeCombo.setValue(LocalTime.of(10,00));
            endTimeCombo.setValue(LocalTime.of(12, 00));

//            ObservableList<String> timeList = FXCollections.observableArrayList();
//            String strhour, strminute;
//            for (int hour = 0; hour < 24; hour++){
//                if (hour == 0){
//                    strhour = "00";
//                }
//                else{
//                    strhour = String.valueOf(hour);
//                }
//                for (int minute = 0; minute < 60; minute = minute + 15){
//                    if (minute == 0){
//                        strminute = "00";
//                    }
//                    else{
//                        strminute = String.valueOf(minute);
//                    }
//                    timeList.add(strhour+ ":" + strminute);
//                }
//            } original help from course instructor - comment out by another instructor
            startTimeCombo.setItems(getTimeList());
            endTimeCombo.setItems(getTimeList());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    /**
     * this is used to populate the time combo boxes for appointment times
     * @return
     */
    public ObservableList <LocalTime> getTimeList () {
        ObservableList <LocalTime> list = FXCollections.observableArrayList();
        ZonedDateTime estStartBH = ZonedDateTime.of(LocalDate.now(), LocalTime.of(8,0), ZoneId.of("America/New_York"));
        LocalTime localStartBH = estStartBH.withZoneSameInstant(ZoneId.systemDefault()).toLocalTime();
        LocalTime localEndBH = localStartBH.plusHours(14);

        while (localStartBH.isBefore(localEndBH)){
            list.add(localStartBH);
            localStartBH = localStartBH.plusMinutes(15);
        }
        return list;
    }

    /**
     * this handles writing the appointment record to the data base and includes error exception handling to prevent misinformation
     * @param actionEvent
     * @throws SQLException
     */
    public void onAddAppointment(ActionEvent actionEvent) throws SQLException {
        try {

            LocalTime startTime = startTimeCombo.getValue();
            LocalTime endTime = endTimeCombo.getValue();

            LocalDate apptStartD = startDateCombo.getValue();
            LocalDate apptEndD = endDateCombo.getValue();

            LocalDateTime outputStart = LocalDateTime.of(apptStartD, startTime);
            LocalDateTime outputEnd = LocalDateTime.of(apptEndD, endTime);


            if (outputStart.equals(outputEnd)){
                this.AlertMessage(1);
                return;
            }
            if (outputEnd.isBefore(outputStart)){
                this.AlertMessage(2);
                return;
            }
            if (titleTXT.getText().isEmpty()){
                this.AlertMessage(3);
                return;
            }
            if (descriptionTXT.getText().isEmpty()){
                this.AlertMessage(3);
                return;
            }
            if (locationTXT.getText().isEmpty()){
                this.AlertMessage(3);
                return;
            }
            if (typeTXT.getText().isEmpty()){
                this.AlertMessage(3);
                return;
            }

            if (contactCombo.getSelectionModel().isEmpty()) {
                this.AlertMessage(3);
                return;
            }

            if (customerIDCombo.getSelectionModel().isEmpty()) {
                this.AlertMessage(3);
                return;
            }

            if (userIDCombo.getSelectionModel().isEmpty()) {
                this.AlertMessage(3);
                return;
            }

            /**
             * instructor assisted - check to prevent overlapping appointments for times and customer
             */
            boolean check = false;

            for (Appointment appt : AppointmentSQL.getAllAppointmentsByCustomer(customerIDCombo.getValue().getCustomerID())){

                if (appt.getBegin().isAfter(outputStart) &&  appt.getBegin().isBefore(outputEnd)){
                    check = true;
                    break;
                }
                if (appt.getBegin().isEqual(outputStart) || appt.getEnd().isEqual(outputEnd)){
                    check = true;
                    break;
                }
            }

            if (check == true){
                this.AlertMessage(5);
                return;
            }

            String addStatement = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(addStatement);

//            customerObservableList.forEach(customer -> {
//                if (customer.getCustomerName().equals(customerIDCombo.getValue())){
//                    parameter.setInt(parameter 11, customer.getCustomerID())
//                }
//            }); Lambda help from instructor

            preparedStatement.setString(1,titleTXT.getText());
            preparedStatement.setString(2,descriptionTXT.getText());
            preparedStatement.setString(3,locationTXT.getText());
            preparedStatement.setString(4, typeTXT.getText());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(outputStart));
            preparedStatement.setTimestamp(6,Timestamp.valueOf(outputEnd));
            preparedStatement.setTimestamp(7,Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(8,"USER");
            preparedStatement.setTimestamp(9,Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(10,"USER");
            preparedStatement.setInt(11,(customerIDCombo.getValue().getCustomerID()));
            preparedStatement.setInt(12, (userIDCombo.getValue().getUserID()));
            preparedStatement.setInt(13, (contactCombo.getValue().contactID));
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
     * this is the listing for the error message handling exceptions
     * @param message
     */
    private void AlertMessage(int message) {
        Alert error;
        if (message == 1) {
            error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Exception!!");
            error.setContentText("The start time cant be the same as the end time");
            error.showAndWait();
        }
        if (message == 2) {
            error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Exception!!");
            error.setContentText("the end time can not come before the begin time");
            error.showAndWait();
        }

        if (message == 3) {
            error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Exception!!");
            error.setContentText("User, Contact, and Customer Field can not be empty");
            error.showAndWait();
        }

        if (message == 5) {
            error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Exception!!");
            error.setContentText("Overlapping Times - pick a different time");
            error.showAndWait();
        }

    }

}
