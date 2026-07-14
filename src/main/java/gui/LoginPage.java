package gui;

import controller.*;

import javax.security.sasl.AuthenticationException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPage {
    private static JFrame frame;
    private JPanel basePanel;
    private JComboBox<String> roleComboBox;
    private JTextField loginTextField;
    private JPasswordField pswdField;
    private JButton loginButton;
    private JButton regButton;
    private final UserAuthentication controller = new UserAuthentication();

    public static void main(String[] args) {
        frame = new JFrame("LoginPage");
        frame.setContentPane(new LoginPage().basePanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public LoginPage() {
        loginButton.addActionListener(e -> {
            String role = (String) roleComboBox.getSelectedItem();
            String login = loginTextField.getText();
            String pswd = String.valueOf(pswdField.getPassword());
            boolean success = true;

            switch (role) {
                case "Studente":
                    try {
                        controller.login(login, pswd);
                    } catch (AuthenticationException ae) {
                        JOptionPane.showMessageDialog(basePanel, ae.getMessage());
                        success = false;
                    }
                    break;
                case "Docente":
                    try {
                        controller.login(login, pswd);
                    } catch (AuthenticationException ae) {
                        JOptionPane.showMessageDialog(basePanel, ae.getMessage());
                        success = false;
                    }
                    break;
                case "Coordinatore":
                    try {
                        controller.login(login, pswd);
                    } catch (AuthenticationException ae) {
                        JOptionPane.showMessageDialog(basePanel, ae.getMessage());
                        success = false;
                    }
                    break;
                default:
                    JOptionPane.showMessageDialog(frame, "Invalid role");
                    break;
            }
            if (success) {
                JOptionPane.showMessageDialog(basePanel, "Login Success");
                new HomePage();
                frame.dispose();
            }
        });

        regButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegPage(frame);
                frame.setVisible(false);
            }
        });
    }
}

