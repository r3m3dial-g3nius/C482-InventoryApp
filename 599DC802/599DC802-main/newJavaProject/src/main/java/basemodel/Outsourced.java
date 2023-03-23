package basemodel;

/**
 * Public class that represents Outsourced parts, not manufactured on site.
 */
public class Outsourced extends Part {

    private String companyName;

    /**
     * creates new part not manufactured "In House".
     * @param id the part ID number.
     * @param name the part name.
     * @param price the price of the part.
     * @param stock the quantity of this part in Inventory.
     * @param min the minimum par of this part in Inventory.
     * @param max the maximum par of this part in Inventory.
     * @param companyName the name of the manufacturing company.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     *
     * @return the name of the company that manufactured the part.
     */
    public String getCompanyName() {
        return this.companyName;
    }

    /**
     * sets the name of the company that manufactured the part.
     * @param companyName the name of the company that manufactured the part.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
