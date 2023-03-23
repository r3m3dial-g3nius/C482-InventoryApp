package basemodel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 *  This class initializes and controls the Add Parts Screen. LOGIC ERROR: try/catch added as int for MachineID or String for Company Name is associated with
 *  InHouse and Outsourced Parts, respectively. FUTURE ENHANCEMENTS: update inventory when part or product is sold.
 */
public class AddPartScreenController implements Initializable {      //   ----   implements Initializable?  Doesn't run with that implementation

    Stage stage;
    Parent scene;

    /**
     * "In House" part button, designates part origin as in house.
     */
    @FXML
    private RadioButton inHousePartButton;

    /**
     * "Outsourced" part button, designates part origin as outsourced.
     */
    @FXML
    private RadioButton outsourcedPartButton;

    /**
     * part ID number (auto generated).
     */
    @FXML
    private TextField partIdText;

    /**
     * allows user entry of quantity of parts currently in Inventory.
     */
    @FXML
    private TextField partInStockText;

    /**
     * allows user entry of Machine ID that produced part.
     */
    @FXML
    private TextField partMachineIDText;

    /**
     * allows user entry of maximum quantity allowed in Inventory.
     */
    @FXML
    private TextField partMaxText;

    /**
     * allows user entry of minimum quantity allowed in Inventory.
     */
    @FXML
    private TextField partMinText;

    /**
     * allows user entry of name of part.
     */
    @FXML
    private TextField partNameText;

    /**
     * This label toggles between Machine ID if manufactured "In House", or Company Name if "Outsourced".
     */
    @FXML
    private Label partOriginLabel;

    /**
     * Toggle group for inHouse and outsourced radio buttons.
     */
    @FXML
    private ToggleGroup partOriginToggle;

    /**
     * allows user entry of price of part.
     */
    @FXML
    private TextField partPriceText;

    /**
     * Cancel button.
     * @param event Cancels out adding of part, returns to Main Screen.
     * @throws IOException
     */
    @FXML
    void onActionCancelAddPartButton(ActionEvent event) throws IOException {
        System.out.println("CANCEL Add Part Button pressed");
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Save button.
     * @param event Saves new part if entered data is valid, otherwise user receives error/warning messages.
     *      * Returns to Main Screen upon successful Save.
     * @throws IOException
     */
    @FXML
    void onActionSavePartButton(ActionEvent event) throws IOException {
        System.out.println("SAVE Add Part Button pressed");

        String name = partNameText.getText();

        try {
            int stock = Integer.parseInt(partInStockText.getText());
            double price = Double.parseDouble(partPriceText.getText());
            int min = Integer.parseInt(partMinText.getText());
            int max = Integer.parseInt(partMaxText.getText());

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

            if (inHousePartButton.isSelected()) {
                int machineId = Integer.parseInt(partMachineIDText.getText());
                Part newInHousePart = new InHouse(Inventory.getPartNumber(), name, price, stock, min, max, machineId);

                Inventory.addPart(newInHousePart);
                System.out.println("New INHOUSE part: " + name + " added");
            } else {
                String companyName = partMachineIDText.getText();
                Part newOutSourcedPart = new Outsourced (Inventory.getPartNumber(), name, price, stock, min, max, companyName);

                Inventory.addPart(newOutSourcedPart);
                System.out.println("New OUTSOURCED part: " + name + " added");
            }

        } catch (NumberFormatException e) {
//            System.out.println("Incorrect entry - NUMBER FORMAT ERROR");
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR Invalid Entry");
            alert.setContentText("Please check fields and enter correct values.");
            alert.showAndWait();
            return;
        }

//  -------------------------------  exits to mainscreen  -----------------------------------------------
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * fires when inHousePartButton is selected.
     * @param actionEvent sets part to be added origin as "In House". Changes partOriginLabel to "Machine ID".
     */
    @FXML
    public void onActionInHouseSelect(ActionEvent actionEvent) {
        System.out.println("inHouse toggled ON");
        partOriginLabel.setText("Machine ID");
    }

    /**
     * fires when outsourcedPartsButton is selected.
     * @param actionEvent sets part to be added origin as "Outsourced". Changes partOriginLabel to "Company Name".
     */
    @FXML
    public void onActionOutsourcedSelected(ActionEvent actionEvent) {
        System.out.println("outsourced toggled ON");
        partOriginLabel.setText("Company Name");
    }

    /**
     * initializes AddPartScreenController.java
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Add Parts Controller Initialized");
    }

    //
}
