package crm_project_02.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_project_02.entity.Role;
import crm_project_02.entity.Users;
import crm_project_02.service.RoleService;
import crm_project_02.service.UserService;

@WebServlet(name = "userController", urlPatterns = {"/user-add", "/user","/user-update"})
public class UserController extends HttpServlet{


	private UserService userService = new UserService();
	private RoleService roleservice = new RoleService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		 String path = req.getServletPath();

		    if (path.equals("/user-add")) {
		        List<Role> list = new ArrayList<>();
		        try {
		            list = userService.getRole();
		        } catch (SQLException e) {
		            System.out.println("fail" + e.getLocalizedMessage());
		        }
		        req.setAttribute("listRole", list);
		        req.getRequestDispatcher("user-add.jsp").forward(req, resp);
		    } else if (path.equals("/user")) {
		        List<Users> listU = new ArrayList<>();
		        listU = userService.getUsers();
		        req.setAttribute("listU", listU);
		        req.getRequestDispatcher("user-table.jsp").forward(req, resp);
		    } else if (path.equals("/user-update")) {
		    	int id = Integer.parseInt(req.getParameter("id"));
				List<Role> listR = new ArrayList<>();
				try {
					listR = userService.getRole();
				} catch (SQLException e) {
					System.out.println("Lỗi getAllRole " + e.getLocalizedMessage());
				}
				Users user = userService.getUserById(id);
				req.setAttribute("user", user);
				req.setAttribute("listR", listR);
				req.getRequestDispatcher("user-update.jsp").forward(req, resp);
		    }

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		resp.setContentType("UTF-8");
		String path = req.getServletPath();
		String fullname = req.getParameter("fullname");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String phone = req.getParameter("phone");
		int id_role = Integer.parseInt(req.getParameter("role"));
		
		if(path.equals("/user-add")) {
			
			boolean isSuccess = userService.addUser(fullname, email, password, phone, id_role);
			
			List<Role> list = new ArrayList<>();
			try {
				 list = userService.getRole();
			} catch (SQLException e) {


				System.out.println("fail" +e.getLocalizedMessage());
			}
			req.setAttribute("listRole", list);
			
			 req.getRequestDispatcher("user-add.jsp").forward(req, resp);
		}else if(path.equals("/user-update")) {
		
		    
			int id_user = Integer.parseInt(req.getParameter("id_user"));
			boolean isSuccess = userService.updateUser(fullname, password, email, phone, id_role, id_user);
			List<Role> list = new ArrayList<>();
			try {
				list = userService.getRole();
			} catch (SQLException e) {
				System.out.println("Lỗi getAllRole " + e.getLocalizedMessage());
			}
			if (isSuccess) {
				req.setAttribute("isSuccess", "Update success");
			}
			Users user = userService.getUserById(id_user);
			req.setAttribute("user", user);
			req.setAttribute("listRole", list);
			req.getRequestDispatcher("user-update.jsp").forward(req, resp);
		}

		
		

		

		/*
		 * String query =
		 * "INSERT INTO Users(fullname,email,pwd,phone,id_role) VALUES('"+fullname+"','"
		 * +email+"','"+password+"','"+phone+"','"+id_role+"')";
		 *
		 * Connection conn = MysqlConfig.getConnection();
		 *
		 * try { PreparedStatement stm = conn.prepareStatement(query);
		 *
		 *
		 * int countRow = stm.executeUpdate(); if(countRow >0) {
		 * System.out.println("Add successful"); }else { System.out.println("Add fail");
		 * } }catch(Exception e) { System.out.println("Error" +e.getLocalizedMessage());
		 * }finally { try { conn.close(); } catch (SQLException e) {
		 * System.out.println("fail close connection "+ e.getLocalizedMessage()); } }
		 *
		 * List<Role> list = new ArrayList<>(); try { list = getAllRole(); } catch
		 * (SQLException e) {
		 *
		 *
		 * System.out.println("fail" +e.getLocalizedMessage()); }
		 * req.setAttribute("listRole", list);
		 * req.getRequestDispatcher("user-add.jsp").forward(req, resp);
		 */

	}

	/*
	 * private List<Role> getAllRole() throws SQLException { String query =
	 * "Select * from Role";
	 *
	 * Connection conn = MysqlConfig.getConnection();
	 *
	 *
	 * PreparedStatement stm = conn.prepareStatement(query);
	 *
	 * ResultSet rs = stm.executeQuery(); List<Role> list = new ArrayList<>();
	 *
	 * while(rs.next()) { Role role = new Role(); role.setId(rs.getInt("id"));
	 * role.setName(rs.getString("name"));
	 * role.setDescription(rs.getString("description"));
	 *
	 * list.add(role); }
	 *
	 * return list;
	 *
	 * }
	 */
}
