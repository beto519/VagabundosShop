/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configuracion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 *
 * @author VINKOs
 */
public class DBHelper {
    private String parOp = "";
    private Connection connection;
    private String error;
    private String controller = "com.mysql.jdbc.Driver";
    private String database = "jdbc:mysql://bfbkuvpw7umrcsbqjoms-mysql.services.clever-cloud.com/bfbkuvpw7umrcsbqjoms"+parOp;
    private String user = "u2lqklodizhwhirf";
    private String password = "dztvKBJuvAS7tMKdErn6";

    public DBHelper() {

    }

    public DBHelper(String controller, String database, String user, String password) {
        this.controller = controller;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    public boolean connect() {
        try {
            Class.forName(this.controller);

            setConnection(DriverManager.getConnection(this.database, this.user, this.password));
        } catch (ClassNotFoundException e) {//Sucede si no se encuentra el driver
            error = e.getMessage();
            return false;
        } catch (SQLException e) {//Sucede si la conexion falla
            error = e.getMessage();
            return false;
        }
        return true;
    }

    public Object execute(String sql, boolean data_manipule) {

        try {
            Statement sentencia = getConnection().createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);

            if (data_manipule) {
                if (sentencia.executeUpdate(sql) > 0) {
                    sentencia.close();
                    return true;
                } else {
                    sentencia.close();
                    return false;
                }
            } else {
                ResultSet resultado = sentencia.executeQuery(sql);
                return resultado;
            }
        } catch (SQLException ex) {
            this.error = ex.getMessage();
            return false;
        }
    }

    public void disconnect() {
        try {
            getConnection().close();
        } catch (Exception ex) {
            this.error = ex.getMessage();
        }
    }

    public String getMensajeError() {
        return getError();
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getController() {
        return controller;
    }

    public void setController(String controller) {
        this.controller = controller;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
