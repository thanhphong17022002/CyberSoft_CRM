package crm_project_02.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_project_02.config.MysqlConfig;
import crm_project_02.entity.Role;
import crm_project_02.service.RoleService;

@WebServlet(name = "RoleController", urlPatterns = {"/role-add","/role","/role-update"})
public class RoleController extends HttpServlet {


	private RoleService roleService = new RoleService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       String path = req.getServletPath();
       if(path.equals("/role-add")) {
    	   req.getRequestDispatcher("role-add.jsp").forward(req, resp);
       }else if(path.equals("/role")){
    	   String query = " SELECT * from Role";
    	   Connection conn = MysqlConfig.getConnection();
    	   List<Role> list = new ArrayList<>();
    	   try {
    		   PreparedStatement stm = conn.prepareStatement(query);
    		   ResultSet rs = stm.executeQuery();
    		   while(rs.next()) {
    			   Role r = new Role();

    			   r.setId(rs.getInt("id"));
    			   r.setName(rs.getString("name"));
    			   r.setDescription(rs.getString("description"));

    			   list.add(r);
    		   }
    	   }catch (SQLException e) {
			e.printStackTrace();
		}

    	   req.setAttribute("listR", list);
    	   req.getRequestDispatcher("role-table.jsp").forward(req, resp);
       }else if (path.equals("/role-update")) {
		
    	   int id = Integer.parseInt(req.getParameter("id"));
    	    Role role = roleService.getRoleById(id); 
    	    req.setAttribute("role", role);
    	    req.getRequestDispatcher("role-update.jsp").forward(req, resp);
    	   
	}
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	 req.setCharacterEncoding("UTF-8");
    	 resp.setContentType("text/html; charset=UTF-8");

    	 String path = req.getServletPath();
    	 if(path.equals("/role-add")) {
    		 String name = req.getParameter("name");
    	    	String description = req.getParameter("description");

    			boolean isSuccess = roleService.addRole(name, description);
    			req.setAttribute("isSuccess", isSuccess);


    	    	req.getRequestDispatcher("/role-add.jsp").forward(req, resp);
    	 }else if (path.equals("/role-update")) {
    		 int id = Integer.parseInt(req.getParameter("id"));
    		    String name = req.getParameter("name");
    		    String description = req.getParameter("description");
    		    boolean isSuccess = roleService.updateRole(id, name, description);
    		    req.setAttribute("isSuccess", isSuccess);
    		    req.getRequestDispatcher("role-update.jsp").forward(req, resp);
		}
    	


    	/*
		 * req.setCharacterEncoding("UTF-8");
		 * resp.setContentType("text/html; charset=UTF-8");
		 *
		 * String name = req.getParameter("name"); String description =
		 * req.getParameter("description");
		 *
		 * String query = "INSERT INTO Role(name, description) VALUES (?, ?)";
		 *
		 *
		 * Connection conn = MysqlConfig.getConnection(); boolean isSuccess = false; try
		 * { PreparedStatement stm = conn.prepareStatement(query); stm.setString(1,
		 * name); stm.setString(2, description); int countRow = stm.executeUpdate(); if
		 * (countRow > 0) { isSuccess = true; System.out.println("add successfull"); }
		 * else { System.out.println("add fail"); } } catch (SQLException e) {
		 * e.printStackTrace();
		 * resp.getWriter().println("Error occurred while adding role"); } finally { try
		 * { if (conn != null) { conn.close(); } } catch (SQLException e) {
		 * e.printStackTrace(); } } req.setAttribute("isSuccess", isSuccess);
		 * req.getRequestDispatcher("role-add.jsp").forward(req, resp);
		 */
    }
}
