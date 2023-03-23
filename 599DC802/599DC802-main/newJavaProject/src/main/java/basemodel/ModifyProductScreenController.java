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
 * Public class controls the Modify Product Screen.
 * Product productToMod references static variable in MainScreenController.
 * Copy of productToMod (tempProductToMod) used to allow cancelling save and retaining original values.
 *
 */
public class ModifyProductScreenController implements Initializable {

    Stage stage;
    Parent scene;
    Product productToMod;
    Product tempProductToMod;                   //  allows cancel out of mod product without updating assPartsList
    private ObservableList<Part> tempAssPartsList = FXCollections.observableArrayList();

    @FXML
    private Button addAssPartButton;

    @FXML
    private TableColumn<?, ?> assPartIdCol;

    @FXML
    private TableColumn<?, ?> assPartInStockColumn;

    @FXML
    private TableColumn<?, ?> assPartNameCol;

    @FXML
    private TableColumn<?, ?> assPartPriceCol;

    @FXML
    private TableView<Part> assPartTableView;

    @FXML
    private TableColumn<?, ?> partIdCol;

    @FXML
    private TableColumn<?, ?> partInStockColumn;

    @FXML
    private TableColumn<?, ?> partNameCol;

    @FXML
    private TableColumn<?, ?> partPriceCol;

    @FXML
    private TextArea partSearchText;

    @FXML
    private TableView<Part> partTableView;

    @FXML
    private TextField productIdText;

    @FXML
    private TextField productInStockText;

    @FXML
    private TextField productMaxText;

    @FXML
    private TextField productMinText;

    @FXML
    private TextField productNameText;

    @FXML
    private TextField productPriceText;

    @FXML
    private Button removeAssPartbutton;

    /**
     * fires when Add Associated Part button is selected.
     * @param event adds selected part to associated parts list of product being modified.
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

        tempProductToMod.addAssociatedPart(selectedPart);

        System.out.println(selectedPart.getName() + " added to Associated Parts list");
    }

    /**
     * fires when Cancel button is selected.
     * @param event returns user to Main Screen without saving.
     * @throws IOException
     */
    @FXML
    void onActionCancelAddProductButton(ActionEvent event) throws IOException {
        System.out.println("CANCEL Add Product Button pressed");        //  - -----   TEST   -----------
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * fires when Search button is selected.
     * NUMBER FORMAT EXCEPTION - try/catch added to intercept and determine if user entered Part Name or Part ID in search field.
     *
     * @param event searches Inventory for user specified part then updates table view appropriately.
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
     * fires when Remove Associated Part button is selected.
     * @param event removes selected part Associated Parts list.
     */
    @FXML
    void onActionRemoveAssociatedPart(ActionEvent event) {
        System.out.println("REMOVE Associated Part button pressed");
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
            tempProductToMod.deleteAssociatedPart(selectedPart);
            System.out.println(selectedPart.getName() + " deleted from Associated Parts list");
        } else if ((result.isPresent()) && (result.get() == ButtonType.CANCEL)) {
            assPartTableView.setItems(tempProductToMod.getAllAssociatedParts());
        }
    }

    /**
     * fires when Cancel button is selected.
     * @param event loads Main Screen. No Save.
     * @throws IOException
     */
    @FXML
    void onActionCancelModifyProductButton(ActionEvent event) throws IOException {
        System.out.println("Cancel Add Product Button Pressed");
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();             //  center screen
        stage.show();
    }

    /**
     * fires when Save button is selected.
     * NUMBER FORMAT EXCEPTION - try/catch added to intercept and determine if user entered invalid data type.
     * @param event upon successful input validation, saves part to inventory and exits to Main Screen.
     * @throws IOException
     */
    @FXML
    void onActionSaveProductButton(ActionEvent event) throws IOException {
        System.out.println("Save Product Button Pressed");

        //   ------------------------------   add code to save Product   ------------------------------------

        String name = productNameText.getText();

        try {
            int stock = Integer.parseInt(productInStockText.getText());
            double price = Double.parseDouble(productPriceText.getText());
            int min = Integer.parseInt(productMinText.getText());
            int max = Integer.parseInt(productMaxText.getText());
            tempAssPartsList = tempProductToMod.getAllAssociatedParts();

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

            Product modifiedProduct = new Product(productToMod.getId(),name, price, stock, min, max);
            for (Part assParts : tempAssPartsList) {
                modifiedProduct.addAssociatedPart(assParts);
            }

            Inventory.updateProduct(Inventory.getAllProducts().indexOf(productToMod), modifiedProduct);


        } catch (NumberFormatException e) {
//            System.out.println("Incorrect entry - NUMBER FORMAT ERROR");
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR Invalid Entry");
            alert.setContentText("Please check fields and enter correct values.");
            alert.showAndWait();
            return;
        }

//   ------------------------------   Exit to mainScreen  ------------------------------------
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();
        stage.show();

    }

    /**
     * initializes Modify Product screen.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Modify Product Screen initialized");
        productToMod = MainScreenController.getSelectedProduct();

// ---------------------  vvvvv  -----   copy of productToMod   -----   vvvvvv     -------------------------------
        tempProductToMod = new Product(productToMod.getId(), productToMod.getName(), productToMod.getPrice(),
                productToMod.getStock(), productToMod.getMin(), productToMod.getMax());

        for (Part eachPart : productToMod.getAllAssociatedParts()) {
            tempProductToMod.addAssociatedPart(eachPart);
        }

        partTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partInStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        assPartTableView.setItems(tempProductToMod.getAllAssociatedParts());

        assPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        assPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        assPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        assPartInStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        productIdText.setText(String.valueOf(tempProductToMod.getId()));
        productNameText.setText(tempProductToMod.getName());
        productInStockText.setText(String.valueOf(tempProductToMod.getStock()));
        productPriceText.setText(String.valueOf(tempProductToMod.getPrice()));
        productMinText.setText(String.valueOf(tempProductToMod.getMin()));
        productMaxText.setText(String.valueOf(tempProductToMod.getMax()));
    }
}
