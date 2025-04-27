module application {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires jssc;
    requires org.jetbrains.annotations;


    opens application to javafx.fxml;
    exports application;
    exports ts;
    exports sokkiacommand;
    exports command;
}