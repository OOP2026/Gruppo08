package dao;

import dao.dto.CourseDTO;

public interface CourseDao extends GenericDao<CourseDTO, Integer> {
	public CourseDTO getByNameNYear(String name, int academicYear);

	public void insertCourse(int teacherUid, String name, int cfu, int academicYear, boolean isActive);
}
