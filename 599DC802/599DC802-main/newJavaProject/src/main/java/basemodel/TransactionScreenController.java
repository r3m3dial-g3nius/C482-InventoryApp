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

public class TransactionScreenController implements Initializable {
    
    private ObservableList<Product> tempShoppingCartList = FXCollections.observableArrayList();
    Stage stage;
    Parent scene;

    double total = 0;

    @FXML
    private TableColumn<?, ?> productInStockinCartCol;

    @FXML
    private TableColumn<?, ?> productPriceinCartCol;

    @FXML
    private Button addToCartButton;

    @FXML
    private TextField address1Text;

    @FXML
    private TextField address2Text;

    @FXML
    private TextField billingAddress1Text;

    @FXML
    private TextField billingAddress2Text;

    @FXML
    private TextField billingCityText;

    @FXML
    private TextField billingFirstNameText;

    @FXML
    private TextField billingLastNameText;

    @FXML
    private RadioButton billingSameAsShippingButton;

    @FXML
    private TextField billingStateText;

    @FXML
    private TextField billingZipText;

    @FXML
    private Button cancelTransactionButton;

    @FXML
    private TextField cartTotalText;

    @FXML
    private Button checkoutButton;

    @FXML
    private TextField cityText;

    @FXML
    private TextField creditCVVText;

    @FXML
    private TextField creditCardNumberText;

    @FXML
    private TextField creditExpirationText;

    @FXML
    private TextField firstNameText;

    @FXML
    private TextField lastNameText;

    @FXML
    private TableColumn<?, ?> productIDinCartCol;

    @FXML
    private TableColumn<?, ?> productIdCol;

    @FXML
    private TableColumn<?, ?> productInStockColumn;

    @FXML
    private TableColumn<?, ?> productNameCol;

    @FXML
    private TableColumn<?, ?> productNameinCartCol;

    @FXML
    private TableColumn<?, ?> productPriceCol;

    @FXML
    private Button productSearchButton;

    @FXML
    private TextArea productSearchText;

    @FXML
    private TableView<Product> productTableView;

    @FXML
    private Button removeFromCartButton;

    @FXML
    private TableView<Product> shoppingCartTableView;

    @FXML
    private TextField stateText;

    @FXML
    private TextField zipText;

    @FXML
    void onActionAddToCart(ActionEvent event) {
        System.out.println("Add to Cart Button Pressed");        //  -----  TEST   --------------------
        Product selectedProduct = productTableView.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            System.out.println("Product not selected. Please select a part.");
            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Please select a Product to continue.");
            alert.showAndWait();
            return;
        }

        //  ***********   add selectedPart to shopping cart   ---------------------------
        tempShoppingCartList.add(selectedProduct);
        total+= selectedProduct.getPrice();
        cartTotalText.setText(String.valueOf(total));       //  update total

        System.out.println(selectedProduct.getName() + " added to Associated Parts list");
    }

    @FXML
    void onActionBillingSameAsShipping(ActionEvent event) {
        billingFirstNameText = firstNameText;
        billingLastNameText = lastNameText;
        billingAddress1Text = address1Text;
        billingAddress2Text = address2Text;
        billingCityText = cityText;
        billingStateText = stateText;
        billingZipText = zipText;
    }

    @FXML
    void onActionCancelTransaction(ActionEvent event) throws IOException {
        System.out.println("CANCEL Button pressed");
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();
        stage.show();
    }

    @FXML
    void onActionCheckout(ActionEvent event) {
        System.out.println("Checkout Button Pressed");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Proceed to checkout?");
        alert.setContentText("Finalize purchase?");

        Optional<ButtonType> result = alert.showAndWait();

        if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
            try {
                String firstName = firstNameText.getText();
                String lastName = lastNameText.getText();
                String address1 = address1Text.getText();
                String address2 = address2Text.getText();
                String city = cityText.getText();
                String state = stateText.getText();
                int zip = Integer.parseInt(zipText.getText());

                int cardOnFile = Integer.parseInt(creditCardNumberText.getText());
                String expDate = creditExpirationText.getText();
                int ccv = Integer.parseInt(creditCVVText.getText());

                String billingFirstName = billingFirstNameText.getText();
                String billingLastName = billingLastNameText.getText();
                String billingAddress1 = billingAddress1Text.getText();
                String billingAddress2 = billingAddress2Text.getText();
                String billingCity = billingCityText.getText();
                String billingState = billingStateText.getText();
                int billingZip = Integer.parseInt(billingZipText.getText());

                Customer newCustomer = new Customer(Customer.getCustomerId(),firstName, lastName, address1, address2, city,
                        state, zip, cardOnFile, expDate, ccv, billingFirstName, billingLastName, billingAddress1,
                        billingAddress2, billingCity, billingState, billingZip);
                Transaction transaction = new Transaction();

                for (Product eachProduct : tempShoppingCartList) {      //  copy products from temp Cart to real Cart
                    transaction.addToCart(eachProduct);
                }

                newCustomer.addCustomerToList(newCustomer);             //   adds customer to customer list
                transaction.checkout();

            } catch (NumberFormatException e) {
                System.out.println("Incorrect entry - NUMBER FORMAT ERROR");
                e.printStackTrace();

                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("ERROR Invalid Entry");
                alert1.setContentText("Please check fields and enter correct values.");
                alert1.showAndWait();
                return;
            }
        } else if ((result.isPresent()) && (result.get() == ButtonType.CANCEL)) {
            return;
        }

    }

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

        if (productSearchResult.size() == 1) {                             // highlights the ONLY result
            productTableView.getSelectionModel().select(0);               // how do you change the color???
        }
    }

    @FXML
    void onActionRemoveFromCart(ActionEvent event) {
        System.out.println("Remove from Cart Button Pressed");        //  -----  TEST   --------------------
        Product selectedProduct = shoppingCartTableView.getSelectionModel().getSelectedItem();

        if (selectedProduct == null) {
            System.out.println("Associated product not selected. Please select a product.");
            Alert alert = new Alert (Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Please select a product to continue.");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm removal");
        alert.setContentText("Are you sure you want to remove from cart?");

        Optional<ButtonType> result = alert.showAndWait();

        if ((result.isPresent()) && (result.get() == ButtonType.OK)) {
            tempShoppingCartList.remove(selectedProduct);
            total-=selectedProduct.getPrice();
            cartTotalText.setText(String.valueOf(total));
            System.out.println(selectedProduct.getName() + " deleted from Cart");
        } else if ((result.isPresent()) && (result.get() == ButtonType.CANCEL)) {
            shoppingCartTableView.setItems(tempShoppingCartList);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Transaction Screen Initialized");       //   ----------------------  TEST  -------------

        productTableView.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productInStockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        shoppingCartTableView.setItems(tempShoppingCartList);         //  ----  populate shopping cart here
        productIDinCartCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameinCartCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPriceinCartCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productInStockinCartCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }
}
