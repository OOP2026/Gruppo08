package model;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;

public class RichiestaSpostamentoRepository {
	private RichiestaSpostamentoRepository() {
	}

	private static RichiestaSpostamentoRepository instance;

	public static RichiestaSpostamentoRepository getInstance() {
		if (instance == null)
			instance = new RichiestaSpostamentoRepository();

		return instance;
	}

	List<RichiestaSpostamento> richieste = new ArrayList<>();

	public void addRichiesta(RichiestaSpostamento r) {
		richieste.add(r);
	}

	public RichiestaSpostamento findById(int idRichiesta) throws NoSuchElementException {
		for (RichiestaSpostamento r : richieste) {
			if (r.getIdRichiesta() == idRichiesta)
				return r;
		}
		throw new NoSuchElementException();
	}
}
