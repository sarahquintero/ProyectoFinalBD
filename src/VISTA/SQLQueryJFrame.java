/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VISTA;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionListener;

public class SQLQueryJFrame extends JFrame {

    private JPanel queryPanel;
    // private JTextField queryField;
    private JTable resultTable;
    private JScrollPane tableScrollPane;

    // Añadir botones para nuevas consultas
    private JButton listarParticipantesButton;
    private JButton promedioTiempoGeneroFButton;
    private JButton promedioTiempoGeneroMButton;
    private JButton participantesCarrerasButton;
    private JButton carrerasParticipantesButton;
    private JButton distanciaPromedioButton;
    private JButton saveButton;

    public SQLQueryJFrame() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Consultas Maraton");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        queryPanel = new JPanel();
        queryPanel.setLayout(new FlowLayout());

        // queryField = new JTextField(30);
        // queryPanel.add(queryField);

        // Inicializar nuevos botones
        listarParticipantesButton = new JButton("Listar Participantes");
        promedioTiempoGeneroFButton = new JButton("Promedio Tiempo (F)");
        promedioTiempoGeneroMButton = new JButton("Promedio Tiempo (M)");
        participantesCarrerasButton = new JButton("Participantes y Carreras");
        carrerasParticipantesButton = new JButton("Carreras y Participantes");
        distanciaPromedioButton = new JButton("Distancia Promedio");
        saveButton = new JButton("Guardar en Archivo");

        // Agregar botones al panel
        queryPanel.add(listarParticipantesButton);
        queryPanel.add(promedioTiempoGeneroFButton);
        queryPanel.add(promedioTiempoGeneroMButton);
        queryPanel.add(participantesCarrerasButton);
        queryPanel.add(carrerasParticipantesButton);
        queryPanel.add(distanciaPromedioButton);
        queryPanel.add(saveButton);

        add(queryPanel, BorderLayout.NORTH);

        resultTable = new JTable();
        tableScrollPane = new JScrollPane(resultTable);
        add(tableScrollPane, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }

    // Métodos para obtener la consulta del campo de texto
    /*
     * public String getQuery() {
     * return queryField.getText();
     * }
     */

    // Método para obtener la tabla de resultados
    public JTable getResultTable() {
        return resultTable;
    }

    // Métodos para asignar listeners a los botones
    public void setListarParticipantesButtonListener(ActionListener listener) {
        listarParticipantesButton.addActionListener(listener);
    }

    public void setPromedioTiempoGeneroFButtonListener(ActionListener listener) {
        promedioTiempoGeneroFButton.addActionListener(listener);
    }

    public void setPromedioTiempoGeneroMButtonListener(ActionListener listener) {
        promedioTiempoGeneroMButton.addActionListener(listener);
    }

    public void setParticipantesCarrerasButtonListener(ActionListener listener) {
        participantesCarrerasButton.addActionListener(listener);
    }

    public void setCarrerasParticipantesButtonListener(ActionListener listener) {
        carrerasParticipantesButton.addActionListener(listener);
    }

    public void setDistanciaPromedioButtonListener(ActionListener listener) {
        distanciaPromedioButton.addActionListener(listener);
    }

    // Método para actualizar la tabla de resultados
    public void updateTable(JTable newTable) {
        System.out.println("Llamando a updateTable"); // Mensaje de depuración
        System.out.println("Número de filas en la nueva tabla: " + newTable.getRowCount()); // Verificar número de filas
        System.out.println("Número de columnas en la nueva tabla: " + newTable.getColumnCount()); // Verificar número de
                                                                                                  // columnas

        // Remover el JScrollPane actual
        remove(tableScrollPane);
        // Actualizar la tabla de resultados con la nueva tabla
        resultTable = newTable;
        // Crear un nuevo JScrollPane con la nueva tabla
        tableScrollPane = new JScrollPane(resultTable);
        // Añadir el nuevo JScrollPane al JFrame
        add(tableScrollPane, BorderLayout.CENTER);
        // Validar y repintar el JFrame para reflejar los cambios
        validate();
        repaint();
        System.out.println("Tabla actualizada y repintada"); // Mensaje de depuración
    }

    public void setSaveButtonListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }

    // Método para limpiar el campo de texto y la tabla de resultados
    /*
     * public void clearQueryAndTable() {
     * queryField.setText("");
     * resultTable.setModel(new DefaultTableModel());
     * }
     */
}
