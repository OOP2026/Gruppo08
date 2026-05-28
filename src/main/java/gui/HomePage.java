package gui;

import controller.Controller;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.Locale;


public class HomePage {
    private static JFrame frame;
    private JPanel basePanel;
    private JTable orarioTable;
    private DefaultTableModel orarioTableModel;

    public HomePage(JFrame callerFrame, Controller controller) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(basePanel);
        frame.pack();
        frame.setVisible(true);
        initializeTable();
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
