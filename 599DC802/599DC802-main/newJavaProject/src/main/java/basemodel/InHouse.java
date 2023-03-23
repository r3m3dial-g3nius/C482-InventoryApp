package basemodel;

/**
 *   Public class that represents parts created in house.
 */
public class InHouse extends Part {

    /**
     * ID number of machine that produced part.
     */
    private int machineId;

    /**
     * creates new part manufactured "In House".
     * @param id part ID (auto generated).
     * @param name part name.
     * @param price part price.
     * @param stock quantity of parts currently in Inventory.
     * @param min minimum quantity of parts allowed in Inventory.
     * @param max maximum quantity of parts allowed in Inventory.
     * @param machineId ID number of machine that produced part.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * this method gets machine ID number that produced part.
     * @return returns ID number of machine that produced part as int.
     */
    public int getMachineId() {
        return this.machineId;
    }

    /**
     * this method sets machineID to user defined int.
     * @param machineId ID number of machine that produced part.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
