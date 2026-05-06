package model;

public class TestModel {

	public static void main(String[] args) {
		ServizioUtenti servizioUtenti = new ServizioUtenti();

		servizioUtenti.registerDocente("Alessio", "Manna", "alessio", "test@mail.com", "verystrongpswd");

		Docente io;
		try {
			// TODO: BORKED
			io = servizioUtenti.docenteLogin("alessio", "verystrongpswd");
			System.out.println(io.puoModificareOrario());
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
