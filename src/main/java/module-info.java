module com.upvj.latrix {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.upvj.latrix to javafx.fxml;
    exports com.upvj.latrix;
}