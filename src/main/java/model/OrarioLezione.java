package model;

/*
 * NOTE:
 * OrarioLezione come dovrebbe segnare l'orario 24h della lezione? 
 * Lezione contiene un DataOraInizio e DataOraFine ma dovrebbero essere due concetti separati.
 * */

public class OrarioLezione {
	private AnnoAccademico anno;
	private String giornoSett;
	private Insegnamento insegnamento;

	public OrarioLezione(AnnoAccademico anno, String giornoSett, Insegnamento insegnamento) {
		this.anno = anno;
		this.giornoSett = giornoSett;
		this.insegnamento = insegnamento;
	}

	public Insegnamento getInsegnamento() {
		return insegnamento;
	}

	public AnnoAccademico getAnno() {
		return anno;
	}

	public String getGiornoSett() {
		return giornoSett;
	}

}
