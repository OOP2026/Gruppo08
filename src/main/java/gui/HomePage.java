package gui;

import controller.Controller;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage {
    private JFrame frame;
    private JPanel basePanel;
    private JTable orarioTable1;
    private JLabel wLabel;
    private JLabel mLabel;
    private JButton spostamentoButton;
    private JButton manageButton;
    private JTable orarioTable2;
    private JTable orarioTable3;
    private JButton aggiornaButton1;
    private JButton aggiornaButton2;
    private JButton aggiornaButton3;
    private Controller controller;
    private DefaultTableModel orarioModel1;
    private DefaultTableModel orarioModel2;
    private DefaultTableModel orarioModel3;

    public HomePage(Controller controller) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(basePanel);
        frame.pack();
        frame.setVisible(true);
        this.controller = controller;
        initializeTable();
        wLabel.setText("Benvenuto, " + controller.getSession().getNome());
        if (controller.isStudente())
        {
            mLabel.setVisible(true);
            mLabel.setText("Matricola: " + controller.getSession().getMatricola().toString());

            switch (controller.getAnno())
            {
                case 1:
                    orarioTable2.setVisible(false);
                    orarioTable3.setVisible(false);
                    aggiornaButton2.setVisible(false);
                    aggiornaButton3.setVisible(false);
                    break;
                case 2:
                    orarioTable1.setVisible(false);
                    orarioTable3.setVisible(false);
                    aggiornaButton1.setVisible(false);
                    aggiornaButton3.setVisible(false);
                    break;
                case 3:
                    orarioTable1.setVisible(false);
                    orarioTable2.setVisible(false);
                    aggiornaButton1.setVisible(false);
                    aggiornaButton2.setVisible(false);
                    break;
                default:
                    break;
            }
        }
        aggiornaButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable(1);
            }
        });
        aggiornaButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable(2);
            }
        });
        aggiornaButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTable(3);
            }
        });
    }

    private void initializeTable() {
        DefaultTableModel orarioTableModel;
        String[] cols = {"Orario", "Lunedi", "Martedi", "Mercoledi", "Giovedi", "Venerdi"};

        String[] intervalliOrari = {"8:00 - 10:00", "11:00 - 13:00", "14:00 - 16:00"};

        Object[][] dati = new Object[intervalliOrari.length][6];
        for (int i = 0; i < intervalliOrari.length;i++)
            dati[i][0] = intervalliOrari[i];


        orarioTableModel = new DefaultTableModel(dati, cols) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        orarioModel1 = orarioTableModel;
        orarioModel2 = orarioTableModel;
        orarioModel3 = orarioTableModel;

        orarioTable1.setModel(orarioModel1);
        orarioTable2.setModel(orarioModel2);
        orarioTable3.setModel(orarioModel3);
    }

    private void updateTable(int anno) {
        Object[][] mtx = controller.getOrarioMtx(anno);

        if (mtx == null)
            return;

        switch(anno) {
            case 1:
                orarioModel1.setRowCount(0);
                for(int i = 0; i < Math.min(mtx.length, 3); i++)
                {
                    orarioModel1.addRow(mtx[i]);
                }
                break;
            case 2:
                orarioModel2.setRowCount(0);
                for(int i = 0; i < Math.min(mtx.length, 3); i++)
                {
                    orarioModel2.addRow(mtx[i]);
                }
                break;
            case 3:
                orarioModel3.setRowCount(0);
                for(int i = 0; i < Math.min(mtx.length, 3); i++)
                {
                    orarioModel3.addRow(mtx[i]);
                }
                break;
            default:
                break;
        }
    }
}
