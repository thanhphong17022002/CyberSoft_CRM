package crm_project_02.api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import crm_project_02.payload.BaseRespone;
import crm_project_02.service.JobService;

@WebServlet(name = "apiJobController" , urlPatterns = {"/api/job/delete"})
public class ApiJobController extends HttpServlet{

	JobService jobservice = new JobService();
	Gson gson = new Gson();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id_job = Integer.parseInt(req.getParameter("id"));
		boolean isSuccess = jobservice.deleteJob(id_job);
		BaseRespone respone = new BaseRespone();
		respone.setStatusCode(200);
		respone.setMessage(isSuccess ? "Delete successful" : "Delete fail");
		respone.setData(isSuccess);
		String json = gson.toJson(respone);
		//tra du lieu json
		PrintWriter out = resp.getWriter();
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		out.print(json);
		out.flush();
	}
}
