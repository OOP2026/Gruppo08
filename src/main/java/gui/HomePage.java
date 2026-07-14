package gui;

import controller.*;
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
    private DefaultTableModel orarioModel1;
    private DefaultTableModel orarioModel2;
    private DefaultTableModel orarioModel3;
    private final LectureService ls = new LectureService();

    public HomePage() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(basePanel);
        frame.pack();
        frame.setVisible(true);
        initializeTable();
        wLabel.setText("Benvenuto, " + SessionManager.getInstance().getSession().getFname());
        if (SessionManager.getInstance().isStudent()) {
            mLabel.setVisible(true);
            mLabel.setText("Matricola: " + SessionManager.getInstance().getStudentId());

            switch (SessionManager.getInstance().getAcademicYear()) {
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
        String[] cols = ls.getCols();

        String[] timeInterval = { "8:00 - 10:00", "11:00 - 13:00", "14:00 - 16:00" };

        Object[][] data = new Object[timeInterval.length][6];
        for (int i = 0; i < timeInterval.length; i++)
            data[i][0] = timeInterval[i];

        orarioTableModel = new DefaultTableModel(data, cols) {
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

    private void updateTable(int year) {
        Object[][] mtx = null;

        try {
            mtx = ls.getLecturesMtx(year);
        }
        catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(frame, "Impossibile aggiornare la tabella");
        }

        if (mtx == null)
            return;

        switch (year) {
            case 1:
                orarioModel1.setRowCount(0);
                orarioModel1 = new DefaultTableModel(mtx, ls.getCols());
                orarioTable1.setModel(orarioModel1);
                break;
            case 2:
                orarioModel2.setRowCount(0);
                orarioModel2 = new DefaultTableModel(mtx, ls.getCols());
                orarioTable2.setModel(orarioModel2);
                break;
            case 3:
                orarioModel3.setRowCount(0);
                orarioModel3 = new DefaultTableModel(mtx, ls.getCols());
                orarioTable3.setModel(orarioModel3);
                break;
            default:
                break;
        }
    }
}
