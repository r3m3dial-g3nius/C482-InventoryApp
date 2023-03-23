package basemodel;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 *  public class that controls the main screen.
 */
public class MainScreenController implements Initializable {

    Stage stage;
    Parent scene;
    static Part selectedPart;
    static Product selectedProduct;

    @FXML
    private TableColumn<?, ?> partIdCol;

    @FXML
    private Button addPartButton;

    @FXML
    private Button addProductButton;

    @FXML
    private Button deletePartButton;

    @FXML
    private Button deleteProductButton;

    @FXML
    private Button exitProgramButton;

    @FXML
    private Button modifyPartButton;

    @FXML
    private Button modifyProductButton;

    @FXML
    private TableColumn<?, ?> partInStockColumn;

    @FXML
    private TableColumn<?, ?> partNameCol;

    @FXML
    private TableColumn<?, ?> partPriceCol;

    @FXML
    private TextField partSearchText;

    @FXML
    private TableView<Part> partTableView;

    @FXML
    private TableColumn<?, ?> productIdCol;

    @FXML
    private TableColumn<?, ?> productInStockCol;

    @FXML
    private TableColumn<?, ?> productNameCol;

    @FXML
    private TableColumn<?, ?> productPriceCol;

    @FXML
    private TextField productSearchText;

    @FXML
    private TableView<Product> productTableView;

    /**
     *
     * @return the selected part.
     */
    public static Part getSelectedPart() {
        return selectedPart;
    }

    /**
     *
     * @return the selected product.
     */
    public static Product getSelectedProduct() {
        return selectedProduct;
    }

    /**
     * fires when Add Part button is selected.
     * @param event loads Add Part screen.
     * @throws IOException
     */
    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {
        System.out.println("Add Part Button Pressed");
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("addPartScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();     //  this is how you center the screen!!!
        stage.show();
    }

    /**
     * fires when Add Product button is selected.
     * @param event loads Add Product screen.
     * @throws IOException
     */
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {
        System.out.println("Add Product Button Pressed");
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("addProductScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();     //  this is how you center the screen!!!
        stage.show();
    }

    /**
     * fires when Delete Part button is selected.
     * @param event deletes selected part from inventory.
     */
    @FXML
    void onActionDeletePart(ActionEvent event) {
        System.out.println("Delete Part Button Pressed");
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Please select a Part to continue.");
            alert.showAndWait();
            return;
        }

//  ---------------   vvvvv   CONFIRM DELETE PART  vvvvv   ---------------

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setTitle("Confirm Part delete");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();

        if ((result.isPresent()) && (result.get() == ButtonType.OK)) {

//  ---------------- vvvvv  removes deleted part from associated parts list of each product  vvvvv   -------------------

            for (Product eachProduct : Inventory.getAllProducts()) {
                while (eachProduct.getAllAssociatedParts().contains(selectedPart)) {
                    eachProduct.deleteAssociatedPart(selectedPart);
                    System.out.println(selectedPart.getName() + " deleted from Associated Parts List from Product: " +
                            eachProduct.getName());
                }
            }

//  --------------------------  vvvvv    Deletes part from allParts    vvvvv   ----------------------------------------

            Inventory.deletePart(selectedPart);
            System.out.println(selectedPart.getName() + " deleted from allParts list");

        } else if ((result.isPresent()) && (result.get() == ButtonType.CANCEL)) {
            System.out.println("Action cancelled. " + selectedPart.getName() + " not deleted.");
        }

        partSearchText.setText("");                             //   ---------   resets the search field
        partTableView.setItems(Inventory.getAllParts());        //   ---------   refreshes the tableview
    }

    /**
     * fires when Delete Product button is selected.
     * @param event deletes selected product only if that product has no associated parts.
     */
    @FXML
    void onActionDeleteProduct(ActionEvent event) {
        System.out.println("Delete Product Button Pressed");

        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Please select a Product to continue.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Product Delete");
        alert.setContentText("Are you sure?");

        Optional<ButtonType> result = alert.showAndWait();

        if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
            if (selectedProduct.getAllAssociatedParts().isEmpty()) {
                if (Inventory.deleteProduct(selectedProduct)) {
                    productSearchText.setText("");                               //   ---------   resets the search field
                    productTableView.setItems(Inventory.getAllProducts());        //   ---------   refreshes the tableview
                    System.out.println(selectedProduct.getName() + " deleted from allProducts list");
                } else {
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setTitle("Delete ERROR");
                    alert2.setContentText("Product not deleted. Error detected.");
                    alert2.showAndWait();
                }
            } else {
                Alert alert3 = new Alert(Alert.AlertType.WARNING);
                alert3.setTitle("Product not deleted");
                alert3.setContentText("Product contains associated parts. Delete not allowed at this time.");
                alert3.showAndWait();
            }

        } else if ((result.isPresent()) && (result.get() == ButtonType.CANCEL)) {
            System.out.println("Action cancelled. " + selectedProduct.getName() + " not deleted.");
            productSearchText.setText("");                               //   ---------   resets the search field
            productTableView.setItems(Inventory.getAllProducts());        //   ---------   refreshes the tableview

        }
    }

    /**
     * fires when Search button is selected.
     * NUMBERFORMATEXCEPTION - try/catch added to allow searching by part name or part number.
     * @param event searches inventory for part by ID number, name, or partial name.
     */
    @FXML
    void onActionLookupPart(ActionEvent event) {

        String q = partSearchText.getText();
        int n = -1;
        ObservableList<Part> partSearchResult = Inventory.lookupPart(q);

            try {
                n = Integer.parseInt(partSearchText.getText());
            } catch (NumberFormatException e) {
                //  ignore
            }

            Part partToFind = Inventory.lookupPart(n);

            if (partToFind != null) {
                if (!partSearchResult.contains(partToFind)) {
                    partSearchResult.add(partToFind);
                }
            }

            if (partSearchResult.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Part not found in inventory. Please enter new search.");
                alert.showAndWait();
                partTableView.setItems(Inventory.getAllParts());
                partSearchText.setText("");
            } else {
                partTableView.setItems(partSearchResult);
            }

            if (partSearchResult.size() == 1) {                             // highlights the ONLY result
                partTableView.getSelectionModel().select(0);               // how do you change the color???
            }
    }

    /**
     * fires when Search button is selected.
     * NUMBERFORMATEXCEPTION - try/catch added to allow searching by product name or product number.
     * @param event searches inventory for product by product ID, name, or partial name.
     */
    @FXML
    void onActionLookupProduct(ActionEvent event) {

        String q = productSearchText.getText();
        int n = -1;
        ObservableList<Product> productSearchResult = Inventory.lookupProduct(q);

        try {
            n = Integer.parseInt(productSearchText.getText());
        } catch (NumberFormatException e) {
            //  ignore
        }

        Product productToFind = Inventory.lookupProduct(n);

        if (productToFind != null) {
            if (!productSearchResult.contains(productToFind)) {
                productSearchResult.add(productToFind);
            }
        }

        if (productSearchResult.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Product not found in inventory. Please enter new search.");
            alert.showAndWait();
            productTableView.setItems(Inventory.getAllProducts());
            productSearchText.setText("");
        } else {
            productTableView.setItems(productSearchResult);
        }

        if (productSearchResult.size() == 1) {                     // highlights the ONLY result
            productTableView.getSelectionModel().select(0);      // BUT how do you change the color of the highlight?
        }
    }

    /**
     * fires when Modify Part button is selected.
     * @param event loads Modify Part screen if part is selected.
     * @throws IOException
     */
    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {
        System.out.println("Modify Part Button Pressed");

        selectedPart = partTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            System.out.println("Part not selected.  Please select a part.");
            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Please select a Part to modify.");
            alert.showAndWait();
            return;
        }

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("modifyPartScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();     //  this is how you center the screen!!!
        stage.show();
    }

    /**
     * fires when Modify Product button is selected.
     * @param event loads Modify Product screen if Product is selected.
     * @throws IOException
     */
    @FXML
    void onActionModifyProduct(ActionEvent event) throws IOException {
        System.out.println("Modify Product Button Pressed");
        selectedProduct = productTableView.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            System.out.println("Product not selected.  Please select a product.");
            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Please select a Product to modify.");
            alert.showAndWait();
            return;
        }

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("modifyProductScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();     //  this is how you center the screen!!!
        stage.show();
    }

    /**
     * fires when Exit button is selected.
     * @param event closes application.
     */
    @FXML
    void onActionExitButton(ActionEvent event) {
        System.out.println("Exit button pressed");
        System.exit(0);
    }

    /**
     * initializes Main Screen.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Screen initialized");

//  -----------------------  Assigns tableview columns with values  --------------------------------------
        partTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partInStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        productTableView.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productInStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        //
    }

}
