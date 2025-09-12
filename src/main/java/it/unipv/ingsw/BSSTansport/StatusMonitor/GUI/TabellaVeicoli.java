package it.unipv.ingsw.BSSTansport.StatusMonitor.GUI;

import it.unipv.ingsw.BSSTansport.StatusMonitor.data.Veicolo;
import it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.ArrayList;

public class TabellaVeicoli {
    private JFrame frame;
    private JTable vtable, gtable;
    private JTextField[] filtri;

    String[] colonne = {"", "Stato", "Veicolo", "Linea", "Ultima Fermata", "Prossima Fermata", "Ritardo"};
    private DefaultTableModel vtableModel, gtableModel;

    String[] campifiltri = {"Stato", "Veicolo", "Linea", "Ultima Fermata", "Prossima Fermata", "Ritardo"};
    Integer[] filtriColonnaMap;
    private TableRowSorter<DefaultTableModel> vsorter;

    IntermediarioFlottaTabella flotta = new IntermediarioFlottaTabella();

    public TabellaVeicoli() {
        // frame
        frame = new JFrame("Stato flotta");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1700, 956);
        frame.setLayout(new BorderLayout());

        //vtable
        vtable = new JTable();
        gtable = new JTable();
        resetTabelle();
        vtable.setFillsViewportHeight(true);
        gtable.setFillsViewportHeight(true);

        //filtri
        // mappa dall'indice del filtro all'indice della colonna
        // filtriColonnaMap[i] = j s.t. campifiltri[i]==colonne[j]
        filtriColonnaMap = new Integer[campifiltri.length];
        for (int i = 0; i < campifiltri.length; i++) {
            filtriColonnaMap[i] = Utils.arrayIndexOf(colonne, campifiltri[i]);
        }

        //pannello
        JPanel filtroPanel = new JPanel();
        filtroPanel.setLayout(new BoxLayout(filtroPanel, BoxLayout.LINE_AXIS));

        //textfields collection
        filtri = new JTextField[campifiltri.length];
        //creazione label e textfield
        filtroPanel.add(Box.createRigidArea(new Dimension(20, 0)));

        for (int i = 0; i < campifiltri.length; i++) {
            filtroPanel.add(new JLabel(campifiltri[i] + ":"));
            filtroPanel.add(Box.createRigidArea(new Dimension(10, 0)));
            JTextField filtroField = new JTextField();
            filtroPanel.add(filtroField);
            filtri[i] = filtroField;

            filtroPanel.add(Box.createRigidArea(new Dimension(20, 0)));

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
            });
        }

        //assembla
        JPanel guastiPanel = new JPanel();
        guastiPanel.setPreferredSize(new Dimension(0, 250));
        guastiPanel.setLayout(new BorderLayout());
        guastiPanel.add(new JLabel("Guasti"), BorderLayout.NORTH);
        guastiPanel.add(new JScrollPane(gtable), BorderLayout.CENTER);


        frame.add(filtroPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(vtable), BorderLayout.CENTER);
        frame.add(guastiPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void resetTabelle() {
        vtableModel = new DefaultTableModel(colonne, 0);
        vsorter = new TableRowSorter(vtableModel);
        vtable.setModel(vtableModel);
        vtable.setRowSorter(vsorter);

        gtableModel = new DefaultTableModel(colonne, 0);
        TableRowSorter gsorter = new TableRowSorter(gtableModel);
        gtable.setModel(gtableModel);
        gtable.setRowSorter(gsorter);
    }

    private void filtra() {
        ArrayList<RowFilter<Object, Object>> rowFilters = new ArrayList<>();
        for (int i = 0; i < filtri.length; i++) {
            String testo = filtri[i].getText().trim();
            if (testo.isEmpty()) {
                rowFilters.add(RowFilter.regexFilter("(?i)" + testo, filtriColonnaMap[i])); //(?i) makes the formula case-insensitive
            }
        }

        RowFilter<Object, Object> intersezioneFiltri = rowFilters.isEmpty() ? null : RowFilter.andFilter(rowFilters);
        vsorter.setRowFilter(intersezioneFiltri);
    }

    public void update(Veicolo[] veicoli) {
        this.flotta.update(veicoli);

        resetTabelle();

        for (VeicoloGUI veicolo : this.flotta.veicoli) {
            vtableModel.addRow(veicolo.aRiga());
            if (veicolo.isGuasto())
                gtableModel.addRow(veicolo.aRiga());
        }

        filtra();
    }
}
