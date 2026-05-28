package gui;

import controller.Controller;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class HomePage {
    private static JFrame frame;
    private JPanel basePanel;
    private JTable orarioTable;
    private JLabel wLabel;
    private JLabel mLable;
    private DefaultTableModel orarioTableModel;

    public HomePage(JFrame callerFrame, Controller controller) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(basePanel);
        frame.pack();
        frame.setVisible(true);
        initializeTable();
        wLabel.setText("Benvenuto, " + controller.getSession().getNome());
        if (controller.isStudente())
        {
            mLable.setVisible(true);
            mLable.setText("Matricola: " + controller.getSession().getMatricola().toString());
        }
    }

    private void initializeTable() {
        String[] cols = {"Orario", "Lunedi", "Martedi", "Mercoledi", "Giovedi", "Venerdi"};

        String[] intervalliOrari = {"8:00 - 10:00", "11:00 - 13:00", "14:00 - 16:00"};

        Object[][] dati = new Object[intervalliOrari.length][6];
        for (int i = 0; i < intervalliOrari.length;i++)
            dati[i][0] = intervalliOrari[i];
                /*{
                {"8:00 - 10:00", "", "", "", "", ""},
                {"11:00 - 13:00", "", "", "", "", ""},
                {"14:00 - 16:00", "", "", "", "", ""}
        }; */

        orarioTableModel = new DefaultTableModel(dati, cols) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        orarioTable.setModel(orarioTableModel);
    }

}
