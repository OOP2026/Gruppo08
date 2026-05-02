package model;

public class ServizioLezioni {
	private LezioneRepository lezioneRepo = new LezioneRepository();

	/*
	 * TODO:
	 * - richiediSpostamento: interagisce con Docente e OrarioLezioni e crea uno
	 * spostamento temporaneo per una determinata settimana della lezione
	 * - confermaSpostamento: prende un Coordinatore e cambia lo StatoSpostamento
	 * - creaLezione: organizza una lezione in un determinato giorno della settimana
	 * (si interfaccia a LezioneRepository).
	 * - getOrario: deve essere chiamabile da tutti gli utenti e restituisce la hash
	 * table.
	 * - creaOrarioLezione: metodo usato da Coordinatore per creare eventi
	 * settimanali di istanziamento di lezioni.
	 */
}
