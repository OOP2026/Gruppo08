package model;

import java.util.ArrayList;
import java.util.List;

public class Insegnamento {
	private int numeroCfu;
	// annoDiCorso != AnnoAccademico, e' come l'1 di fronte ad Analisi.
	private int annoDiCorso;
	private Materia materia;

	private List<Lezione> lezioni = new ArrayList<>();

	public Insegnamento(Materia materia, int numeroCfu, int annoDiCorso) {
		this.materia = materia;
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

	public Materia getMateria() {
		return materia;
	}
}
