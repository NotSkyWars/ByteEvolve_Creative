package net.royalbyte.creative.database;


import net.royalbyte.creative.logger.Logger;
import net.royalbyte.creative.logger.enums.LogTypes;

import java.sql.*;

public class MySQL {

    private String host, username, password, database;
    private int port;
    private Connection connection;

    public MySQL(String host, String username, String password, String database, int port) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.database = database;
        this.port = port;

        this.connect();
        this.createTables();
    }

    private void createTables() {
        update("CREATE TABLE IF NOT EXISTS CREATIVE_PLOTS (ID INT(100) NOT NULL AUTO_INCREMENT,UUID VARCHAR(100), NAME VARCHAR(16), FROM_X INT(100), FROM_Z INT(100), TO_X INT(100), TO_Z INT(100), PRIMARY KEY (ID))");
        new Logger(LogTypes.INFO, "§bCreative_Plots §7Table wurde erstellt.");
    }

    private void connect() {
        if (!isConnected()) {
            try {
                this.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?autoReconnect=true", this.username, this.password);
                new Logger(LogTypes.SUCCESS, "Erfolgreich zur MySQL-Datenbank verbunden.");
            } catch (SQLException e) {
                new Logger(LogTypes.ERROR, "Konnte nicht zur MySQL-Datenbank verbinden.");
                e.printStackTrace();
            }
        }
    }

    public void close() {
        if (isConnected()) {
            try {
                this.connection.close();
                new Logger(LogTypes.SUCCESS, "Erfolgreich die MySQL-Datenbank geschlossen.");
            } catch (SQLException e) {
                new Logger(LogTypes.ERROR, "Konnte nicht die MySQL-Datenbank schließen.");
                e.printStackTrace();
            }
        }
    }

    public void update(final String qry) {
        try {
            Statement statement = this.connection.createStatement();
            statement.executeUpdate(qry);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getResult(final String qry) {
        ResultSet resultSet = null;

        try {
            Statement statement = this.connection.createStatement();
            resultSet = statement.executeQuery(qry);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    private boolean isConnected() {
        return this.connection != null;
    }
}
