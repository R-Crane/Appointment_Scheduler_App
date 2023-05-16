package c195Project.controller;

import c195Project.DAO.AppointmentSQL;
import c195Project.model.Appointment;
import c195Project.model.User;
import c195Project.DAO.UserSQL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    public TextField usernameTXT;
    public PasswordField passwordTXT;
    public Label zoneID;
    public String timezone;
    public Label title;
    public Button login;
    public Label pass;
    public Label name;

        /**
         * this handles validating the user and letting the log in to the application
         * this will also alert if the user has an appointment within 15 minutes
         * @param actionEvent
         * @throws IOException
         */
        public void onLoginButton (ActionEvent actionEvent) throws IOException {
            String username = usernameTXT.getText();
            String password = passwordTXT.getText();

            try {
                User userLogin = UserSQL.validateUser(username, password);

                ResourceBundle resourceBundle = ResourceBundle.getBundle("c195Project.RBMain", Locale.getDefault());

                FileWriter log = new FileWriter("login_activity.txt", true);
                PrintWriter activity = new PrintWriter(log);

                if (userLogin == null) {

                    activity.print("UnSuccessful login from " + username + " at time/date: " + LocalDateTime.now() + " \n ");

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle(resourceBundle.getString("titleError"));
                    alert.setContentText(resourceBundle.getString("unsuccessful"));
                    alert.show();
                } else {

                    activity.print("Successful login from " + username + " at time/date: " + LocalDateTime.now() + " \n ");

                    String msg = "Welcome In! - You have the following appointment(s): ";
                    boolean upcoming = false;
                    for (Appointment appt : AppointmentSQL.getUpcomingAppointments(userLogin.getUserID())) {
                        upcoming = true;
                        msg += "\nID: " + appt.getAppointmentID() + " At " + appt.getBegin().toLocalDate() + " " + appt.getBegin().toLocalTime();
                    }

                    if (!upcoming) {
                        msg = "Welcome In! - No Upcoming Appointments!";
                    }

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText(msg);
                    alert.showAndWait();

                    Parent main = FXMLLoader.load(getClass().getResource("../view/MainMenu.fxml"));
                    Scene scene = new Scene(main);
                    Stage stageMain = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    stageMain.setTitle("C195 Application");
                    stageMain.setScene(scene);
                    stageMain.show();
                }
                activity.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    /**
     * this sets all the labels on the login in screen to be able to change from english to french based on the computers location
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        try {
            timezone = ZoneId.systemDefault().getId();
            zoneID.setText(timezone);

            resourceBundle = ResourceBundle.getBundle("c195Project.RBMain", Locale.getDefault());

            title.setText(resourceBundle.getString("title"));
            login.setText(resourceBundle.getString("login"));
            name.setText(resourceBundle.getString("name"));
            pass.setText(resourceBundle.getString("pass"));

        } catch (Exception e) {
            e.printStackTrace();
            }
        }
    }
