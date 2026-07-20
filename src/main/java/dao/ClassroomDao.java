package dao;

import java.util.List;
import java.util.NoSuchElementException;

import daoImplementation.ClassroomPostgresDao;

import java.sql.SQLException;
import java.util.ArrayList;

import model.Classroom;

public class ClassroomDao extends AbstractDao<Classroom, ClassroomPostgresDao, String> {
	private static ClassroomDao instance;

	private ClassroomDao() {
		super(new ClassroomPostgresDao());
	}

	public static ClassroomDao getInstance() {
		if (instance == null)
			instance = new ClassroomDao();
		return instance;
	}

	public void insertClassroom(String name) throws SQLException {
		Classroom c = sqldao.insertClassroom(name);
		inMem.add(c);
	}
}
