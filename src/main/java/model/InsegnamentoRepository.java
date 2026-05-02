package model;

import java.util.ArrayList;
import java.util.List;

public class InsegnamentoRepository {
	private List<Insegnamento> insegnamenti = new ArrayList<>();

	public List<Insegnamento> getInsegnamenti() {
		return insegnamenti;
	}

	public void addInsegnamento(Insegnamento i) {
		insegnamenti.add(i);
	}

}
