package model;

public class ServizioLezioni {
	private LezioneRepository lezioneRepo = new LezioneRepository();
	private AulaRepository aulaRepo = new AulaRepository();

	/*
	 * TODO:
	 * - creaLezione: organizza una lezione in un determinato giorno della settimana
	 * (si interfaccia a LezioneRepository).
	 * - getOrario: deve essere chiamabile da tutti gli utenti e restituisce la hash
	 * table.
	 * - creaOrarioLezione: metodo usato da Coordinatore per creare eventi
	 * settimanali di istanziamento di lezioni.
	 * - richiediSpostamento: interagisce con Docente e OrarioLezioni e crea uno
	 * spostamento temporaneo per una determinata settimana della lezione
	 * - confermaSpostamento: prende un Coordinatore e cambia lo StatoSpostamento
	 */

	// WIP: public void creaLezione(LocalDateTime oraInizio, LocalDateTime oraFine, char lettera, int numero,)

	// NOTE: ServizioLezioni dipende strettamente da ServizioUniversita, WIP

}
