package dao;

import implementazioneDao.entity.CourseEntity;

import java.util.List;

public interface CourseDao extends GenericDao<CourseEntity, Integer> {
	public CourseEntity getByNameNYear(String name, int academicYear);

	public void insertCourse(int teacherUid, String name, int cfu, int academicYear, boolean isActive);

	public List<CourseEntity> getAllActiveCourses();
}
