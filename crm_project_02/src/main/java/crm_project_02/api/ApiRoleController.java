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
import crm_project_02.service.RoleService;


@WebServlet(name="apiRoleController",urlPatterns = {"/api/role/delete"})
public class ApiRoleController extends HttpServlet{

	private Gson gson = new Gson();
	private RoleService roleservice = new RoleService();
	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int id =Integer.parseInt(req.getParameter("id")) ;
		boolean isSuccess = roleservice.deleteUserById(id);

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
