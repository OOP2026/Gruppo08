package controller;

import java.util.NoSuchElementException;

import model.*;

public class ServizioUniversita {
	private MateriaRepository materiaRepo = new MateriaRepository();
	private InsegnamentoRepository insegnamentoRepo = new InsegnamentoRepository();
	private AnnoAccademicoRepository annoAccademicoRepo = new AnnoAccademicoRepository();
	private AulaRepository aulaRepo = new AulaRepository();
	private DocenteRepository docRepo = new DocenteRepository();

	public Materia makeMateria(String nome) {
		Materia m = new Materia(nome);
		materiaRepo.addMateria(m);
		return m;
	}

	public Insegnamento makeInsegnamento(String nomeMateria, String loginDocente, int numeroCfu, int annoDiCorso) {
		Materia materia;
		Docente docente;
		try {
			materia = materiaRepo.findMateria(nomeMateria);
			docente = docRepo.findByLogin(loginDocente);
		} catch (NoSuchElementException e) {
			throw e;
		}

		Insegnamento i = new Insegnamento(materia, docente, numeroCfu, annoDiCorso);
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
