package CONTROLADOR;

import MODELO.QueryModel;
import VISTA.SQLQueryJFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

public class SQLQueryController {

    private SQLQueryJFrame view;
    private QueryModel model;

    public SQLQueryController(SQLQueryJFrame view, QueryModel model) {
        this.view = view;
        this.model = model;

        // Asignar listeners a los nuevos botones
        view.setListarParticipantesButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarParticipantes();
            }
        });

        view.setPromedioTiempoGeneroFButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                promedioTiempoGenero("F");
            }
        });

        view.setPromedioTiempoGeneroMButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                promedioTiempoGenero("M");
            }
        });

        view.setParticipantesCarrerasButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                obtenerParticipantesYCarreras();
            }
        });

        view.setCarrerasParticipantesButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                obtenerCarrerasYParticipantes();
            }
        });

        view.setDistanciaPromedioButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                distanciaPromedio();
            }
        });

        view.setSaveButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveResultsToFile();
            }
        });

    }

    private void listarParticipantes() {
        String query = "SELECT * FROM participante";
        try {
            JTable resultTable = model.executeQuery(query);
            view.updateTable(resultTable);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error ejecutando la consulta: " + ex.getMessage());
        }
    }

    private void promedioTiempoGenero(String genero) {
        String query = "SELECT AVG(tiempo) AS promedio_tiempo FROM carrera c " +
                "JOIN participacion p ON c.id = p.id_carrera " +
                "JOIN participante pa ON p.id_participante = pa.id " +
                "WHERE pa.genero = '" + genero + "'";
        try {
            JTable resultTable = model.executeQuery(query);
            view.updateTable(resultTable);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error ejecutando la consulta: " + ex.getMessage());
        }
    }

    private void obtenerParticipantesYCarreras() {
        String query = "SELECT pa.id, pa.nombre, COUNT(p.id_carrera) AS numero_carreras " +
                "FROM participante pa " +
                "JOIN participacion p ON pa.id = p.id_participante " +
                "GROUP BY pa.id, pa.nombre";
        try {
            JTable resultTable = model.executeQuery(query);
            view.updateTable(resultTable);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error ejecutando la consulta: " + ex.getMessage());
        }
    }

    private void obtenerCarrerasYParticipantes() {
        String query = "SELECT c.id, c.distancia, pa.nombre " +
                "FROM carrera c " +
                "JOIN participacion p ON c.id = p.id_carrera " +
                "JOIN participante pa ON p.id_participante = pa.id";
        try {
            JTable resultTable = model.executeQuery(query);
            view.updateTable(resultTable);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error ejecutando la consulta: " + ex.getMessage());
        }
    }

    private void distanciaPromedio() {
        String query = "SELECT AVG(distancia) AS distancia_promedio FROM carrera";
        try {
            JTable resultTable = model.executeQuery(query);
            view.updateTable(resultTable);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, "Error ejecutando la consulta: " + ex.getMessage());
        }
    }

    private void saveResultsToFile() {
        JTable table = view.getResultTable();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("resultados.txt"))) {
            // Escribir los nombres de las columnas con formato
            for (int i = 0; i < table.getColumnCount(); i++) {
                writer.write(String.format("%-20s", table.getColumnName(i)));
            }
            writer.write("\n");
            writer.write("------------------------------------------------------------");
            writer.write("\n");

            // Escribir los datos de cada fila con formato
            for (int i = 0; i < table.getRowCount(); i++) {
                for (int j = 0; j < table.getColumnCount(); j++) {
                    writer.write(String.format("%-20s", table.getValueAt(i, j)));
                }
                writer.write("\n");
            }
            JOptionPane.showMessageDialog(view, "Resultados guardados en resultados.txt");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(view, "Error al guardar los resultados: " + ex.getMessage());
        }
    }

}
