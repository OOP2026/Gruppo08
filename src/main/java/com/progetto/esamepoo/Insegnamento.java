package com.progetto.esamepoo;

import java.util.ArrayList;
import java.util.List;

public class Insegnamento {
	private int numeroCfu;
	private int annoDiCorso;

	private List<Lezione> lezioni = new ArrayList<>();

	public Insegnamento(int numeroCfu, int annoDiCorso) {
		this.numeroCfu = numeroCfu;
		this.annoDiCorso = annoDiCorso;
	}

	public int getNumeroCfu() {
		return numeroCfu;
	}

	public int getAnnoDiCorso() {
		return annoDiCorso;
	}

	public List<Lezione> getLezioni() {
		return lezioni;
	}

	public void addLezione(Lezione lezione) {
		lezioni.add(lezione);
	}
}
