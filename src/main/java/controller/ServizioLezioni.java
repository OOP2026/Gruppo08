package controller;

import java.time.LocalDateTime;

import model.*;

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

	// NOTE: ServizioLezioni dipende strettamente da ServizioUniversita, WIP
	public Lezione makeLezione(Aula aula, Insegnamento insegnamento, LocalDateTime oraInizio, LocalDateTime oraFine) {
		Lezione l = new Lezione(aula, insegnamento, oraInizio, oraFine);
		lezioneRepo.addLezione(l);
		return l;
	}

}
