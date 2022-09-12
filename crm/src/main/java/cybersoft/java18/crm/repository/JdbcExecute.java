package cybersoft.java18.crm.repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface JdbcExecute<T> {
    T processor(Connection connection) throws SQLException;
}
