package crm_project_02.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import crm_project_02.entity.Project;
import crm_project_02.payload.BaseRespone;
import crm_project_02.service.ProjectService;


@WebServlet(name = "apiProjectController", urlPatterns = { "/api/groupwork", "/api/groupwork/delete" })
public class ApiProjectController extends HttpServlet {

	private ProjectService service = new ProjectService();
	private Gson gson = new Gson();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		List<Project> listProjec;
		if (path.equals("/api/groupwork")) {

			listProjec = service.getAllproject();

			BaseRespone repone = new BaseRespone();
			repone.setStatusCode(200);
			repone.setMessage("");
			repone.setData(listProjec);

			
			String dataJson = gson.toJson(repone);

			
			PrintWriter out = resp.getWriter();
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");
			out.print(dataJson);
			out.flush();
		} else if (path.equals("/api/groupwork/delete")) {
			int id = Integer.parseInt(req.getParameter("id"));
			boolean isSuccess = service.deleteProject(id);

			BaseRespone repone = new BaseRespone();
			repone.setStatusCode(200);
			repone.setMessage(isSuccess ? "Delete success" : "Delete fail");
			repone.setData(isSuccess);
			String dataJson = gson.toJson(repone);

			
			PrintWriter out = resp.getWriter();
			resp.setContentType("application/json");
			resp.setCharacterEncoding("UTF-8");

			out.print(dataJson);
		}

	}
}