module com.amalitech.javamongodbcrud {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires org.slf4j;
    requires jbcrypt;
    requires org.mongodb.driver.core;
//    requires com.amalitech.javamongodbcrud;
    requires javafx.graphics;


    opens com.amalitech.javamongodbcrud to javafx.fxml;
    exports com.amalitech.javamongodbcrud;
}