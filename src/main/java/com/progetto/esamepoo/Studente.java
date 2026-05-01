package com.progetto.esamepoo;

import java.util.ArrayList;
import java.util.List;

public class Studente extends Utente {
	private static int counter = 0;
	private int matricola;
	private List<Insegnamento> insegnamentiSeguiti = new ArrayList<>();

	Studente(String nome, String cognome, String login, String email, String pswd) {
		super(nome, cognome, login, email, pswd);
		matricola = counter++;
	}

	public int getMatricola() {
		return matricola;
	}

	public List<Insegnamento> insegnamentiSeguiti() {
		return insegnamentiSeguiti;
	}
}
