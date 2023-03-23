package basemodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Public class that represents Products, which may or may not contain associated parts found in associatedPartsList.
 */
public class Product {

    private ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * creates new product.
     * @param id the product ID (auto generated).
     * @param name the name of the product.
     * @param price the price of the product.
     * @param stock quantity of product in Inventory.
     * @param min minimum par of product in Inventory.
     * @param max maximum par of product in Inventory.
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     *
     * @return the product ID.
     */
    public int getId() {
        return id;
    }

    /**
     * sets the product ID.
     * @param id the desired ID number for the product.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return the name of the product.
     */
    public String getName() {
        return name;
    }

    /**
     * sets the name of the product.
     * @param name the desired name of the product.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return the price of the product.
     */
    public double getPrice() {
        return price;
    }

    /**
     * sets the price of the product.
     * @param price the desired price of the product.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     *
     * @return the number of products currently in Inventory.
     */
    public int getStock() {
        return stock;
    }

    /**
     * sets the number of products in Inventory.
     * @param stock the desired number of this product in Inventory.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     *
     * @return minimum par value of product
     */
    public int getMin() {
        return min;
    }

    /**
     * sets the minimum par value for the product.
     * @param min the desired minimum par value.
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     *
     * @return the maximum par value of the product.
     */
    public int getMax() {
        return max;
    }

    /**
     * sets the maximum par value of the product.
     * @param max the desired maximum par value.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     *
     * @return list of all Associated Parts.
     */
    public ObservableList<Part> getAllAssociatedParts() {
//        System.out.println("Associated parts list: ");          //  -----  TESTING
//        for (Part assParts : associatedPartsList) {
//            System.out.println(assParts.getId() + " - " + assParts.getName());
//        }
        return associatedPartsList;
    }

    /**
     * adds part to Associated Parts list.
     * @param part the part to be added to the Associated Parts list.
     */
    public void addAssociatedPart(Part part) {
        this.associatedPartsList.add(part);
    }

    /**
     * deletes part from Associated Parts list.
     * @param selectedAssociatedPart the part to remove from Associated parts list.
     * @return true if successful, false if part not found in Associated parts list.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if (associatedPartsList.contains(selectedAssociatedPart)) {
//            System.out.println(selectedAssociatedPart.getId() + " - " + selectedAssociatedPart.getName() +
//                    " removed from associated parts list");         //  ---------------------------------  TESTING
            return this.associatedPartsList.remove(selectedAssociatedPart);
        } else {
            System.out.println("Part not found");                   //  -----  ???
            return false;
        }
    }
}
