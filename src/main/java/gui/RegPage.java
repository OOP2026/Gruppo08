package gui;

import javax.security.sasl.AuthenticationException;
import javax.swing.*;
import controller.UserService;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

}



