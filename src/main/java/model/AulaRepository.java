package model;

import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class AulaRepository {
	private List<Aula> aule = new ArrayList<>();

	public List<Aula> getAule() {
		return aule;
	}

	public Aula findAula(String nome) throws NoSuchElementException {
		for (Aula a : aule) {
			if (a.getNome().equals(nome))
				return a;
		}
		throw new NoSuchElementException();
	}

	public void addAula(Aula a) {
		aule.add(a);
	}
}
