package crm_project_02.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import crm_project_02.entity.Users;
import crm_project_02.payload.BaseRespone;
import crm_project_02.service.UserService;

@WebServlet (name="apiUserController", urlPatterns = {"/api/user","/api/user/delete"})
public class ApiUserController extends HttpServlet{


	//payload respone : chua cac class lien quan den format jso tra ra cho client
	// request : chua cac class lien quan toi format json request (tham so) client truyen len server
	private UserService userservice = new UserService();
	private Gson gson = new Gson();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();
		if(path.equals("/api/user")) {
			
				List<Users> listU = userservice.getUsers();

				BaseRespone respone = new BaseRespone();
				respone.setStatusCode(200);
				respone.setMessage("");
				respone.setData(listU);
				String json = gson.toJson(respone);
				String dataJson = gson.toJson(listU);

				PrintWriter out = resp.getWriter();
				resp.setContentType("application/json");
				resp.setCharacterEncoding("UTF-8");
				out.print(dataJson);
				out.flush();
			
		}else if(path.equals("/api/user/delete")) {
			int id =Integer.parseInt(req.getParameter("id")) ;
			boolean isSuccess = userservice.deleteUserById(id);

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



}
