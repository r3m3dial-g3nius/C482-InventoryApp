package basemodel;

import javafx.collections.FXCollections;
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
 * This class initializes and controls the Add Product Screen.
 */
public class AddProductScreenController implements Initializable {

    Stage stage;
    Parent scene;
    private ObservableList<Part> tempAssPartsList = FXCollections.observableArrayList();

    /**
     * Add Associated Parts button.
     */
    @FXML
    private Button addAssPartButton;

    /**
     * part ID column for associated parts.
     */
    @FXML
    private TableColumn<?, ?> assPartIdCol;

    /**
     * number of associated parts in Inventory column.
     */
    @FXML
    private TableColumn<?, ?> assPartInStockColumn;

    /**
     * associated part name column.
     */
    @FXML
    private TableColumn<?, ?> assPartNameCol;

    /**
     * associated part price column.
     */
    @FXML
    private TableColumn<?, ?> assPartPriceCol;

    /**
     * Table view of all parts associated with product.
     */
    @FXML
    private TableView<Part> assPartTableView;

    /**
     * part ID column.
     */
    @FXML
    private TableColumn<?, ?> partIdCol;

    /**
     * column displaying quantity of parts in current Inventory.
     */
    @FXML
    private TableColumn<?, ?> partInStockColumn;

    /**
     * part name column.
     */
    @FXML
    private TableColumn<?, ?> partNameCol;

    /**
     * part price column.
     */
    @FXML
    private TableColumn<?, ?> partPriceCol;

    /**
     * area to enter name or ID of part to search.
     */
    @FXML
    private TextArea partSearchText;

    /**
     * table view of all parts in Inventory.
     */
    @FXML
    private TableView<Part> partTableView;

    /**
     * product ID (auto generated).
     */
    @FXML
    private TextField productIdText;

    /**
     * allows user entry of quantity of product in current Inventory.
     */
    @FXML
    private TextField productInStockText;

    /**
     * allows user entry of maximum quantity of products allowed in Inventory.
     */
    @FXML
    private TextField productMaxText;

    /**
     * allows user entry of minimum quantity of products allowed in Inventory.
     */
    @FXML
    private TextField productMinText;

    /**
     * allows user entry for product name.
     */
    @FXML
    private TextField productNameText;

    /**
     * allows user entry for product price.
     */
    @FXML
    private TextField productPriceText;

    /**
     * Remove Associated Part button.
     */
    @FXML
    private Button removeAssPartbutton;

    /**
     * fires when Add Associated Part button is pressed.
     * @param event adds selected part to temporary Observable List of associated parts.
     */
    @FXML
    void onActionAddAssociatedPart(ActionEvent event) {
        System.out.println("ADD Associated Part Button Pressed");        //  -----  TEST   --------------------
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            System.out.println("Associated part not selected. Please select a part.");
            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Please select a Part to continue.");
            alert.showAndWait();
            return;
        }

        //  ***********   add code to add selectedPart to associated parts list   ?????????
        tempAssPartsList.add(selectedPart);

        System.out.println(selectedPart.getName() + " added to Associated Parts list");
    }

    /**
     * fires when part search button is pressed.
     * @param event searches Inventory for part based on user defined search from partSearchText.
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
     * fires when user selects Remove Associated Part.
     * @param event removes associated part from associated parts table. User receives error dialog if no part is
     *              selected and confirmation dialog to confirm removal.
     */
    @FXML
    void onActionRemoveAssociatedPart(ActionEvent event) {
        System.out.println("Remove Associated Part Button Pressed");        //  -----  TEST   --------------------
        Part selectedPart = assPartTableView.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            System.out.println("Associated part not selected. Please select a part.");
            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Please select a Part to continue.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm removal");
        alert.setContentText("Are you sure you want to remove associated item?");

        Optional<ButtonType> result = alert.showAndWait();

        if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
            tempAssPartsList.remove(selectedPart);
            System.out.println(selectedPart.getName() + " deleted from Associated Parts list");
        } else if ((result.isPresent()) && (result.get() == ButtonType.CANCEL)) {
            assPartTableView.setItems(tempAssPartsList);
        }
    }

    /**
     * Cancel button
     * @param event Cancels Add Product, returns to Main Screen.
     * @throws IOException
     */
    @FXML
    void onActionCancelAddProductButton(ActionEvent event) throws IOException {
        System.out.println("CANCEL Add Product Button pressed");
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Save button.
     * @param event validates user entered values and delivers appropriate dialog if incorrect. Exits to Main Screen
     *              after successful save.
     * @throws IOException
     */
    @FXML
    void onActionSaveProductButton(ActionEvent event) throws IOException {
        System.out.println("Save Product Button Pressed");

        //   ------------------------------   This is probably WRONG  ------------------------------------
        String name = productNameText.getText();

        try {
            int stock = Integer.parseInt(productInStockText.getText());
            double price = Double.parseDouble(productPriceText.getText());
            int min = Integer.parseInt(productMinText.getText());
            int max = Integer.parseInt(productMaxText.getText());

            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR Invalid Entry");
                alert.setContentText("Minimum Par Value cannot be greater than Maximum Par Value.");
                alert.showAndWait();
                return;
            }

            if (min > stock) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR Invalid Entry");
                alert.setContentText("Quantity In Stock must be greater than Minimum Par Value.");
                alert.showAndWait();
                return;
            }

            if (stock > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR Invalid Entry");
                alert.setContentText("Quantity In Stock cannot exceed Maximum Par Value.");
                alert.showAndWait();
                return;
            }

            Product newProduct = new Product(Inventory.getProductNumber(), name, price, stock, min, max);

            for (Part assParts : tempAssPartsList) {
                newProduct.addAssociatedPart(assParts);
            }

            Inventory.addProduct(newProduct);
            System.out.println("Product: " + name + " added to Inventory");

        } catch (NumberFormatException e) {
            System.out.println("Incorrect entry - NUMBER FORMAT ERROR");
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR Invalid Entry");
            alert.setContentText("Please check fields and enter correct values.");
            alert.showAndWait();
            return;
        }


//   -----------------   EXIT to mainScreen   -------------------------------------------------
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * initializes AddProductScreenController.java; populates part and associated parts table. 
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Add Product Screen Initialized");       //   ----------------------  TEST  -------------

        partTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partInStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        //ObservableList<Part> tempAssPartsList = FXCollections.observableArrayList();    // temp list? pass to assPartList on save?

        assPartTableView.setItems(tempAssPartsList);         //  ----  populate associated parts here?
        assPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        assPartInStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

    }
}
