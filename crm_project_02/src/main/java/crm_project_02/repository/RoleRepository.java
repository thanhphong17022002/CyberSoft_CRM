package crm_project_02.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import crm_project_02.config.MysqlConfig;
import crm_project_02.entity.Role;

//chu toan bo cau truy van lien quan toi bang ROLE
public class RoleRepository {

	public int insert(String name, String description) {

		String query = "INSERT INTO Role(name, description) VALUES (?, ?)";
		int count =0;
		Connection conn = MysqlConfig.getConnection();
		try {
			PreparedStatement stm = conn.prepareStatement(query);
			stm.setString(1, name);
			stm.setString(2, description);

			count = stm.executeUpdate();


		} catch (SQLException e) {

			System.out.println("fail " +e.getLocalizedMessage());

		}
		return count;
	}
	public int deleteRolerbyId(int id) {
		String query = "DELETE From Role  WHERE id =?";
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
	
	public int updateRole(int id, String name, String description) {
	    String query = "UPDATE Role SET name = ?, description = ? WHERE id = ?";
	    int count = 0;
	    
	    Connection conn = MysqlConfig.getConnection();
	    try {
	        PreparedStatement stm = conn.prepareStatement(query);
	        stm.setString(1, name);
	        stm.setString(2, description);
	        stm.setInt(3, id);
	        
	        count = stm.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    
	    return count;
	}
	 public Role getRoleById(int id) {
	        String query = "SELECT * FROM Role WHERE id = ?";
	        Role role = null;
	        
	        Connection conn = MysqlConfig.getConnection();
	        try {
	            PreparedStatement stm = conn.prepareStatement(query);
	            stm.setInt(1, id);
	            
	            ResultSet rs = stm.executeQuery();
	            if (rs.next()) {
	                role = new Role();
	                role.setId(rs.getInt("id"));
	                role.setName(rs.getString("name"));
	                role.setDescription(rs.getString("description"));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        
	        return role;
	    }
	 
	 public List<Role> getRoles() {
		    String query = "SELECT * FROM Role";

		    Connection conn = MysqlConfig.getConnection();
		    List<Role> list = new ArrayList<>();

		    try {
		        PreparedStatement stm = conn.prepareStatement(query);
		        ResultSet rs = stm.executeQuery();

		        while (rs.next()) {
		            Role role = new Role();
		            role.setId(rs.getInt("id"));
		            role.setName(rs.getString("name"));
		            role.setDescription(rs.getString("description"));
		            list.add(role);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }

		    return list;
		}


	
	
}
