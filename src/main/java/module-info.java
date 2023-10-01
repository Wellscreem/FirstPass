module software.Firstpass {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.datatransfer;
    requires java.desktop;
    requires okhttp3;
    requires jdk.security.auth;


    opens software to javafx.fxml;
    exports software;
    exports software.DataBase;
    opens software.DataBase to javafx.fxml;
    exports software.Languages;
    opens software.Languages to javafx.fxml;
}