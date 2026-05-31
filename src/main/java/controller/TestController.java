package controller;

import java.time.DayOfWeek;
import java.time.LocalTime;

import model.*;

/*
 * TODO:
 * - Metodi per far interfacciare l'utente alle proprie lezioni in base all'anno di corso;
 * - metodi make* senza parametro id se l'oggetto non e' presente nel db;
 *
 * NOTE:
 * - Le classi Repository andrebbero come in memory db (cache), da implementare nel DAO.
 * */

public class TestController {
	public static void main(String[] args) {
		Controller controller = new Controller();

		controller.registerStudente("Alessio", "Manna", "ale", "mannaalessio@tutamail.com", "verystrongpswd");
		controller.registerCoordinatore("Alessio2", "Manna", "ale2", "mannaalessio2@tutamail.com", "verystrongpswd");

		controller.coordinatoreLogin("ale2", "verystrongpswd");

		System.out.println(controller.isCoordinatore());

		controller.makeAnnoAccademico(2026);
		controller.makeAula("Aula A1");
		controller.makeMateria("Analisi");
		controller.makeInsegnamento(0, "Analisi", "ale2", 9, 1);
		controller.makeLezione(0, 2026, DayOfWeek.FRIDAY, "Aula A1", 0, LocalTime.now(), LocalTime.now());

		LezioneRepository lRepo = LezioneRepository.getInstance();
		Lezione l = lRepo.findById(0);

		System.out.println(l.getGiornoSett());

		controller.makeRichiestaSpostamento(0, 0, "ale2", DayOfWeek.MONDAY, LocalTime.NOON, LocalTime.MIDNIGHT);
		controller.approvaRichiestaSpostamento(0, true);

		System.out.println(l.getGiornoSett());
	}
}
