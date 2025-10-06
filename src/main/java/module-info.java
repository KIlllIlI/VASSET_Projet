module com.upvj.latrix {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.upvj.latrix to javafx.fxml;
    exports com.upvj.latrix;
}