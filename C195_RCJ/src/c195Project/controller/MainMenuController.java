package c195Project.controller;

import c195Project.DAO.AppointmentSQL;
import c195Project.DAO.CustomerSQL;
import c195Project.model.Appointment;
import c195Project.model.Customer;
import c195Project.util.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * THE LAMBDAS FOR THIS PROJECT CAN BE FOUND IN MAIN MENU CONTROLLER - #1 IS FOR THE MONTH TOTAL APPOINTMENTS AND #2 IS FOR
 *  THE WEEK TOTAL APPOINTMENTS
 */
public class MainMenuController implements Initializable {

    public TableView customersTable;
    public TableView appointmentsTable;
    public Button custEdit;
    public Button custDelete;
    public Button appEdit;
    public Button appDelete;
    public RadioButton weekButton;
    public RadioButton monthButton;
    public RadioButton allButton;
    public TableColumn customerID;
    public TableColumn customerName;
    public TableColumn customerAddress;
    public TableColumn customerZip;
    public TableColumn customerPhone;
    public TableColumn customerFLD;
    public ToggleGroup appointmentsRadioGroup;
    public TableColumn appointmentID;
    public TableColumn appointmentTitle;
    public TableColumn appointmentDesc;
    public TableColumn appointmentLoc;
    public TableColumn appointmentType;
    public TableColumn appointmentBegin;
    public TableColumn appointmentEnd;
    public TableColumn CxID;
    public TableColumn apptContactID;
    public TableColumn apptUserID;
    public TableColumn countNam;

    public static Customer getCustomerEdit() {
        return customerEdit;
    }
    public static Customer customerEdit;
    public static Customer getCustomerDelete() {
        return customerDelete;
    }
    public static Customer customerDelete;
    public static Appointment appointmentDelete;
    public static Appointment appointmentEdit;

    public static Appointment getAppointmentEdit() {
        return appointmentEdit;
    }

    /**
     * this will handle the customer add button in the middle of the main menu screen
     * @param event
     */
    @FXML
    public void customerAdd(ActionEvent event) {
        try {
            Parent customerAdd = (Parent) FXMLLoader.load(this.getClass().getResource("../view/CustomerAdd.fxml"));
            Scene scene = new Scene(customerAdd);
            Stage stageAddPart = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stageAddPart.setTitle("Add a customer");
            stageAddPart.setScene(scene);
            stageAddPart.show();
        } catch (IOException var5) {
        }
    }

    /**
     * this will handle the appointment add button found on the main menu screen
     * @param event
     */
    @FXML
    public void appointmentAdd (ActionEvent event) {
        try {
            Parent appointmentAdd = (Parent) FXMLLoader.load(this.getClass().getResource("../view/AppointmentAdd.fxml"));
            Scene scene = new Scene(appointmentAdd);
            Stage stageAddPart = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stageAddPart.setTitle("Add a customer");
            stageAddPart.setScene(scene);
            stageAddPart.show();
        } catch (IOException var5) {
        }
    }

    /**
     * this will handle loading the reports screen
     * @param event
     */
    @FXML
    public void reports (ActionEvent event) {
        try {
            Parent reports = (Parent) FXMLLoader.load(this.getClass().getResource("../view/Report.fxml"));
            Scene scene = new Scene(reports);
            Stage stageAddPart = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stageAddPart.setTitle("C195 Report Data");
            stageAddPart.setScene(scene);
            stageAddPart.show();
        } catch (IOException var5) {
        }
    }

    /**
     * this will exit the application and close the connection to the data base
     * @param event
     */
    @FXML
    public void exit(ActionEvent event) {
        JDBC.closeConnection();
        System.exit(0);
    }

    /**
     * this will set the items in both tables customers and appointments on the main menu
     * @param url
     * @param resourceBundle
     */
    public void initialize(URL url, ResourceBundle resourceBundle){
        try {
            ObservableList<Customer> customerObservableList = CustomerSQL.getAllCustomer();
            ObservableList<Appointment> appointmentObservableList = AppointmentSQL.getAllAppointments();

            customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            customerAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
            customerZip.setCellValueFactory(new PropertyValueFactory<>("customerPostal"));
            customerPhone.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
            customerFLD.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
            countNam.setCellValueFactory(new PropertyValueFactory<>("countryName"));

            appointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            appointmentTitle.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
            appointmentDesc.setCellValueFactory(new PropertyValueFactory<>("appointmentDesc"));
            appointmentLoc.setCellValueFactory(new PropertyValueFactory<>("appointmentLoc"));
            appointmentType.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
            appointmentBegin.setCellValueFactory(new PropertyValueFactory<>("begin"));
            appointmentEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
            CxID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            apptUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
            apptContactID.setCellValueFactory(new PropertyValueFactory<>("contactID"));

            customersTable.setItems(customerObservableList);
            appointmentsTable.setItems(appointmentObservableList);


            ToggleGroup apptGroup = new ToggleGroup();
            allButton.setToggleGroup(apptGroup);
            weekButton.setToggleGroup(apptGroup);
            monthButton.setToggleGroup(apptGroup);
            allButton.setSelected(true);
            weekButton.setSelected(false);
            monthButton.setSelected(false);



        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * this will delete and appointment from the appointments table on the main menu
     * @param actionEvent
     * @throws SQLException
     */
    @FXML
    public void onAppDelete(ActionEvent actionEvent) throws SQLException {
        appointmentDelete = (Appointment) this.appointmentsTable.getSelectionModel().getSelectedItem();
        if (appointmentDelete == null) {
            this.AlertMessage(1);
        } else {
            Alert deleteCust = new Alert(Alert.AlertType.INFORMATION, "Item(s) will be deleted", new ButtonType[0]);
            deleteCust.setTitle("Ok to delete?");
            Optional<ButtonType> choice = deleteCust.showAndWait();
            if (choice.isPresent() && choice.get() == ButtonType.OK) {
                int aX = appointmentDelete.getAppointmentID();

                String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";

                PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);

                preparedStatement.setInt(1, aX);
                preparedStatement.execute();

                ObservableList<Appointment> resetView = AppointmentSQL.getAllAppointments();
                appointmentsTable.setItems(resetView);

            }
        }
    }

    /**
     * this will handle loading the edit application screen
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void onAppEdit(ActionEvent actionEvent) throws IOException {
        appointmentEdit = (Appointment)this.appointmentsTable.getSelectionModel().getSelectedItem();
        if (appointmentEdit == null) {
            this.AlertMessage(1);
        } else{
            Parent reports = (Parent) FXMLLoader.load(this.getClass().getResource("../view/AppointmentEdit.fxml"));
            Scene scene = new Scene(reports);
            Stage stageAddPart = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            stageAddPart.setTitle("Edit Appointment");
            stageAddPart.setScene(scene);
            stageAddPart.show();
        }
    }

    /**
     * this will handle deleting the customer record selected from the table on the main menu
     * @param actionEvent
     * @throws SQLException
     */
    @FXML
    public void onCustomerDelete(ActionEvent actionEvent) throws SQLException {
        customerDelete = (Customer) this.customersTable.getSelectionModel().getSelectedItem();
        if (customerDelete == null) {
            this.AlertMessage(1);
        } else {
            Alert deleteCust = new Alert(Alert.AlertType.INFORMATION, "Item(s) will be deleted", new ButtonType[0]);
            deleteCust.setTitle("Ok to delete?");
            Optional<ButtonType> choice = deleteCust.showAndWait();
            if (choice.isPresent() && choice.get() == ButtonType.OK) {
                int cX = customerDelete.getCustomerID();

                String sql = "DELETE FROM customers WHERE Customer_ID = ?";

                PreparedStatement preparedStatement = JDBC.getConnection().prepareStatement(sql);

                preparedStatement.setInt(1, cX);
                preparedStatement.execute();

                ObservableList<Customer> resetView = CustomerSQL.getAllCustomer();
                customersTable.setItems(resetView);

            }
        }
    }

    /**
     * this will handle opening the screen to edit the customer
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    public void onCustomerEdit(ActionEvent actionEvent) throws IOException {
    customerEdit = (Customer)this.customersTable.getSelectionModel().getSelectedItem();
    if (customerEdit == null) {
        this.AlertMessage(1);
    } else{
        Parent reports = (Parent) FXMLLoader.load(this.getClass().getResource("../view/CustomerEdit.fxml"));
        Scene scene = new Scene(reports);
        Stage stageAddPart = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stageAddPart.setTitle("Edit Customer");
        stageAddPart.setScene(scene);
        stageAddPart.show();
         }

    }

    /**
     * this is the listing for alert messages to handle exceptions on the main menu
     * @param message
     */
    private void AlertMessage(int message) {
        Alert error;
        if (message == 1) {
            error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Exception!!");
            error.setContentText("Nothing Selected");
            error.showAndWait();
        }

        if (message == 2) {
            error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Exception!!");
            error.setContentText("You can't delete this ' '  if one or more ' '  is Associated!!");
            error.showAndWait();
        }

    }

    /**
     *
     * @param actionEvent
     * @throws SQLException
     */
    public void onAll(ActionEvent actionEvent) throws SQLException {
        Alert reset = new Alert(Alert.AlertType.INFORMATION, "Resetting Appointment View", new ButtonType[0]);
        reset.setTitle("Reset");
        Optional<ButtonType> choice = reset.showAndWait();
        if (choice.isPresent() && choice.get() == ButtonType.OK) {
            ObservableList<Appointment> resetView = AppointmentSQL.getAllAppointments();
            appointmentsTable.setItems(resetView);
        }
    }

    /**
     * LAMBDA #1
     *This is the lambda method to show a filtered listing of the appointments by month in the Appointments table view
     * @param actionEvent
     */
    public void onMonth(ActionEvent actionEvent) throws SQLException {
//    filteredList
//
//        list.forEach(a -> {
//    if (a.getmonth == currentMonth)
//        filteredList.add(a)
//}); assistance from course instructor - pseudo to help build lambdas for project
       try {

            ObservableList<Appointment> a = AppointmentSQL.getAllAppointments();
            ObservableList<Appointment> currentMonth = FXCollections.observableArrayList();

        LocalDateTime monthBegin = LocalDateTime.now().minusMonths(1);
        LocalDateTime monthEnd = LocalDateTime.now().plusMonths(1);

        a.forEach(appt -> {
             if (appt.getEnd().isAfter(monthBegin) && appt.getBegin().isBefore(monthEnd)){
                currentMonth.add(appt);
             }
             appointmentsTable.setItems(currentMonth);
        });
    } catch (SQLException throwables) {
           throwables.printStackTrace();
       }
    }

    /**
     * LAMBDA #2
     *This is the lambda method to show a filtered listing of the appointments by Week in the Appointments table view
     * @param actionEvent
     */
    public void onWeek(ActionEvent actionEvent) {

        try {

            ObservableList<Appointment> a = AppointmentSQL.getAllAppointments();
            ObservableList<Appointment> currentWeek = FXCollections.observableArrayList();

            LocalDateTime weekBegin = LocalDateTime.now().minusWeeks(1);
            LocalDateTime weekEnd = LocalDateTime.now().plusWeeks(1);

            a.forEach(appt -> {
                if (appt.getEnd().isAfter(weekBegin) && appt.getBegin().isBefore(weekEnd)){
                    currentWeek.add(appt);
                }
                appointmentsTable.setItems(currentWeek);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}












