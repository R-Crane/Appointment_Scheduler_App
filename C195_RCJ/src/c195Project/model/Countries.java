package c195Project.model;

public class Countries {

    public int countryID;
    public String countryName;

    /**
     *
     * @return
     */
    public int getCountryID() {
        return countryID;
    }

    /**
     *
     * @param countryID
     */
    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    /**
     *
     * @return
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     *
     * @param countryName
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }


    /**
     *
     * @param countryID
     * @param countryName
     */
    public Countries(int countryID, String countryName){

        this.countryID = countryID;
        this.countryName = countryName;

    }

    /**
     * This returns the ID along with the Country name when listed in the combo boxes found in the project
     * @return
     */
    @Override
    public String toString() {
        return (" ID : " + Integer.toString(countryID) + " Name : " + countryName);
    }
}
