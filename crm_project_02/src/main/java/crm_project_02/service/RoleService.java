package crm_project_02.service;

import java.util.List;

import crm_project_02.entity.Role;
import crm_project_02.repository.RoleRepository;

//Service chua nhung class chuyen di xu ly lolgic cho controller
//Cach dat ten : Giong voi controller
//Cach dat ten ham : dat tuong ung voi chuc nang lam tren giao dien ben controller
public class RoleService {

	private RoleRepository rolerepository = new RoleRepository();

	public boolean addRole(String name, String description) {
		int count = rolerepository.insert(name, description);
		return count >0;

	}
	public boolean deleteUserById(int id) {
		int count = rolerepository.deleteRolerbyId(id);
		return count >0;
	}
	
	public boolean updateRole(int id,String name, String description) {
		int count = rolerepository.updateRole(id, name, description);
		return count >0;
	}
	  public Role getRoleById(int id) {
	        return rolerepository.getRoleById(id);
	    }
	  
	  public List<Role> getRoles() {
	       return rolerepository.getRoles();
	        
	    }

}
