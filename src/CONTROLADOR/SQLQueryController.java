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
        ejecutarConsultaYGuardarResultados(query);
    }

    private void promedioTiempoGenero(String genero) {
        String query = "SELECT AVG(tiempo) AS promedio_tiempo FROM carrera c " +
                       "JOIN participacion p ON c.id = p.id_carrera " +
                       "JOIN participante pa ON p.id_participante = pa.id " +
                       "WHERE pa.genero = '" + genero + "'";
        ejecutarConsultaYGuardarResultados(query);
    }

    private void obtenerParticipantesYCarreras() {
        String query = "SELECT pa.id, pa.nombre, COUNT(p.id_carrera) AS numero_carreras " +
                       "FROM participante pa " +
                       "JOIN participacion p ON pa.id = p.id_participante " +
                       "GROUP BY pa.id, pa.nombre";
        ejecutarConsultaYGuardarResultados(query);
    }

    private void obtenerCarrerasYParticipantes() {
        String query = "SELECT c.id, c.distancia, pa.nombre " +
                       "FROM carrera c " +
                       "JOIN participacion p ON c.id = p.id_carrera " +
                       "JOIN participante pa ON p.id_participante = pa.id";
        ejecutarConsultaYGuardarResultados(query);
    }

    private void distanciaPromedio() {
        String query = "SELECT AVG(distancia) AS distancia_promedio FROM carrera";
        ejecutarConsultaYGuardarResultados(query);
    }

    private void ejecutarConsultaYGuardarResultados(String query) {
        try {
            System.out.println("Ejecutando consulta: " + query); // Mensaje de depuración
            JTable resultTable = model.executeQuery(query);
            System.out.println("Número de filas en resultado: " + resultTable.getRowCount()); // Depuración
            view.updateTable(resultTable);
            System.out.println("Consulta ejecutada correctamente. Resultados actualizados."); // Mensaje de depuración

            // Guardar resultados en archivo
            guardarResultadosEnArchivo(query, resultTable);
        } catch (SQLException ex) {
            System.err.println("Error ejecutando la consulta: " + ex.getMessage());
            JOptionPane.showMessageDialog(view, "Error ejecutando la consulta: " + ex.getMessage());
        }
    }

    private void guardarResultadosEnArchivo(String query, JTable table) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("resultados.txt", true))) {
            writer.write("------------------------------------------------------------");
            writer.write("\n");
            writer.write("CONSULTAS DE LOS DETALLES DE LA BASE DE DATOS DE LA MARATON");
            writer.write("\n");
            writer.write("------------------------------------------------------------");
            writer.write("\n");
            writer.write("Consulta ejecutada: " + query);
            writer.write("\n");
            for (int i = 0; i < table.getColumnCount(); i++) {
                writer.write(String.format("%-20s", table.getColumnName(i)));
            }
            writer.write("\n");
            writer.write("------------------------------------------------------------");
            writer.write("\n");

            for (int i = 0; i < table.getRowCount(); i++) {
                for (int j = 0; j < table.getColumnCount(); j++) {
                    writer.write(String.format("%-20s", table.getValueAt(i, j)));
                }
                writer.write("\n");
            }
            writer.write("\n");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(view, "Error al guardar los resultados: " + ex.getMessage());
        }
    }

    private void saveResultsToFile() {
        JOptionPane.showMessageDialog(view, "Las consultas ejecutadas se han guardado en resultados.txt");
    }
}

