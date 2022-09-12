package cybersoft.java18.crm.repository;

import cybersoft.java18.crm.jdbc.MysqlConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public abstract class AbstractRepository<T> {
    //getAll
    protected List<T> executeQuery(JdbcExecute<List<T>> process) {
        try {
            Connection connection = MysqlConnection.getConnection();
            //lamda function
            return process.processor(connection);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected T executeSaveAndUpdate(JdbcExecute<T> process) {
        try {
            Connection connection = MysqlConnection.getConnection();
            return process.processor(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
