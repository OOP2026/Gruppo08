package gui;

import controller.*;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class HomePage {
    private JFrame frame;
    private JPanel basePanel;
    private JTable orarioTable1;
    private JLabel wLabel;
    private JLabel mLabel;
    private JButton spostamentoButton;
    private JButton manageReqButton;
    private JTable orarioTable2;
    private JTable orarioTable3;
    private JButton aggiornaButton1;
    private JButton aggiornaButton2;
    private JButton aggiornaButton3;
    private JScrollPane scrollPane1;
    private JScrollPane scrollPane2;
    private JScrollPane scrollPane3;
    private JButton logoutButton;
    private JButton adminButton;
    private DefaultTableModel orarioModel1;
    private DefaultTableModel orarioModel2;
    private DefaultTableModel orarioModel3;
    private final LectureService ls = new LectureService();

    public HomePage() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(basePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        initializeTable();
        updateTable(1);
        updateTable(2);
        updateTable(3);
        scrollPane1.getViewport().setBackground(new Color(49, 49, 49));
        scrollPane2.getViewport().setBackground(new Color(49, 49, 49));
        scrollPane3.getViewport().setBackground(new Color(49, 49, 49));
        spostamentoButton.setVisible(false);
        manageButton.setVisible(false);
        wLabel.setText("Benvenuto, " + SessionManager.getInstance().getFname());
        if (SessionManager.getInstance().isStudent()) {
            mLabel.setVisible(true);
            mLabel.setText("Matricola: " + SessionManager.getInstance().getStudentId());

            switch (SessionManager.getInstance().getAcademicYear()) {
                case 1:
                    scrollPane2.setVisible(false);
                    scrollPane3.setVisible(false);
                    aggiornaButton2.setVisible(false);
                    aggiornaButton3.setVisible(false);
                    break;
                case 2:
                    scrollPane1.setVisible(false);
                    scrollPane3.setVisible(false);
                    aggiornaButton1.setVisible(false);
                    aggiornaButton3.setVisible(false);
                    break;
                case 3:
                    scrollPane1.setVisible(false);
                    scrollPane2.setVisible(false);
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

        if (SessionManager.getInstance().isTeacher()) {
            spostamentoButton.setVisible(true);
            if (SessionManager.getInstance().isCoordinator()) {
                manageReqButton.setVisible(true);
                adminButton.setVisible(true);
            }
        }

        spostamentoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                new ReqPage(frame);
            }
        });

        manageReqButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                new ManagePage(frame);
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SessionManager.getInstance().setSession(null);
                frame.dispose();
                new LoginPage().display();
            }
        });
    }

    private void initializeTable() {
        DefaultTableModel orarioTableModel;
        String[] cols = ls.getCols();

        String[] timeInterval = {" "};

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
        } catch (IllegalStateException e) {
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

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        basePanel = new JPanel();
        basePanel.setLayout(new BorderLayout(0, 0));
        basePanel.setBackground(new Color(-13553359));
        basePanel.setForeground(new Color(-13553359));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-13553359));
        panel1.setForeground(new Color(-13553359));
        basePanel.add(panel1, BorderLayout.NORTH);
        wLabel = new JLabel();
        wLabel.setBackground(new Color(-13553359));
        Font wLabelFont = this.$$$getFont$$$(null, Font.BOLD, 20, wLabel.getFont());
        if (wLabelFont != null) wLabel.setFont(wLabelFont);
        wLabel.setForeground(new Color(-2894893));
        wLabel.setText("Benvenuto, Utente");
        panel1.add(wLabel, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        mLabel = new JLabel();
        mLabel.setBackground(new Color(-13553359));
        mLabel.setForeground(new Color(-2894893));
        mLabel.setText("Matricola:");
        mLabel.setVisible(false);
        panel1.add(mLabel, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panel2.setBackground(new Color(-13553359));
        panel2.setForeground(new Color(-13553359));
        basePanel.add(panel2, BorderLayout.SOUTH);
        spostamentoButton = new JButton();
        spostamentoButton.setBackground(new Color(-11579569));
        spostamentoButton.setForeground(new Color(-2894893));
        spostamentoButton.setText("Richiedi Spostamento");
        panel2.add(spostamentoButton);
        manageReqButton = new JButton();
        manageReqButton.setBackground(new Color(-11579569));
        manageReqButton.setForeground(new Color(-2894893));
        manageReqButton.setText("Gestisci Richieste");
        panel2.add(manageReqButton);
        logoutButton = new JButton();
        logoutButton.setBackground(new Color(-11579569));
        logoutButton.setForeground(new Color(-2894893));
        logoutButton.setText("Logout");
        panel2.add(logoutButton);
        adminButton = new JButton();
        adminButton.setBackground(new Color(-11579569));
        adminButton.setForeground(new Color(-2894893));
        adminButton.setText("Gestisci Corso");
        panel2.add(adminButton);
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(9, 4, new Insets(0, 0, 0, 0), -1, -1));
        panel3.setBackground(new Color(-13553359));
        panel3.setForeground(new Color(-13553359));
        basePanel.add(panel3, BorderLayout.CENTER);
        scrollPane1 = new JScrollPane();
        scrollPane1.setBackground(new Color(-13553359));
        scrollPane1.setForeground(new Color(-13553359));
        scrollPane1.setOpaque(true);
        panel3.add(scrollPane1, new com.intellij.uiDesigner.core.GridConstraints(5, 0, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        orarioTable2 = new JTable();
        orarioTable2.setBackground(new Color(-13553359));
        orarioTable2.setForeground(new Color(-2894893));
        orarioTable2.setGridColor(new Color(-11579569));
        orarioTable2.setSelectionBackground(new Color(-11579569));
        orarioTable2.setSelectionForeground(new Color(-2894893));
        scrollPane1.setViewportView(orarioTable2);
        scrollPane2 = new JScrollPane();
        scrollPane2.setBackground(new Color(-13553359));
        scrollPane2.setForeground(new Color(-13553359));
        scrollPane2.setOpaque(true);
        panel3.add(scrollPane2, new com.intellij.uiDesigner.core.GridConstraints(8, 0, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        orarioTable3 = new JTable();
        orarioTable3.setBackground(new Color(-13553359));
        orarioTable3.setForeground(new Color(-2894893));
        orarioTable3.setGridColor(new Color(-11579569));
        orarioTable3.setSelectionBackground(new Color(-11579569));
        orarioTable3.setSelectionForeground(new Color(-2894893));
        scrollPane2.setViewportView(orarioTable3);
        final JLabel label1 = new JLabel();
        label1.setText("Primo anno:");
        panel3.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Secondo anno:");
        panel3.add(label2, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Terzo anno:");
        panel3.add(label3, new com.intellij.uiDesigner.core.GridConstraints(6, 0, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        scrollPane3 = new JScrollPane();
        scrollPane3.setBackground(new Color(-13553359));
        scrollPane3.setForeground(new Color(-13553359));
        scrollPane3.setOpaque(true);
        panel3.add(scrollPane3, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 4, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        orarioTable1 = new JTable();
        orarioTable1.setBackground(new Color(-13553359));
        orarioTable1.setForeground(new Color(-2894893));
        orarioTable1.setGridColor(new Color(-11579569));
        orarioTable1.setSelectionBackground(new Color(-11579569));
        orarioTable1.setSelectionForeground(new Color(-2894893));
        scrollPane3.setViewportView(orarioTable1);
        aggiornaButton1 = new JButton();
        aggiornaButton1.setBackground(new Color(-11579569));
        aggiornaButton1.setForeground(new Color(-2894893));
        aggiornaButton1.setText("Aggiorna");
        panel3.add(aggiornaButton1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        aggiornaButton2 = new JButton();
        aggiornaButton2.setBackground(new Color(-11579569));
        aggiornaButton2.setForeground(new Color(-2894893));
        aggiornaButton2.setText("Aggiorna");
        panel3.add(aggiornaButton2, new com.intellij.uiDesigner.core.GridConstraints(4, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        aggiornaButton3 = new JButton();
        aggiornaButton3.setBackground(new Color(-11579569));
        aggiornaButton3.setForeground(new Color(-2894893));
        aggiornaButton3.setText("Aggiorna");
        panel3.add(aggiornaButton3, new com.intellij.uiDesigner.core.GridConstraints(7, 0, 1, 3, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return basePanel;
    }

}
