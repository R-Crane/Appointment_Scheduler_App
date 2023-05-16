package c195Project.model;

public class FirstLevelDivisions {

    public int divisionID;

    public int getDivisionID() {
        return divisionID;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public int getCountryID() {
        return countryID;
    }

    public String divisionName;
    public int countryID;


    /**
     * This class houses all the first level divisions setters and getters
     * @param divisionID
     * @param divisionName
     * @param countryID
     */
    public FirstLevelDivisions(int divisionID, String divisionName, int countryID){

        this.divisionID = divisionID;
        this.divisionName = divisionName;
        this.countryID = countryID;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return (divisionName);
    }
}
