package basemodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.text.SimpleDateFormat;

public class Transaction {

    private static ObservableList<Product> shoppingCart = FXCollections.observableArrayList();
    private static int counter = 100;
    private String dateStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new java.util.Date());
    private String transactionId;
    private int numItems = shoppingCart.size();
    private double totalSale = 0.00;

    // new transaction is created when sale screen is initialized

    public Transaction() {
        transactionId = (dateStamp + (++counter));
    }

    public ObservableList<Product> getShoppingCart() {
        return shoppingCart;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public double getTotalSale() {
        return totalSale;
    }

    public boolean addToCart(Product product) {
        if (product.getStock() > 0) {
            shoppingCart.add(product);
            product.setStock(product.getStock() - 1);
            numItems++;
            totalSale+= product.getPrice();
            System.out.println(product.getName() + " added to cart.");
            return true;
        } else {
            System.out.println(product.getName() + " is currently out of stock.");
            return false;
        }
    }

    public void removeFromCart(Product product) {
        shoppingCart.remove(product);
        product.setStock(product.getStock() + 1);
        numItems--;
        totalSale = totalSale - product.getPrice();
    }

    public void checkout() {
        System.out.println("Thanks for shopping!");
        System.out.println("Items in cart: " + numItems);
        for (Product eachProduct : shoppingCart) {
            System.out.println(String.format("%.2f", eachProduct.getPrice()) + "   -   " + eachProduct.getName());
        }
        System.out.println("Total due: " + String.format("%.2f", totalSale));
        System.out.println(transactionId);
    }

    public void cancel() {
        if (shoppingCart.isEmpty()) {
            return;
        } else {
            for (Product eachProduct : shoppingCart) {
                eachProduct.setStock(eachProduct.getStock() + 1);
                shoppingCart.remove(eachProduct);
            }
        }
    }

}
