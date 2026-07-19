package gui;

import javax.swing.*;
import controller.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ManagePage {
    private JFrame frame;
    private JPanel basePanel;
    private JButton accettaButton;
    private JButton rifiutaButton;
    private JComboBox<String> reqComboBox;
    private JLabel oldDateLabel;
    private JLabel newDateLabel;
    private JButton indietroButton;
    private ChangeOfDateReqService cs = new ChangeOfDateReqService();

    public ManagePage(JFrame callerFrame) {
        frame = new JFrame("Manage");
        frame.setContentPane(basePanel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        populateRequests();

        indietroButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                callerFrame.setVisible(true);
                frame.dispose();
            }
        });
    }

    private void populateRequests() {
        List<String> codrs;

        try {
            codrs = cs.getCODRInfo();
            reqComboBox.setModel(new DefaultComboBoxModel<>(codrs.toArray(new String[0])));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, e.getMessage());
        }
    }
}
