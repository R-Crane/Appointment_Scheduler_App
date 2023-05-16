package c195Project.model;

public class ReportItemTotal {

    private String name;
    private int total;

    /**
     * This class is created for use in the report menu screen for the different tables that show the types and by month total
     * of reports for all appointments
     * @param name
     * @param total
     */
    public ReportItemTotal(String name, int total) {
        this.name = name;
        this.total = total;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public int getTotal() {
        return total;
    }

    /**
     *
     * @param total
     */
    public void setTotal(int total) {
        this.total = total;
    }
}
