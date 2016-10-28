package com.tikape.keskustelupalsta.dao;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.h2.tools.RunScript;

public class Database<T> {

    private boolean debug;
    private String address;

    public Database(String address) throws Exception {
        this.address = address;

        Connection conn = getConnection();

        try {
            // If database has not yet been created, insert content
            RunScript.execute(conn, new FileReader("database-schema.sql"));
            //RunScript.execute(conn, new FileReader("database-import.sql"));
        } catch (Throwable t) {
        }

        conn.close();
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(address, "sa", "");
    }
}
