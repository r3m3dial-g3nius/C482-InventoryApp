package basemodel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;



//   ---------------------------  THIS IS THE FINAL VERSION OF THE PROJECT  ---------------------------------


/**
 * This class creates an application that allows accurate logging of inventory levels.
 *
 */
public class PartsProductsApp extends Application {

    /**
     * displays stage and centers on screen
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PartsProductsApp.class.getResource("mainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Base Model Inventory Application");
        stage.setScene(scene);
        stage.centerOnScreen();     //  this is how you center the screen!!!
        stage.show();
    }

    /**
     * This is the main program screen. This loads sample data and launches the application.
     * @param args
     */
    public static void main(String[] args) {


//   ------------------------------------   vvv   TEST DATA   vvv   ---------------------------------------------------------

        InHouse block = new InHouse(Inventory.getPartNumber(), "Block", 2.10,
                3, 1, 10, 90000);
        Outsourced screw1 = new Outsourced(Inventory.getPartNumber(), "Finger block screw #1",
                0.15, 100, 10, 1000, "The Screw Company");
        Outsourced screw2 = new Outsourced(Inventory.getPartNumber(), "Finger block screw #2",
                0.10, 100, 10, 1000, "The Screw Company");
        Outsourced screw3 = new Outsourced(Inventory.getPartNumber(), "Finger block screw #3",
                0.12, 50, 10, 1000, "Fastenal");
        Outsourced screw4 = new Outsourced(Inventory.getPartNumber(), "Finger block screw #4",
                0.12, 77, 10, 1000, "Fastenal");
        Outsourced screw5 = new Outsourced(Inventory.getPartNumber(), "Retainer screw #601A",
                2.25, 7, 3, 10, "Smith & Nephew");
        InHouse brokenPart = new InHouse(Inventory.getPartNumber(), "Broken part", 5.00, 10, 1,
                100, 66601);
        InHouse broomEnd = new InHouse(Inventory.getPartNumber(), "ACME Deluxe Broom End",
                2.55, 50, 10, 1000, 292352);
        Outsourced broomHandle = new Outsourced(Inventory.getPartNumber(), "WACO Broom Handle", 4.25,
                50, 10, 1000, "WACO Broom Handle Co");

        Product twoScrewFingerBlock = new Product(Inventory.getProductNumber(), "Two screw finger block", 5.00, 10, 5, 100);
        Product fourScrewFingerBlock = new Product(Inventory.getProductNumber(), "Four screw finger block", 10.00, 10, 5, 100);
        Product bagOfScrews = new Product(Inventory.getProductNumber(), "Bag of random screws", 150.00, 10, 5, 20);
        Product bagOfBrokenParts = new Product(Inventory.getProductNumber(), "Bag of broken parts", 55.00, 4, 1, 10);
        Product sackOf61001Shingles = new Product(Inventory.getProductNumber(), "Sack of 61001 shingles", 855.00, 14, 10, 30);
        Product broom = new Product(Inventory.getProductNumber(), "Deluxe Broom", 15, 50, 10, 1000);

        Inventory.addPart(block);
        Inventory.addPart(screw1);
        Inventory.addPart(screw2);
        Inventory.addPart(screw3);
        Inventory.addPart(screw4);
        Inventory.addPart(screw5);
        Inventory.addPart(brokenPart);
        Inventory.addPart(broomEnd);
        Inventory.addPart(broomHandle);


        twoScrewFingerBlock.addAssociatedPart(block);
        twoScrewFingerBlock.addAssociatedPart(screw1);
        twoScrewFingerBlock.addAssociatedPart(screw2);

        fourScrewFingerBlock.addAssociatedPart(block);
        fourScrewFingerBlock.addAssociatedPart(screw1);
        fourScrewFingerBlock.addAssociatedPart(screw2);
        fourScrewFingerBlock.addAssociatedPart(screw3);
        fourScrewFingerBlock.addAssociatedPart(screw4);
        fourScrewFingerBlock.addAssociatedPart(screw5);

        bagOfScrews.addAssociatedPart(screw1);
        bagOfScrews.addAssociatedPart(screw2);
        bagOfScrews.addAssociatedPart(screw3);
        bagOfScrews.addAssociatedPart(screw4);
        bagOfScrews.addAssociatedPart(screw5);

        bagOfBrokenParts.addAssociatedPart(brokenPart);
        bagOfBrokenParts.addAssociatedPart(brokenPart);
        bagOfBrokenParts.addAssociatedPart(brokenPart);
        bagOfBrokenParts.addAssociatedPart(brokenPart);

        broom.addAssociatedPart(broomEnd);
        broom.addAssociatedPart(broomHandle);

        Inventory.addProduct(twoScrewFingerBlock);
        Inventory.addProduct(fourScrewFingerBlock);
        Inventory.addProduct(bagOfScrews);
        Inventory.addProduct(bagOfBrokenParts);
        Inventory.addProduct(sackOf61001Shingles);
        Inventory.addProduct(broom);

//        Transaction newPurchase = new Transaction();
//
//        newPurchase.addToCart(bagOfBrokenParts);
//        newPurchase.addToCart(twoScrewFingerBlock);
//        newPurchase.addToCart(fourScrewFingerBlock);
//        newPurchase.addToCart(bagOfScrews);
//
//        newPurchase.checkout();


//   ------------------------------------   ^^^   TEST DATA   ^^^   ---------------------------------------------------------


//----------------------   LAUNCH   ---------------------------------------------------------------------

    launch(args);
    }
}