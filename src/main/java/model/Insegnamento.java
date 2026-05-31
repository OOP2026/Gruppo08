package model;

public class Insegnamento {
	private int idInsegnamento;
	private int numeroCfu;
	private int annoDiCorso;
	private Materia materia;
	private Docente docente;

	public Insegnamento(int idInsegnamento, Materia materia, Docente docente, int numeroCfu, int annoDiCorso) {
		this.idInsegnamento = idInsegnamento;
		this.materia = materia;
		this.docente = docente;
		this.numeroCfu = numeroCfu;
		this.annoDiCorso = annoDiCorso;
	}

	public int getIdInsegnamento() {
		return idInsegnamento;
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

	public Docente getDocente() {
		return docente;
	}

}
