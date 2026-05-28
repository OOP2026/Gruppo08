package model;

import java.util.ArrayList;
import java.util.List;

// TODO: relazione con orarioLezione al posto di insegnamentiSeguiti

public class Studente extends Utente {
	private static int counter = 0;
	private int matricola;
	private List<Insegnamento> insegnamentiSeguiti = new ArrayList<>();

	public Studente(String nome, String cognome, String login, String email, String pswd) {
		super(nome, cognome, login, email, pswd);
		matricola = counter++;
	}

	public List<Insegnamento> insegnamentiSeguiti() {
		return insegnamentiSeguiti;
	}

	@Override
	public boolean puoRichiedereSpostamento() {
		return false;
	}

	@Override
	public boolean puoModificareOrario() {
		return false;
	}

	@Override
	public Integer getMatricola() {
		return matricola;
	}
}
