module com.upvj.latrix {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.upvj.latrix to javafx.fxml;
    exports com.upvj.latrix;
    exports com.upvj.latrix.graphicObjects;
    opens com.upvj.latrix.graphicObjects to javafx.fxml;
    exports com.upvj.latrix.graphicObjects.Rectangles;
    opens com.upvj.latrix.graphicObjects.Rectangles to javafx.fxml;
}