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
 * Public class that controls the Modify Parts Screen.
 * Part partToMod references static variable in MainScreenController to relay values.
 */
public class ModifyPartScreenController implements Initializable {

    Stage stage;
    Parent scene;
    Part partToMod;

    @FXML
    private Label partOriginLabel;

    @FXML
    private RadioButton inHousePartButton;

    @FXML
    private RadioButton outsourcedPartButton;

    @FXML
    private ToggleGroup partOriginToggle;

    @FXML
    private TextField partIdText;

    @FXML
    private TextField partInStockText;

    @FXML
    private TextField partMachineIDText;

    @FXML
    private TextField partMaxText;

    @FXML
    private TextField partMinText;

    @FXML
    private TextField partNameText;

    @FXML
    private TextField partPriceText;

    /**
     * fires when In House radio button is selected.
     * @param actionEvent sets partOriginLabel to "Machine ID".
     */
    @FXML
    public void onActionInHouseSelect(ActionEvent actionEvent) {
        System.out.println("inHouse toggled ON");
        partOriginLabel.setText("Machine ID");          //  ---------  this should be partOriginLabel ????

    }

    /**
     * fires when Outsourced radio button is selected.
     * @param actionEvent sets partOriginLabel to "Company Name".
     */
    @FXML
    public void onActionOutsourcedSelected(ActionEvent actionEvent) {
        System.out.println("outsourced toggled ON");
        partOriginLabel.setText("Company Name");          //  ---------  this should be partOriginLabel ????
    }

    /**
     * fires when Cancel button is selected.
     * @param event loads Main Screen. No save.
     * @throws IOException
     */
    @FXML
    void onActionCancelModifyPartButton(ActionEvent event) throws IOException {
        System.out.println("CancelAddPartButton pressed");
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * fires when Save button is selected.
     * @param event upon successful input validation, saves part to inventory and exits to Main Screen.
     * @throws IOException
     */
    @FXML
    void onActionSavePartButton(ActionEvent event) throws IOException {
        System.out.println("SavePartButton pressed");

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
                Inventory.updatePart(Inventory.getAllParts().indexOf(partToMod), newInHousePart);
                System.out.println("New INHOUSE part: " + name + " added");
            } else {
                String companyName = partMachineIDText.getText();
                Part newOutSourcedPart = new Outsourced (Inventory.getPartNumber(), name, price, stock, min, max, companyName);
                Inventory.updatePart(Inventory.getAllParts().indexOf(partToMod), newOutSourcedPart);
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


        //   ------------------------------   Save and Exit to mainScreen   ------------------------------------

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("mainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.centerOnScreen();
        stage.show();
        //
    }

    /**
     * initializes Modify Part screen.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Modify Part Screen Initialized");
        partToMod = MainScreenController.getSelectedPart();

        partIdText.setText(String.valueOf(partToMod.getId()));
        partNameText.setText(partToMod.getName());
        partInStockText.setText(String.valueOf(partToMod.getStock()));
        partPriceText.setText(String.valueOf(partToMod.getPrice()));
        partMinText.setText(String.valueOf(partToMod.getMin()));
        partMaxText.setText(String.valueOf(partToMod.getMax()));

        if (partToMod instanceof InHouse) {
            partMachineIDText.setText(String.valueOf(((InHouse)partToMod).getMachineId()));
            partOriginLabel.setText("Machine ID");
            inHousePartButton.setSelected(true);
        } else {
            partMachineIDText.setText(String.valueOf(((Outsourced)partToMod).getCompanyName()));
            partOriginLabel.setText("Company Name");
            outsourcedPartButton.setSelected(true);
        }

    }

    //
}
