package it.unipv.ingsw.BSSTansport.StatusMonitor.GUI;

import it.unipv.ingsw.BSSTansport.StatusMonitor.data.Veicolo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;

public class TabellaVeicoli {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;
    private JTextField filtroField;
    IntermediarioFlottaTabella flotta;

    public TabellaVeicoli(IntermediarioFlottaTabella flotta) {
        //frame
        frame = new JFrame("Stato flotta");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1700, 956);
        frame.setLayout(new BorderLayout());

        //dati
        this.flotta = flotta;

        //tabella
        String[] colonne = {"", "Stato", "Veicolo", "Linea", "Ultima Fermata", "Prossima Fermata", "Ritardo"};

        tableModel = new DefaultTableModel(colonne, 0);
        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);

        // filtraggio
        sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);


        JPanel filtroPanel = new JPanel(new BorderLayout());
        filtroField = new JTextField();
        filtroPanel.add(new JLabel("Filtro: "), BorderLayout.WEST);
        filtroPanel.add(filtroField, BorderLayout.CENTER);

        // logica filtraggio
        filtroField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filtra();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filtra();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filtra();
            }

            private void filtra() {
                String testo = filtroField.getText();
                if (testo.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + testo));
                }
            }
        });

        // assemblaggio
        frame.add(filtroPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        frame.setVisible(true);
    }

    public void update(Veicolo[] veicoli) {
        this.flotta.update(veicoli);

        tableModel.setRowCount(0);

        for (VeicoloGUI veicolo : this.flotta.veicoli) {
            tableModel.addRow(veicolo.aRiga());
        }
    }
}
