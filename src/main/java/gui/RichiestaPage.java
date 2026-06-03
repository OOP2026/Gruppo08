package gui;

import controller.Controller;
import javax.swing.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RichiestaPage {
    private static JFrame frame;
    private JPanel basePanel;
    private JComboBox lezioneComboBox;
    private JComboBox giornoComboBox;
    private JComboBox orarioComboBox;
    private JButton confermaButton;
    private Controller controller;

    public RichiestaPage(JFrame callerFrame, Controller controller) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(basePanel);
        frame.pack();
        frame.setVisible(true);
        this.controller = controller;
        populateLezioni();
    }

    private void populateLezioni()
    {
        ArrayList<String> lezioni = new ArrayList<>();
        List<Integer> idLezioni = controller.getIdLezioniDocente();

        for(int id : idLezioni)
        {
            lezioni.add(controller.getNomeMateriaLezione(id) + controller.getGiornoSettLezione(id) +
                    controller.getIntervalloOrarioLezione(id));
        }

        lezioneComboBox.setModel(new DefaultComboBoxModel(lezioni.toArray()));
    }

    private void makeRichiesta(String lezione, DayOfWeek giorno, LocalTime orario)
    {

    }
}


