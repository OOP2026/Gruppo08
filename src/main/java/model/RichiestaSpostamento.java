package model;

import java.time.LocalDateTime;

public class RichiestaSpostamento {
    private Lezione lezione;
    private LocalDateTime nuovoOrario;
    private Docente docente;
    public enum Stato
    {
        IN_ATTESA,
        APPROVATO,
        RIFIUTATO
    }
    private Stato stato;


    public RichiestaSpostamento(Lezione lezione, LocalDateTime nuovoOrario, Docente docente)
    {
        this.lezione = lezione;
        this.nuovoOrario = nuovoOrario;
        this.docente = docente;
        this.stato = Stato.IN_ATTESA;
    }

    public void setStato (Stato nuovoStato)
    {
        this.stato = nuovoStato;
    }

    public Stato getStato()
    {
        return this.stato;
    }

    public LocalDateTime getNuovoOrario()
    {
        return this.nuovoOrario;
    }

    public void setNuovoOrario(LocalDateTime nuovoOrario)
    {
        this.nuovoOrario = nuovoOrario;
    }
    
    public Docente getDocente()
    {
        return this.docente;
    }

    public void setDocente(Docente docente)
    {
        this.docente = docente;
    }

    public Lezione getLezione()
    {
        return this.lezione;
    }

    public void setLezione(Lezione lezione)
    {
        this.lezione = lezione;
    }
}