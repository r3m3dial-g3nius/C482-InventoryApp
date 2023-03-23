package basemodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Public class represents inventory as a whole, including Parts AND Products.
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    private static int partNumber = 110001;                 //  ---------  Parts start with 11****

    private static int productNumber = 220001;              //  ----------  Products start with 22****

    /**
     * "generates" unique part number (part ID)
     * @return static int partNumber +1
     */
    public static int getPartNumber() {
        return partNumber++;
    }

    /**
     * "generates" unique product number (product ID)
     * @return static int productNumber + 1
     */
    public static int getProductNumber() {
        return productNumber++;
    }

    /**
     * adds newPart
     * @param newPart part to be added to inventory (can be inHouse or outSourced).
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * adds newProduct
     * @param newProduct product to be added to inventory.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * searches for part in allParts list
     * @param partId part ID number
     * @return part if found, returns null if not found in allParts Inventory
     */
    public static Part lookupPart(int partId) {
        boolean hasPart = false;
        Part partToFind = null;
        for (Part eachPart : allParts) {
            if (eachPart.getId() == partId) {
                hasPart = true;
                partToFind = eachPart;
                break;
            }
        }
        if (hasPart) {
            return partToFind;
        } else {
            return null;
        }
    }

    /**
     * searches for part in allParts list
     * @param partName string value of part name
     * @return list containing all parts that match search
     */
    public static ObservableList<Part> lookupPart(String partName) {
        boolean hasPart = false;
        ObservableList<Part> matchingParts = FXCollections.observableArrayList();
        ObservableList<Part> partsToSearch = Inventory.getAllParts();

        for (Part eachPart : partsToSearch) {
            if (eachPart.getName().toLowerCase().contains(partName.toLowerCase())) {
                hasPart = true;
                matchingParts.add(eachPart);
            }
        }
        return matchingParts;
    }

    /**
     * searches for product in allProducts list
     * @param productId product ID number
     * @return product if found, returns null if no match in allProducts list
     */
    public static Product lookupProduct(int productId) {
        boolean hasProduct = false;
        Product productToFind = null;
        for (Product eachProduct : allProducts) {
            if (eachProduct.getId() == productId) {
                hasProduct = true;
                productToFind = eachProduct;
                break;
            }
        }
        if (hasProduct) {
            return productToFind;
        } else {
            return null;
        }
    }

    /**
     * searches for product in allProducts list
     * @param productName string value of product name
     * @return list of all matching products (if any)
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        boolean hasPart = false;
        ObservableList<Product> matchingProducts = FXCollections.observableArrayList();
        ObservableList<Product> productsToSearch = Inventory.getAllProducts();

        for (Product eachProduct : productsToSearch) {
            if (eachProduct.getName().toLowerCase().contains(productName.toLowerCase())) {
                hasPart = true;
                matchingProducts.add(eachProduct);
            }
        }
        return matchingProducts;
    }

    /**
     * replaces part located at user specified index with user specified part.
     * @param index index of part to be replaced
     * @param selectedPart new part that replaces old part
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * replaces product located at user specified index with user specified product.
     * @param index index of product to be replaced
     * @param newProduct new product that replaces old product
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * deletes selected part from allParts list
     * @param selectedPart part to be deleted
     * @return true if successful, false if part not found
     */
    public static boolean deletePart(Part selectedPart) {
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        } else {
            return false;
        }
    }

    /**
     * deletes selected product from allParts list
     * @param selectedProduct part to be deleted
     * @return true if successful, false if product not found
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        } else {
            return false;
        }
    }

    /**
     * gets list of all parts in inventory
     * @return allParts list
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * gets list of all products in inventory
     * @return allProducts list
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
