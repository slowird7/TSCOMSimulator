module application {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires jssc;


    opens application to javafx.fxml;
    exports application;
    exports ts;
    exports sokkiacommand;
    exports command;
}