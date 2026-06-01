package model;

import java.time.LocalTime;
import java.time.DayOfWeek;

public class Lezione {
	private int idLezione;
	private LocalTime oraInizio;
	private LocalTime oraFine;
	private AnnoAccademico annoAccademico;
	private DayOfWeek giornoSett;
	private Aula aula;
	private Insegnamento insegnamento;

	public Lezione(int idLezione, AnnoAccademico annoAccademico, DayOfWeek giornoSett, LocalTime oraInizio,
			LocalTime oraFine, Aula aula, Insegnamento insegnamento) {
		this.idLezione = idLezione;
		this.annoAccademico = annoAccademico;
		this.giornoSett = giornoSett;
		this.oraInizio = oraInizio;
		this.oraFine = oraFine;
		this.aula = aula;
		this.insegnamento = insegnamento;
	}

	public int getIdLezione() {
		return idLezione;
	}

	public AnnoAccademico getAnnoAccademico() {
		return annoAccademico;
	}

	public DayOfWeek getGiornoSett() {
		return giornoSett;
	}

	public LocalTime getOraInizio() {
		return oraInizio;
	}

	public LocalTime getOraFine() {
		return oraFine;
	}

	public String getIntervalloOrario() {
		return oraInizio.toString() + " - " + oraFine.toString();
	}

	public Aula getAula() {
		return aula;
	}

	public Insegnamento getInsegnamento() {
		return insegnamento;
	}

	public void setGiornoSett(DayOfWeek giornoSett) {
		this.giornoSett = giornoSett;
	}

	public void setOraInizio(LocalTime oraInizio) {
		this.oraInizio = oraInizio;
	}

	public void setOraFine(LocalTime oraFine) {
		this.oraFine = oraFine;
	}

	public void setAula(Aula aula) {
		this.aula = aula;
	}

}
