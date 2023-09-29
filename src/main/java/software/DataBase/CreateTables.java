package software.DataBase;

import java.sql.SQLException;

public class CreateTables {

    public static void main(String[] args) throws SQLException {
        String req = "create table MotsDePasses";
        DataBaseConnection.getInstance().getConnection().prepareStatement(req);
    }
}
