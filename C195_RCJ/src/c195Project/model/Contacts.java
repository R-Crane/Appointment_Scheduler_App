package c195Project.model;

import c195Project.util.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Contacts {

        public int contactID;
        public String contactName;
        public String contactEmail;

    /**
     *
     * @return
     */
    public int getContactID() {
        return contactID;
    }

    /**
     *
     * @param contactID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     *
     * @return
     */
    public String getContactName() {
        return contactName;
    }

    /**
     *
     * @param contactName
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     *
     * @return
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     *
     * @param contactEmail
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    /**
     *
     * @param contactID
     * @param contactName
     * @param contactEmail
     */
        public Contacts(int contactID, String contactName, String contactEmail){

            this.contactID = contactID;
            this.contactName = contactName;
            this.contactEmail = contactEmail;
        }

    /**
     * This returns a listing to show the ID with the name of the contact and their email in the combo boxes found within the project
     * @return
     */
        @Override
    public String toString() {
        return (" ID : " + Integer.toString(contactID) + " Name : " + contactName + " Email : " + contactEmail);
        }
}
