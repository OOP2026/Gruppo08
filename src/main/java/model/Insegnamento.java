package model;

public class Insegnamento {
	private int numeroCfu;
	// annoDiCorso != AnnoAccademico, e' come l'1 di fronte ad Analisi.
	private int annoDiCorso;
	private Materia materia;

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

	public Materia getMateria() {
		return materia;
	}
}
