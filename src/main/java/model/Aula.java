package model;

public class Aula {
	private char lettera;
	private int numero;
	private int capacita;

	// NOTE: Lezione contiene riferimento a una singola Aula, accessibile tramite
	// LezioneRepository

	public Aula(char lettera, int numero, int capacita) {
		this.lettera = lettera;
		this.numero = numero;
		this.capacita = capacita;
	}

	public char getLettera() {
		return lettera;
	}

	public int getNumero() {
		return numero;
	}

	public int getCapacita() {
		return capacita;
	}
}
