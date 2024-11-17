package MODELO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryModel {
    private Connection connection;

    public QueryModel(Connection connection) {
        this.connection = connection;
    }

    public JTable executeQuery(String query) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        DefaultTableModel tableModel = new DefaultTableModel();
        for (int i = 1; i <= columnCount; i++) {
            tableModel.addColumn(metaData.getColumnName(i));
        }

        while (resultSet.next()) {
            Object[] row = new Object[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                row[i - 1] = resultSet.getObject(i);
            }
            tableModel.addRow(row);
        }

        return new JTable(tableModel);
    }

    // Método para ejecutar consultas INSERT, UPDATE o DELETE
    public int executeUpdate(String query) throws SQLException {
        if (connection == null) {
            throw new IllegalStateException("La conexión a la base de datos no está inicializada.");
        }

        try (Statement stmt = connection.createStatement()) {
            return stmt.executeUpdate(query); // Devuelve el número de filas afectadas
        }
    }

    // Método para cerrar la conexión
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace(); // Maneja la excepción
            }
        }
    }
}
