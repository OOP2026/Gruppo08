package controller;

import java.util.ArrayList;
import java.util.List;
import model.*;

public class TestController {
	public static void main(String[] args) {
		UserAuthentication uauth = new UserAuthentication();
		LectureService lService = new LectureService();

		try {
			uauth.login("alem", "verystrongpswd");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		List<Lecture> lectures = new ArrayList<>();
		try {
			lectures = lService.getAllByTeacher(SessionManager.getInstance().getSession().getUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (Lecture l : lectures)
			System.out.println(l.getLectureId());

		System.out.println(".");
	}
}
