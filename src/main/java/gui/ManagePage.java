package gui;

import javax.swing.*;
import controller.*;
import controller.exception.DatabaseException;
import controller.exception.UnauthorizedException;

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
    private final ChangeOfDateReqService cs = new ChangeOfDateReqService();
    private int selectedId;

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

        rifiutaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rejectRequest();
            }
        });

        accettaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acceptRequest();
            }
        });

        reqComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (reqComboBox.getSelectedItem() == null) {
                    oldDateLabel.setText("");
                    newDateLabel.setText("");
                    return;
                }
                selectedId =  Integer.parseInt(reqComboBox.getSelectedItem().toString().split(" ")[0]);
                try {
                    oldDateLabel.setText("<html>Vecchio orario:<br>" + cs.getCODROldTimeAndDate(selectedId) + "</html>");
                    newDateLabel.setText("<html>Nuovo orario:<br>" + cs.getCODRNewTimeAndDate(selectedId) + "</html>");
                } catch (UnauthorizedException ue) {
                    JOptionPane.showMessageDialog(frame, ue.getMessage());
                }

            }
        });
    }

    private void populateRequests() {
        List<String> codrs;

        try {
            codrs = cs.getWaitingCODRInfo();
            reqComboBox.setModel(new DefaultComboBoxModel<>(codrs.toArray(new String[0])));
        } catch (UnauthorizedException ue) {
            JOptionPane.showMessageDialog(frame, ue.getMessage());
        } catch (NullPointerException _) {
            JOptionPane.showMessageDialog(frame, "Si e' verificato un errore nel recupero delle richieste.");
        }
    }

    public void acceptRequest() {
        if (reqComboBox.getSelectedItem() == null) return;
        try {
            cs.changeStatusOfCODR(selectedId, true);
            JOptionPane.showMessageDialog(frame, "Richiesta accettata correttamente");
            populateRequests();
            oldDateLabel.setText("");
            newDateLabel.setText("");
        } catch (UnauthorizedException ue) {
            JOptionPane.showMessageDialog(frame, ue.getMessage());
        } catch (DatabaseException de) {
            JOptionPane.showMessageDialog(frame, de.getMessage());
        }
    }

    public void rejectRequest() {
        if (reqComboBox.getSelectedItem() == null) return;
        try {
            cs.changeStatusOfCODR(selectedId, false);
            JOptionPane.showMessageDialog(frame, "Richiesta rifiutata correttamente");
            populateRequests();
            oldDateLabel.setText("");
            newDateLabel.setText("");
        } catch (UnauthorizedException ue) {
            JOptionPane.showMessageDialog(frame, ue.getMessage());
        } catch (DatabaseException de) {
            JOptionPane.showMessageDialog(frame, de.getMessage());
        }
    }
}
