package model;

import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class AulaRepository {
	private List<Aula> aule = new ArrayList<>();

	public List<Aula> getAule() {
		return aule;
	}

	public Aula searchAula(char lettera, int numero) throws NoSuchElementException {
		for (Aula aula : aule)
		{
			if (lettera == aula.getLettera() && numero == aula.getNumero())
				{
					return aula;
				}
		}

		throw new NoSuchElementException("l'aula " + lettera + numero + " non esiste");
	}

	public void addAula(Aula a) {
		aule.add(a);
	}
}
