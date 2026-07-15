package controller;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import dao.ChangeOfDateReqDao;
import dao.CourseDao;
import dao.LectureDao;
import dao.UserDao;
import daoImplementation.ChangeOfDateReqPostgresDao;
import model.*;

public class TestController {
	public static void main(String[] args) {
		CourseService cs = new CourseService();
		LectureService ls = new LectureService();
		ClassroomService cls = new ClassroomService();
		ChangeOfDateReqService codreqs = new ChangeOfDateReqService();
		UserAuthentication uauth = new UserAuthentication();

		/*
		 * try {
		 * // uauth.register(true, "alessio", "manna", "mannaalessio@tutamail.com",
		 * "alem",
		 * // "verystrongpswd");
		 * uauth.register(true, "mario", "rossi", "rossimario@gmail.com", "marior",
		 * "verystrongpswd");
		 * } catch (Exception e) {
		 * System.out.println(e);
		 * }
		 */

		try {
			uauth.login("alem", "verystrongpswd");
			System.out.println("what");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		/*
		 * try {
		 * cs.makeCourse(UserDao.getInstance().getUserByLogin("marior").getUserId(),
		 * "Programmazione", 9, 2, true);
		 * } catch (Exception e) {
		 * System.out.println(e);
		 * }
		 * try {
		 * cls.makeClassroom("A2");
		 * } catch (Exception e) {
		 * System.out.println(e);
		 * }
		 * try {
		 * ls.makeLecture(CourseDao.getInstance().getByNameNYear("Programmazione",
		 * 2).getCourseId(), "A2",
		 * DayOfWeek.TUESDAY,
		 * LocalTime.of(12, 30), LocalTime.of(14, 30));
		 * } catch (Exception e) {
		 * System.out.println(e);
		 * }
		 * 
		 * try {
		 * // TODO: find lecture without id
		 * // TODO: asking teacher id should be taken from session
		 * codreqs.makeChangeOfDateReq(UserDao.getInstance().getUserByLogin("marior").
		 * getUserId(), 1, DayOfWeek.FRIDAY,
		 * LocalTime.of(14, 30), LocalTime.of(16, 30));
		 * } catch (Exception e) {
		 * System.out.println(e);
		 * }
		 */

		System.out.println(SessionManager.getInstance().getSession().getUserId());

		try {
			codreqs.changeStatusOfCODR(1, false);
		} catch (Exception e) {
			System.out.println(e);
		}

		System.out.println(".");
	}
}
