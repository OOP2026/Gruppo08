package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import model.Materia;

public class MateriaRepository {
	private MateriaRepository() {
	}

	private static MateriaRepository instance;

	public static MateriaRepository getInstance() {
		if (instance == null)
			instance = new MateriaRepository();

		return instance;
	}

	private List<Materia> materie = new ArrayList<>();

	public void addMateria(Materia m) {
		materie.add(m);
	}

	public Materia findMateria(String nome) throws NoSuchElementException {
		for (Materia m : materie) {
			if (m.getNome().equals(nome))
				return m;
		}
		throw new NoSuchElementException();
	}

	public List<Materia> getMaterie() {
		return materie;
	}

}
