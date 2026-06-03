package controller;

import java.util.NoSuchElementException;

import dao.*;
import model.*;

public class ServizioUniversita {
	private MateriaRepository materiaRepo = MateriaRepository.getInstance();
	private InsegnamentoRepository insegnamentoRepo = InsegnamentoRepository.getInstance();
	private AnnoAccademicoRepository annoAccademicoRepo = AnnoAccademicoRepository.getInstance();
	private AulaRepository aulaRepo = AulaRepository.getInstance();
	private DocenteRepository docRepo = DocenteRepository.getInstance();

	public Materia makeMateria(String nome) {
		Materia m = new Materia(nome);
		materiaRepo.addMateria(m);
		return m;
	}

	public Insegnamento makeInsegnamento(int idInsegnamento, String nomeMateria, String loginDocente, int numeroCfu,
			int annoDiCorso) throws NoSuchElementException {

		Materia materia = materiaRepo.findMateria(nomeMateria);
		Docente docente = docRepo.findByLogin(loginDocente);

		Insegnamento i = new Insegnamento(idInsegnamento, materia, docente, numeroCfu, annoDiCorso);
		insegnamentoRepo.addInsegnamento(i);
		return i;
	}

	public AnnoAccademico makeAnnoAccademico(int anno) {
		AnnoAccademico a = new AnnoAccademico(anno);
		annoAccademicoRepo.addAnnoAccademico(a);
		return a;
	}

	public Aula makeAula(String nome) {
		Aula a = new Aula(nome);
		aulaRepo.addAula(a);
		return a;
	}

}
