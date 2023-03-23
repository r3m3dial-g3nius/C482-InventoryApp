module bennett.steven.c482.paproject.stevenbennettc482project {
    requires javafx.controls;
    requires javafx.fxml;


    opens basemodel to javafx.fxml;
    exports basemodel;
}