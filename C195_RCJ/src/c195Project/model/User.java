package c195Project.model;

public class User {

    /**
     *
     */
    private int userID;
    private String userName;

    /**
     *
     * @param userID
     * @param userName
     */
    public User(int userID, String userName) {
        this.userID = userID;
        this.userName = userName;
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
    public String getUserName() {
        return userName;
    }

    /**
     *
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return (" ID : " + Integer.toString(userID) + " Name : " + userName);
    }

}
