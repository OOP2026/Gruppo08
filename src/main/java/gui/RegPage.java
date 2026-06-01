package gui;

import javax.swing.*;
import controller.*;

import java.awt.*;
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

    public RegPage(JFrame loginPage, Controller controller) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(basePanel);
        frame.pack();
        frame.setVisible(true);

        confermaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String role = (String) roleComboBox.getSelectedItem();
                String nome = nomeTextField.getText();
                String cognome = cognomeTextField.getText();
                String username = usernameTextField.getText();
                String email = emailTextField.getText();
                String pswd = new String(pswdField.getPassword());
                boolean success = true;

                switch (role) {
                    case "Studente":
                        try {
                            controller.registerStudente(nome, cognome, username, email, pswd);
                        } catch (SecurityException se) {
                            JOptionPane.showMessageDialog(basePanel, se.getMessage());
                            success = false;
                        }
                        break;
                    case "Docente":
                        try {
                            controller.registerDocente(nome, cognome, username, email, pswd);
                        } catch (SecurityException se) {
                            JOptionPane.showMessageDialog(basePanel, se.getMessage());
                            success = false;
                        }
                        break;
                    case "Coordinatore":
                        try {
                            controller.registerCoordinatore(nome, cognome, username, email, pswd);
                        } catch (SecurityException se) {
                            JOptionPane.showMessageDialog(basePanel, se.getMessage());
                            success = false;
                        }
                        break;
                    default:
                        JOptionPane.showMessageDialog(basePanel,"Ruolo non valido");
                        break;
                }
                if (success) {
                    JOptionPane.showMessageDialog(basePanel, "Register Success");
                    loginPage.setVisible(true);
                    frame.dispose();
                }
            }
        });
    }

}
