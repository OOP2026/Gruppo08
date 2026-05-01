package com.progetto.esamepoo;

import java.time.LocalDateTime;

public class Lezione {
	private LocalDateTime oraInizio = LocalDateTime.now();
	private LocalDateTime oraFine;

	private Aula aula;

	public Lezione(Aula aula) {
		this.aula = aula;
	}

	public LocalDateTime getOraInizio() {
		return oraInizio;
	}

	public LocalDateTime getOraFine() {
		return oraFine;
	}

	public void endLezione() {
		oraFine = LocalDateTime.now();
	}

	public Aula getAula() {
		return aula;
	}

}
