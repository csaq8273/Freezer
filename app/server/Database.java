package server;

import models.Skier;

import java.sql.*;
import java.util.List;

/**
 * Created by Ivan on 19.01.2015.
 */
public class Database {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    public String log="";
    private boolean connect() {
        try {
            // this will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.jdbc.Driver");
            // setup the connection with the DB.
            String url = "jdbc:msql://phpmyadmin16.sysproserver.de:1114/web854_Freezer";
            connect = DriverManager
                    .getConnection(url
                            + "user=web854_freezer&password=DitAc3yagCir{");
            log+="Koijok"+connect.getSchema();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
log+=e.getMessage();
            log+=e.getLocalizedMessage();
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            log+=e.getMessage();
            log+=e.getLocalizedMessage();
            return false;
        }
        return true;
    }

    public List<Skier> getSkier() {
        connect();
        String query = "INSERT INTO Skier VALUES(0,'ivan','waldboth','agsagasg',0,'afasfsaf')";
        try {
            // set all the preparedstatement parameters
            PreparedStatement st = connect.prepareStatement(query);
            st.executeQuery();
            st.close();
        } catch (Exception e) {

            e.printStackTrace();
            log += e.getMessage();
            log+=e.getLocalizedMessage();
            return null;
        }

        return null;

    }
}
