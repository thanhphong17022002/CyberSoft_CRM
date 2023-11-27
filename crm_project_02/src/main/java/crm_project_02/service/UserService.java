package crm_project_02.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import crm_project_02.entity.Role;
import crm_project_02.entity.Users;
import crm_project_02.repository.UserRepository;

public class UserService {

	private UserRepository userrepository = new UserRepository();


	public boolean addUser(String fullname, String email, String password, String phone, int id_role) {
		int count = userrepository.insert(fullname, email, password, phone, id_role);
		return count >0;
	}
	public List<Role> getRole() throws SQLException{
		List<Role> list = new ArrayList<>();
		return list = userrepository.findAllRole();
	}
	public List<Users> getUsers()  {
	    return userrepository.getAllUser();
	}
	public boolean deleteUserById(int id) {
		int count = userrepository.deleteUserbyId(id);
		return count >0;
	}
	
	public Users getUserById(int id_user) {
		return userrepository.searchById(id_user);
	}
	
	public boolean updateUser(String fullName, String password, String email, String phone, int id_role, int id_user) {
		int count =userrepository.update(fullName, password, email, phone, id_role, id_user);
		return count>0;
	}
	
}
