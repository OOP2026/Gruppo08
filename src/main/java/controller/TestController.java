package controller;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import dao.CourseDao;
import dao.LectureDao;
import dao.UserDao;
import model.*;

public class TestController {
	public static void main(String[] args) {
		CourseService cs = new CourseService();
		LectureService ls = new LectureService();
		ClassroomService cls = new ClassroomService();
		ChangeOfDateReqService codreqs = new ChangeOfDateReqService();
		UserAuthentication uauth = new UserAuthentication();

		try {
			uauth.register(true, "alessio", "manna", "mannaalessio@tutamail.com", "alem", "verystrongpswd");
		} catch (Exception e) {
			System.out.println(e);
		}

		try {
			uauth.login("alem", "verystrongpswd");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		try {
			cs.makeCourse(UserDao.getInstance().getUserByLogin("alem").getUserId(), "Analisi", 12, 2, true);
		} catch (Exception e) {
			System.out.println(e);
		}
		try {
			cls.makeClassroom("A1");
		} catch (Exception e) {
			System.out.println(e);
		}
		try {
			ls.makeLecture(CourseDao.getInstance().getByNameNYear("Analisi", 2).getCourseId(), "A1",
					DayOfWeek.TUESDAY,
					LocalTime.of(12, 30), LocalTime.of(14, 30));
		} catch (Exception e) {
			System.out.println(e);
		}

		try {
			// TODO: find lecture without id
			codreqs.makeChangeOfDateReq(UserDao.getInstance().getUserByLogin("alem").getUserId(), 1, DayOfWeek.FRIDAY,
					LocalTime.of(12, 30), LocalTime.of(14, 30));
		} catch (Exception e) {
			System.out.println(e);
		}
		List<Lecture> buff = null;
		try {
			buff = LectureDao.getInstance().getAllByAcademicYear(1);
		} catch (Exception e) {
			System.out.println(e);
		}

		if (buff == null)
			return;

		for (Lecture l : buff) {
			System.out.println(l.getCourse() + l.getDayofweek().toString());
		}
		System.out.println(".");

	}
}
