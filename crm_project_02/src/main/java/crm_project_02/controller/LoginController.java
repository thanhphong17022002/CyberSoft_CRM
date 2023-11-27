package crm_project_02.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_project_02.config.MysqlConfig;
import crm_project_02.entity.Users;

@WebServlet(name = "LoginController" , urlPatterns = {"/login"})
public class LoginController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Cookie cookie = new Cookie("name",URLEncoder.encode("nguyễn Văn A","UTF-8" ));
		cookie.setMaxAge(60*60);

		resp.addCookie(cookie);

		req.getRequestDispatcher("login.html").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String email = req.getParameter("emmail");
		String password = req.getParameter("password");

		//buoc 2
		String query = "select * from Users u\r\n"
				+ "where u.email =?  AND u.pwd =?";

		// mo ket noi toi co so du lieu
		Connection con = MysqlConfig.getConnection();

		try {
			//chuan bi truyen query xuong csdl thong qua prepareStatement
			PreparedStatement stm = con.prepareStatement(query);

			//gan gia tri cho tham so trong cau query co dau ??
			//
			stm.setString(1, email);
			stm.setString(2, password);

			//thuc thi query
			//executeUpdate: Neu cau query khac select
			//executeQuery : Neu cau query la select

			ResultSet rs = stm.executeQuery();
			List<Users> list = new ArrayList<>();

			//khi nao resultSet ma con qua dong tiep theo dc thi lam
			while(rs.next()) {
				Users u = new Users();
				u.setId(rs.getInt("id"));
				u.setEmail(rs.getString("email"));
				u.setPassword(rs.getString("password"));

				list.add(u);
			}
			if(list.size()>0) {
				//user ton tai
				System.out.println("login successful");
			}else {
				//user khong ton tai , dangnhap that bai
				System.out.println("login failed");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
