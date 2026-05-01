package com.progetto.esamepoo;

import java.util.List;

public class Aula {
	private int capacita;

	// NOTE: Lezione contiene riferimento a una singola Aula, accessibile tramite
	// LezioneRepository

	public Aula(int capacita) {
		this.capacita = capacita;
	}

	public int getCapacita() {
		return capacita;
	}
}
