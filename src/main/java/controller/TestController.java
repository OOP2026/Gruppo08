package controller;

import java.util.List;

public class TestController {
	public static void main(String[] args) {
		UserService uauth = new UserService();
		CourseService cs = new CourseService();
		ClassroomService classs = new ClassroomService();

		try {
			uauth.login("alem", "verystrongpswd");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		List<String> info = classs.getAllClassroomInfo();
		System.out.println(info);
	}
}
