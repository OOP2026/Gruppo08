package model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class InsegnamentoRepository {
	private InsegnamentoRepository() {
	}

	private static InsegnamentoRepository instance;

	public static InsegnamentoRepository getInstance() {
		if (instance == null)
			instance = new InsegnamentoRepository();

		return instance;
	}

	private List<Insegnamento> insegnamenti = new ArrayList<>();

	public List<Insegnamento> getInsegnamenti() {
		return insegnamenti;
	}

	public Insegnamento findInsegnamento(int idInsegnamento) throws NoSuchElementException {
		for (Insegnamento i : insegnamenti) {
			if (i.getIdInsegnamento() == idInsegnamento)
				return i;
		}
		throw new NoSuchElementException();
	}

	public void addInsegnamento(Insegnamento i) {
		insegnamenti.add(i);
	}

}
