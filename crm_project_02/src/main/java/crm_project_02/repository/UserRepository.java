package crm_project_02.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import crm_project_02.config.MysqlConfig;
import crm_project_02.entity.Role;
import crm_project_02.entity.Users;

public class UserRepository {

	public int insert(String fullname, String email, String password, String phone, int id_role) {
		String query = "INSERT INTO Users(fullname,email,pwd,phone,id_role) VALUES(?,?,?,?,?)";
		int count =0;
		Connection con = MysqlConfig.getConnection();
		try {
			PreparedStatement stm = con.prepareStatement(query);
			stm.setString(1, fullname);
			stm.setString(2, email);
			stm.setString(3, password);
			stm.setString(4, phone);
			stm.setInt(5, id_role);
			count = stm.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public List<Role> findAllRole() throws SQLException {
		String query = "Select * from Role";

		Connection conn = MysqlConfig.getConnection();


			PreparedStatement stm = conn.prepareStatement(query);

			ResultSet rs = stm.executeQuery();
			List<Role> list = new ArrayList<>();

			while(rs.next()) {
				Role role = new Role();
				role.setId(rs.getInt("id"));
				role.setName(rs.getString("name"));
				role.setDescription(rs.getString("description"));

				list.add(role);
			}

		return list;

	}

	public List<Users> getAllUser() {
		List<Users> list = new ArrayList<>();
		String query = "SELECT u.id ,u.firstName ,u.lastName,u.fullName ,u.userName ,r.name \r\n"
				+ "FROM Users u join Role r on\r\n"
				+ "u.id_role =r.id ";
		Connection con = MysqlConfig.getConnection();
		try {
			PreparedStatement stm = con.prepareStatement(query);
			ResultSet rs = stm.executeQuery();
			 while(rs.next()) {
				Users user = new Users();
				user.setId(rs.getInt("id"));
	            user.setFirstName(rs.getString("firstName"));
	            user.setLastName(rs.getString("lastName"));
	            user.setFullName(rs.getString("fullName"));
	            user.setUserName(rs.getString("userName"));
	            Role role = new Role();
	            role.setName(rs.getString("name"));
	            user.setId_role(role);

	            list.add(user);


			 }
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public int deleteUserbyId(int id) {
		String query = "DELETE From Users u WHERE u.id =?";
		int count =0;
		Connection con = MysqlConfig.getConnection();
		try {
			PreparedStatement stm = con.prepareStatement(query);
			stm.setInt(1, id);
			count = stm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	public Users searchById(int id_user) {
		Users user = new Users();
		String query = "SELECT  u.phone, u.id, u.email , u.fullName , u.pwd , r.name, u.id_role  FROM Users u JOIN `Role` r WHERE u.id_role = r.id AND u.id = ?";
		Connection con = MysqlConfig.getConnection();
		try {
			PreparedStatement stm = con.prepareStatement(query);
			stm.setInt(1, id_user);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {

				user.setId(rs.getInt("id"));
				user.setPhone(rs.getString("phone"));
				user.setEmail(rs.getString("email"));
				user.setFullName(rs.getString("fullName"));
				user.setPassword(rs.getString("pwd"));
				Role role = new Role();
				role.setId(rs.getInt("id_role"));
				role.setName(rs.getString("name"));
				user.setId_role(role);
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}

		return user;
	}

	public int update(String fullName, String password, String email, String phone, int id_role, int id_user) {
		int count = 0;
		
		String query = "UPDATE Users SET email = ?, pwd = ?,fullName =?, phone = ?,id_role = ? WHERE id = ? ";
		
		Connection con = MysqlConfig.getConnection();
	
		try {
			PreparedStatement stm = con.prepareStatement(query);
			stm.setString(1, email);
			stm.setString(2, password);
			stm.setString(3, fullName);
			stm.setString(4, phone);
			stm.setInt(5, id_role);
			stm.setInt(6, id_user);
			count = stm.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return count;
	}



}
