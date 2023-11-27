package crm_project_02.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_project_02.entity.Project;
import crm_project_02.service.ProjectService;

@WebServlet(name = "projectController", urlPatterns = { "/groupwork-add", "/groupwork","/groupwork-update" })
public class ProjectController extends HttpServlet {

	private ProjectService projectservice = new ProjectService();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		List<Project> listP = new ArrayList<>();
		if (path.equals("/groupwork-add")) {

			req.getRequestDispatcher("groupwork-add.jsp").forward(req, resp);
		} else if (path.equals("/groupwork")) {
			try {
				listP = projectservice.getAllproject();
				req.setAttribute("listP", listP);

				req.getRequestDispatcher("groupwork.jsp").forward(req, resp);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}else if (path.equals("/groupwork-update")) {
			int id_project = Integer.parseInt(req.getParameter("id_project"));
			Project project = projectservice.getProjectById(id_project);
			req.setAttribute("project", project);
			req.getRequestDispatcher("groupwork-update.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");

		String path = req.getServletPath();

		String name = req.getParameter("name");
		String startDateStr = req.getParameter("startDate");
		String endDateStr = req.getParameter("endDate");
		boolean isSuccess = false;

		Date startD = null;
		Date endD = null;
		if (path.equals("/groupwork-add")) {

			if (name.isEmpty()) {
				req.setAttribute("errorName", "Please input name project");
			}

			if (startDateStr.isEmpty()) {
				req.setAttribute("errorDateS", "Wrong format date");
			} else {
				startD = Date.valueOf(startDateStr);
			}

			if (endDateStr.isEmpty()) {
				req.setAttribute("errorDateE", "Wrong format date");
			} else {
				endD = Date.valueOf(endDateStr);
			}

			if (name.isEmpty() || startDateStr.isEmpty() || endDateStr.isEmpty()) {
				req.setAttribute("error", "Please input again");
			} else if (startD.after(endD)) {
				req.setAttribute("errSetDateTime", "End date must after start date !");
			} else {
				isSuccess = projectservice.addProject(name, startD, endD);
			}
			req.setAttribute("isSuccess", isSuccess);
			req.getRequestDispatcher("groupwork-add.jsp").forward(req, resp);
		} else if (path.equals("/groupwork-update")) {

			if (name.isEmpty()) {
				req.setAttribute("errorName", "Please input name project");
			}

			if (startDateStr.isEmpty()) {
				req.setAttribute("errorDateS", "Wrong format date");
			} else {
				startD = Date.valueOf(startDateStr);
			}

			if (endDateStr.isEmpty()) {
				req.setAttribute("errorDateE", "Wrong format date");
			} else {
				endD = Date.valueOf(endDateStr);
			}

			if (name.isEmpty() || startDateStr.isEmpty() || endDateStr.isEmpty()) {
				req.setAttribute("error", "Please input again");
			} else if (startD.after(endD)) {
				req.setAttribute("errSetDateTime", "End date must after start date !");
			} else {
				int id_project = Integer.parseInt(req.getParameter("id_project"));
				isSuccess = projectservice.updateProject(name, startD, endD, id_project);
				Project project = projectservice.getProjectById(id_project);
				req.setAttribute("project", project);

			}

			req.setAttribute("isSuccess", isSuccess);
			req.getRequestDispatcher("groupwork-update.jsp").forward(req, resp);
		}

		/*
		 * String query =
		 * "INSERT INTO Project (name, startDate, endDate) VALUES (?, ?, ?)"; Connection
		 * conn = null;
		 *
		 * try { conn = MysqlConfig.getConnection(); PreparedStatement stm =
		 * conn.prepareStatement(query); stm.setString(1, name); stm.setString(2,
		 * startDateStr); stm.setString(3, endDateStr);
		 *
		 * int countRow = stm.executeUpdate(); if (countRow > 0) { isSuccess = true;
		 * System.out.println("Add successful"); } else {
		 * System.out.println("Add failed"); }
		 *
		 * } catch (SQLException e) { e.printStackTrace(); } finally { if (conn != null)
		 * { try { conn.close(); } catch (SQLException e) { e.printStackTrace(); } } }
		 *
		 * req.setAttribute("isSuccess", isSuccess);
		 * req.getRequestDispatcher("groupwork-add.jsp").forward(req, resp);
		 */
	}

	// ...

}
