package model;

public class TestModel {

	public static void main(String[] args) {
		ServizioUtenti servizioUtenti = new ServizioUtenti();

		servizioUtenti.registerCoordinatore("Alessio", "Manna", "alessio", "test@mail.com", "verystrongpswd");

		Studente io;
		try {
			// TODO: BORKED
			io = servizioUtenti.studenteLogin("alessio", "verystrongpswd");
			System.out.println(io.puoModificareOrario());
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
