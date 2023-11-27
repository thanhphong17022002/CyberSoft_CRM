package crm_project_02.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name="demoGetSession", urlPatterns = {"/getsession"})
public class DemoGetSession extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();

		String name = (String) session.getAttribute("name");
		String gender = (String) session.getAttribute("gender");

		System.out.println("Test "+name);
		System.out.println(gender);
	}
}
