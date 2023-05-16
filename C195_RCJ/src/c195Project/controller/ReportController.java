package c195Project.controller;

import c195Project.DAO.AppointmentSQL;
import c195Project.DAO.ContactSQL;
import c195Project.DAO.ReportMenuSQL;
import c195Project.model.Appointment;
import c195Project.model.Contacts;
import c195Project.model.Customer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class ReportController implements Initializable {

    public TableView intheUSTable;
    public TableView contactScheduleTable;
    public ComboBox <Contacts> contactsCombo;
    public TableColumn appointmentID;
    public TableColumn appointmentTitle;
    public TableColumn appointmentDesc;
    public TableColumn appointmentLoc;
    public TableColumn appointmentContact;
    public TableColumn appointmentType;
    public TableColumn appointmentBegin;
    public TableColumn appointmentEnd;
    public TableColumn apptCustomerID;
    public TableView byTypeTable;
    public TableColumn apptTypeCol;
    public TableColumn apptTypeTotal;
    public TableView byMonthTable;
    public TableColumn apptByMonth;
    public TableColumn byMonthTotal;
    public TableColumn usCustomerName;
    public TableColumn usCountryName;

    /**
     * cancel button handler
     * @param event
     * @throws IOException
     */
    public void clickCancelButton(ActionEvent event) throws IOException {
        Alert cancel = new Alert(Alert.AlertType.INFORMATION);
        cancel.setTitle("Leave Reporting Data");
        cancel.setContentText("Returning to Main Menu - Please click OK to continue");
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

    /**
     * sets the items in various tables for the reporting menu screen
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            ObservableList<Customer> customerObservableList = ReportMenuSQL.getUSCustomer();
            ObservableList<Contacts> contactsObservableList = ContactSQL.getAllContact();

            usCustomerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            usCountryName.setCellValueFactory(new PropertyValueFactory<>("divisionName"));

            appointmentID.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
            appointmentTitle.setCellValueFactory(new PropertyValueFactory<>("appointmentTitle"));
            appointmentDesc.setCellValueFactory(new PropertyValueFactory<>("appointmentDesc"));
            appointmentLoc.setCellValueFactory(new PropertyValueFactory<>("appointmentLoc"));
            appointmentType.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
            appointmentBegin.setCellValueFactory(new PropertyValueFactory<>("begin"));
            appointmentEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
            apptCustomerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
            appointmentContact.setCellValueFactory(new PropertyValueFactory<>("contactID"));

            apptTypeCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            apptTypeTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

            apptByMonth.setCellValueFactory(new PropertyValueFactory<>("name"));
            byMonthTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

            intheUSTable.setItems(customerObservableList);
            contactsCombo.setItems(contactsObservableList);
            contactsCombo.setPromptText("Choose a Contact");
            byTypeTable.setItems(ReportMenuSQL.getByTypeReport());
            byMonthTable.setItems(ReportMenuSQL.getByMonth());

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * this will set the appointment table to show the contact that was selected
     * @param actionEvent
     * @throws SQLException
     */
    public void onContactSelect(ActionEvent actionEvent) throws SQLException {
        Contacts selected = contactsCombo.getValue();
        contactScheduleTable.setItems(AppointmentSQL.getByContact(selected.getContactID()));
    }
}
