module application {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires jssc;
    requires org.jetbrains.annotations;
//    requires jimObjModelImporterJFX;


    opens application to javafx.fxml, javafx.graphics;
    exports application to javafx.fxml, javafx.graphics;
    exports ts;
    exports sokkiacommand;
    exports command;

}