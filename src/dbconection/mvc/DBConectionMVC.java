package dbconection.mvc;

import CONTROLADOR.SQLQueryController;
import MODELO.DBConexion;
import MODELO.QueryModel;
import VISTA.SQLQueryJFrame;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DBConectionMVC {
    public static void main(String[] args) {
        try {
            // Conectar a la base de datos
            Connection connection = DBConexion.getConnection();

            if (connection != null && !connection.isClosed()) {
                System.out.println("Conexión a la base de datos establecida correctamente.");

                // Crear el modelo, la vista y el controlador
                QueryModel model = new QueryModel(connection);
                SQLQueryJFrame view = new SQLQueryJFrame();
                new SQLQueryController(view, model);

                // Mostrar la vista
                view.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo establecer la conexión a la base de datos.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error conectando a la base de datos: " + e.getMessage());
        }
    }
}
