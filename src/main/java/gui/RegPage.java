package gui;

import javax.swing.*;
import controller.*;

public class RegPage extends JFrame {
    private static JFrame frame;
    private JPanel basePanel;
    private JComboBox comboBox1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JPasswordField passwordField1;
    private JButton confermaButton;

    public RegPage(JFrame loginPage, Controller controller) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(basePanel);
        frame.pack();
        frame.setVisible(true);
    }
}
