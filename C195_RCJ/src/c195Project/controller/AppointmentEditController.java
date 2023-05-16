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
import javax.imageio.IIOException;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppointmentEditController implements Initializable {


    public ComboBox <User> userIDCombo;
    public ComboBox <Customer> customerIDCombo;
    public ComboBox <LocalTime> endTimeCombo;
    public ComboBox <LocalTime> startTimeCombo;
    public DatePicker endDateCombo;
    public DatePicker startDateCombo;
    public Button completeEditButton;
    public Button cancelButton;
    public TextField descriptionTXT;
    public TextField locationTXT;
    public TextField typeTXT;
    public TextField titleTXT;
    public TextField appointmentIDTXT;
    public ComboBox <Contacts> contactCombo;
    private Appointment appointmentSelect;
    public ObservableList<Customer> customerObservableList;
    public ObservableList<Contacts> contactsObservableList;
    public ObservableList<User> userObservableList;

    /**
     * this handles completing the edit of the customer record and writing it to the data base
     * @param actionEvent
     */
    public void onCompleteEdit(ActionEvent actionEvent) {


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

            String update = "UPDATE appointments SET Title = ?, Location = ?, Description = ?, Type = ?, Start = ?, End = ?, " +
                    "Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID =? WHERE Appointment_ID = ?";
            PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(update);

            preparedStatement.setString(1, titleTXT.getText());
            preparedStatement.setString(2, descriptionTXT.getText());
            preparedStatement.setString(3, locationTXT.getText());
            preparedStatement.setString(4, typeTXT.getText());
            preparedStatement.setTimestamp(5,Timestamp.valueOf(outputStart));
            preparedStatement.setTimestamp(6,Timestamp.valueOf(outputEnd));
            preparedStatement.setTimestamp(7,Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setString(8,"USER");
            preparedStatement.setInt(9,(customerIDCombo.getValue().getCustomerID()));
            preparedStatement.setInt(10, (userIDCombo.getValue().getUserID()));
            preparedStatement.setInt(11, (contactCombo.getValue().contactID));
            preparedStatement.setInt(12, Integer.parseInt(appointmentIDTXT.getText()));
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
     *
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
     * sets the text fields and combo boxes to the information pulled from data base based on appointment selected
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {

            this.appointmentSelect = MainMenuController.getAppointmentEdit();
            this.appointmentIDTXT.setText(String.valueOf(this.appointmentSelect.getAppointmentID()));
            this.titleTXT.setText(String.valueOf(this.appointmentSelect.getAppointmentTitle()));
            this.descriptionTXT.setText(String.valueOf(appointmentSelect.getAppointmentDesc()));
            this.locationTXT.setText(String.valueOf(appointmentSelect.getAppointmentLoc()));
            this.typeTXT.setText(String.valueOf(appointmentSelect.getAppointmentType()));
            this.startDateCombo.setValue(appointmentSelect.getBegin().toLocalDate());
            this.endDateCombo.setValue(appointmentSelect.getEnd().toLocalDate());
            this.startTimeCombo.setValue(LocalTime.parse(String.valueOf(appointmentSelect.getBegin().toLocalTime())));
            this.endTimeCombo.setValue(LocalTime.parse(String.valueOf(appointmentSelect.getEnd().toLocalTime())));

            contactsObservableList = ContactSQL.getAllContact();

            customerObservableList = CustomerSQL.getAllCustomer();

            userObservableList = UserSQL.getAllUsers();

            contactCombo.setItems(contactsObservableList);
            contactCombo.setPromptText("Choose a Contact");

            customerIDCombo.setItems(customerObservableList);
            customerIDCombo.setPromptText("Choose Customer");

            userIDCombo.setItems(userObservableList);
            userIDCombo.setPromptText("Select a User");

            startTimeCombo.setItems(getTimeList());
            endTimeCombo.setItems(getTimeList());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * this time list populates the combo boxes for the appointment times
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
     * this is the listing for error message exception handling
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
