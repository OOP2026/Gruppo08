package model;

import java.time.LocalDateTime;

public class Lezione {
	private LocalDateTime oraInizio;
	private LocalDateTime oraFine;

	private Aula aula;
	private Insegnamento insegnamento;

	public Lezione(Aula aula, Insegnamento insegnamento, LocalDateTime oraInizio, LocalDateTime oraFine) {
		this.aula = aula;
		this.insegnamento = insegnamento;
		this.oraInizio = oraInizio;
		this.oraFine = oraFine;
	}

	public LocalDateTime getOraInizio() {
		return oraInizio;
	}

	public LocalDateTime getOraFine() {
		return oraFine;
	}

	public Aula getAula() {
		return aula;
	}

	public Insegnamento getInsegnamento() {
		return insegnamento;
	}

	public void setOraInizio(LocalDateTime oraInizio) {
		this.oraInizio = oraInizio;
	}

	public void setOraFine(LocalDateTime oraFine) {
		this.oraFine = oraFine;
	}

}
