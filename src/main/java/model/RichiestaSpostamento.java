package model;

import java.time.LocalTime;
import java.time.DayOfWeek;

public class RichiestaSpostamento {
    private int idRichiesta;
    private Lezione lezioneDaSpostare;
    private Docente docente;
    private DayOfWeek nuovoGiorno;
    private LocalTime nuovaOraInizio;
    private LocalTime nuovaOraFine;

    public enum Stato {
        IN_ATTESA,
        APPROVATO,
        RIFIUTATO
    }

    private Stato stato;

    public RichiestaSpostamento(int idRichiesta, Lezione lezioneDaSpostare, Docente docente, DayOfWeek nuovoGiorno,
            LocalTime nuovaOraInizio, LocalTime nuovaOraFine) {
        this.idRichiesta = idRichiesta;
        this.lezioneDaSpostare = lezioneDaSpostare;
        this.docente = docente;
        this.nuovoGiorno = nuovoGiorno;
        this.nuovaOraInizio = nuovaOraInizio;
        this.nuovaOraFine = nuovaOraFine;
        this.stato = Stato.IN_ATTESA;
    }

    public int getIdRichiesta() {
        return idRichiesta;
    }

    public void setStato(boolean approvato) {
        this.stato = approvato ? Stato.APPROVATO : Stato.RIFIUTATO;
    }

    public Stato getStato() {
        return this.stato;
    }

    public Docente getDocente() {
        return this.docente;
    }

    public Lezione getLezioneDaSpostare() {
        return lezioneDaSpostare;
    }

    public DayOfWeek getNuovoGiorno() {
        return nuovoGiorno;
    }

    public LocalTime getNuovaOraInizio() {
        return nuovaOraInizio;
    }

    public LocalTime getNuovaOraFine() {
        return nuovaOraFine;
    }
}
