package gui;

import javax.security.sasl.AuthenticationException;
import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import controller.UserService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class RegPage {
    private JFrame frame;
    private JPanel basePanel;
    private JComboBox<String> roleComboBox;
    private JTextField nomeTextField;
    private JTextField cognomeTextField;
    private JTextField usernameTextField;
    private JPasswordField pswdField;
    private JButton confermaButton;
    private JTextField emailTextField;
    private JComboBox<Integer> ayComboBox;
    private JLabel ayLabel;
    private JButton indietroButton;
    private final UserService ua = new UserService();

    public RegPage(JFrame loginPage) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(basePanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String role = (String) roleComboBox.getSelectedItem();
                String fname = nomeTextField.getText();
                String lname = cognomeTextField.getText();
                String username = usernameTextField.getText();
                String email = emailTextField.getText();
                String pswd = new String(pswdField.getPassword());
                boolean success = true;

//                try {
//                    switch (role) {
//                        case "Studente":
//                            int academicYear = Integer.parseInt((String) ayComboBox.getSelectedItem());
//                            ua.register(academicYear, fname, lname, email, username, pswd);
//                            break;
//                        case "Docente":
//                            ua.register(false, fname, lname, email, username, pswd);
//                            break;
//                        case "Coordinatore":
//                            ua.register(true, fname, lname, email, username, pswd);
//                            break;
//                        default:
//                            JOptionPane.showMessageDialog(basePanel, "Ruolo non valido");
//                            break;
//                    }
//                } catch (AuthenticationException ae) {
//                    JOptionPane.showMessageDialog(basePanel, ae.getMessage());
//                    success = false;
//                }

                switch (role) {
                    case "Studente":
                        try {
                            int academicYear = Integer.parseInt((String) ayComboBox.getSelectedItem());
                            ua.register(academicYear, fname, lname, email, username, pswd);
                        } catch (AuthenticationException ae) {
                            JOptionPane.showMessageDialog(basePanel, ae.getMessage());
                            success = false;
                        }
                        break;
                    case "Docente":
                        try {
                            ua.register(false, fname, lname, email, username, pswd);
                        } catch (AuthenticationException ae) {
                            JOptionPane.showMessageDialog(basePanel, ae.getMessage());
                            success = false;
                        }
                        break;
                    case "Coordinatore":
                        try {
                            ua.register(true, fname, lname, email, username, pswd);
                        } catch (AuthenticationException ae) {
                            JOptionPane.showMessageDialog(basePanel, ae.getMessage());
                            success = false;
                        }
                        break;
                    default:
                        JOptionPane.showMessageDialog(basePanel, "Ruolo non valido");
                        break;
                }

                if (success) {
                    JOptionPane.showMessageDialog(basePanel, "Register Success");
                    loginPage.setVisible(true);
                    frame.dispose();
                }
            }
        });
        roleComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (roleComboBox.getSelectedIndex() != 0) {
                    ayComboBox.setVisible(false);
                    ayLabel.setVisible(false);
                }
                if (roleComboBox.getSelectedIndex() == 0) {
                    ayComboBox.setVisible(true);
                    ayLabel.setVisible(true);
                }
            }
        });
        indietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginPage.setVisible(true);
                frame.dispose();
            }
        });
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
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        panel1.setBackground(new Color(-13553359));
        panel1.setForeground(new Color(-13553359));
        basePanel.add(panel1, BorderLayout.NORTH);
        final JLabel label1 = new JLabel();
        Font label1Font = this.$$$getFont$$$(null, Font.BOLD, 14, label1.getFont());
        if (label1Font != null) label1.setFont(label1Font);
        label1.setForeground(new Color(-2894893));
        label1.setText("Registra nuovo utente");
        panel1.add(label1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new FormLayout("fill:d:grow,left:4dlu:noGrow,fill:d:grow,left:4dlu:noGrow,fill:d:grow", "center:d:grow,top:4dlu:noGrow,center:d:noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:d:grow"));
        panel2.setBackground(new Color(-13553359));
        panel2.setForeground(new Color(-13553359));
        basePanel.add(panel2, BorderLayout.CENTER);
        final JLabel label2 = new JLabel();
        label2.setForeground(new Color(-2894893));
        label2.setText("Ruolo:");
        CellConstraints cc = new CellConstraints();
        panel2.add(label2, cc.xy(3, 3, CellConstraints.CENTER, CellConstraints.DEFAULT));
        roleComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel1 = new DefaultComboBoxModel();
        defaultComboBoxModel1.addElement("Studente");
        defaultComboBoxModel1.addElement("Docente");
        defaultComboBoxModel1.addElement("Coordinatore");
        roleComboBox.setModel(defaultComboBoxModel1);
        panel2.add(roleComboBox, cc.xy(3, 5));
        final JLabel label3 = new JLabel();
        label3.setBackground(new Color(-13553359));
        label3.setForeground(new Color(-2894893));
        label3.setText("Nome:");
        panel2.add(label3, cc.xy(3, 7, CellConstraints.CENTER, CellConstraints.DEFAULT));
        nomeTextField = new JTextField();
        panel2.add(nomeTextField, cc.xy(3, 9, CellConstraints.FILL, CellConstraints.DEFAULT));
        final JLabel label4 = new JLabel();
        label4.setForeground(new Color(-2894893));
        label4.setText("Cognome:");
        panel2.add(label4, cc.xy(3, 11, CellConstraints.CENTER, CellConstraints.DEFAULT));
        cognomeTextField = new JTextField();
        panel2.add(cognomeTextField, cc.xy(3, 13, CellConstraints.FILL, CellConstraints.DEFAULT));
        final JLabel label5 = new JLabel();
        label5.setForeground(new Color(-2894893));
        label5.setText("Username:");
        panel2.add(label5, cc.xy(3, 19, CellConstraints.CENTER, CellConstraints.DEFAULT));
        usernameTextField = new JTextField();
        panel2.add(usernameTextField, cc.xy(3, 21, CellConstraints.FILL, CellConstraints.DEFAULT));
        final JLabel label6 = new JLabel();
        label6.setForeground(new Color(-2894893));
        label6.setText("Password:");
        panel2.add(label6, cc.xy(3, 27, CellConstraints.CENTER, CellConstraints.DEFAULT));
        pswdField = new JPasswordField();
        panel2.add(pswdField, cc.xy(3, 29, CellConstraints.FILL, CellConstraints.DEFAULT));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panel2.add(spacer1, cc.xy(5, 12, CellConstraints.FILL, CellConstraints.DEFAULT));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        panel2.add(spacer2, cc.xy(1, 19, CellConstraints.FILL, CellConstraints.DEFAULT));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        panel2.add(spacer3, cc.xy(3, 1, CellConstraints.DEFAULT, CellConstraints.FILL));
        final com.intellij.uiDesigner.core.Spacer spacer4 = new com.intellij.uiDesigner.core.Spacer();
        panel2.add(spacer4, cc.xy(3, 31, CellConstraints.DEFAULT, CellConstraints.FILL));
        final JLabel label7 = new JLabel();
        label7.setForeground(new Color(-2894893));
        label7.setText("Email:");
        panel2.add(label7, cc.xy(3, 23, CellConstraints.CENTER, CellConstraints.DEFAULT));
        emailTextField = new JTextField();
        panel2.add(emailTextField, cc.xy(3, 25, CellConstraints.FILL, CellConstraints.DEFAULT));
        ayLabel = new JLabel();
        ayLabel.setForeground(new Color(-2894893));
        ayLabel.setText("Anno accademico:");
        panel2.add(ayLabel, cc.xy(3, 15, CellConstraints.CENTER, CellConstraints.DEFAULT));
        ayComboBox = new JComboBox();
        final DefaultComboBoxModel defaultComboBoxModel2 = new DefaultComboBoxModel();
        defaultComboBoxModel2.addElement("1");
        defaultComboBoxModel2.addElement("2");
        defaultComboBoxModel2.addElement("3");
        ayComboBox.setModel(defaultComboBoxModel2);
        panel2.add(ayComboBox, cc.xy(3, 17));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        panel3.setBackground(new Color(-13553359));
        panel3.setForeground(new Color(-13553359));
        basePanel.add(panel3, BorderLayout.SOUTH);
        confermaButton = new JButton();
        confermaButton.setBackground(new Color(-11579569));
        confermaButton.setForeground(new Color(-2894893));
        confermaButton.setText("Conferma");
        panel3.add(confermaButton, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        indietroButton = new JButton();
        indietroButton.setBackground(new Color(-11579569));
        indietroButton.setForeground(new Color(-2894893));
        indietroButton.setText("Indietro");
        panel3.add(indietroButton, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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



