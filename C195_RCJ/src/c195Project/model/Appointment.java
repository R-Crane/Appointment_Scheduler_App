package c195Project.model;

import java.time.LocalDateTime;

public class Appointment {

    private int appointmentID;
    private String appointmentTitle;
    private String appointmentDesc;
    private String appointmentLoc;
    private String appointmentType;
    private LocalDateTime begin;
    private LocalDateTime end;

    /**
     *
     * @return
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     *
     * @param appointmentID
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     *
     * @return
     */
    public String getAppointmentTitle() {
        return appointmentTitle;
    }

    /**
     *
     * @param appointmentTitle
     */
    public void setAppointmentTitle(String appointmentTitle) {
        this.appointmentTitle = appointmentTitle;
    }

    /**
     *
     * @return
     */
    public String getAppointmentDesc() {
        return appointmentDesc;
    }

    /**
     *
     * @param appointmentDesc
     */
    public void setAppointmentDesc(String appointmentDesc) {
        this.appointmentDesc = appointmentDesc;
    }

    /**
     *
     * @return
     */
    public String getAppointmentLoc() {
        return appointmentLoc;
    }

    /**
     *
     * @param appointmentLoc
     */
    public void setAppointmentLoc(String appointmentLoc) {
        this.appointmentLoc = appointmentLoc;
    }

    /**
     *
     * @return
     */
    public String getAppointmentType() {
        return appointmentType;
    }

    /**
     *
     * @param appointmentType
     */
    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    /**
     *
     * @return
     */
    public LocalDateTime getBegin() {
        return begin;
    }

    /**
     *
     * @param begin
     */
    public void setBegin(LocalDateTime begin) {
        this.begin = begin;
    }

    /**
     *
     * @return
     */
    public LocalDateTime getEnd() {
        return end;
    }

    /**
     *
     * @param end
     */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**
     *
     * @return
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     *
     * @param customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     *
     * @return
     */
    public int getUserID() {
        return userID;
    }

    /**
     *
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

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

    public int customerID;
    public int userID;
    public int contactID;

    /**
     *
     * @param appointmentID
     * @param appointmentTitle
     * @param appointmentDesc
     * @param appointmentLoc
     * @param appointmentType
     * @param begin
     * @param end
     * @param customerID
     * @param userID
     * @param contactID
     */
    public Appointment(int appointmentID, String appointmentTitle, String appointmentDesc, String appointmentLoc, String appointmentType,
                       LocalDateTime begin, LocalDateTime end, int customerID, int userID, int contactID){


        this.appointmentID = appointmentID;
        this.appointmentTitle = appointmentTitle;
        this.appointmentDesc = appointmentDesc;
        this.appointmentLoc = appointmentLoc;
        this.appointmentType = appointmentType;
        this.begin = begin;
        this.end = end;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }
}
