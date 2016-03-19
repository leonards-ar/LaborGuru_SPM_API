package org.apache.commons.support;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by federicobarreraoro on 3/18/16.
 */
public class BasicDataSource extends org.apache.commons.dbcp2.BasicDataSource {

    @Override
    public Connection getConnection(String user, String pass) throws SQLException {
        return super.getConnection();
    }
}
