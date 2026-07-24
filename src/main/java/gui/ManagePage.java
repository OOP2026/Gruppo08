package gui;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import controller.*;
import controller.exception.DatabaseException;
import controller.exception.UnauthorizedException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Locale;

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
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

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
                selectedId = Integer.parseInt(reqComboBox.getSelectedItem().toString().split(" ")[0]);
                try {
                    oldDateLabel.setText("<html>Vecchio orario:<br>" + cs.getCODROldTimeAndDate(selectedId) + "</html>");
                    newDateLabel.setText("<html>Nuovo orario:<br>" + cs.getCODRNewTimeAndDate(selectedId) + "</html>");
                } catch (UnauthorizedException ue) {
                    JOptionPane.showMessageDialog(frame, ue.getMessage());
                }

            }
        });

        populateRequests();
    }

    private void populateRequests() {
        List<String> codrs;

        try {
            codrs = cs.getWaitingCODRInfo();
            reqComboBox.setModel(new DefaultComboBoxModel<>(codrs.toArray(new String[0])));
        } catch (UnauthorizedException ue) {
            JOptionPane.showMessageDialog(frame, ue.getMessage());
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(frame, "Si e' verificato un errore nel recupero delle richieste.");
        } catch (DatabaseException e) {
            JOptionPane.showMessageDialog(frame, "impossibile recuperare le richieste di spostamento");
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
