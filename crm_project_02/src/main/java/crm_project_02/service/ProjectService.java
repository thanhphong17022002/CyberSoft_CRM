package crm_project_02.service;

import java.sql.Date;
import java.util.List;

import crm_project_02.entity.Project;
import crm_project_02.repository.ProjectRepository;

public class ProjectService {

	private ProjectRepository projectrepository = new ProjectRepository();

	public boolean addProject(String name, Date dateStart, Date dateEnd) {
		int count = projectrepository.insert(name, dateStart, dateEnd);
		return count>0;
	}
	
	public List<Project> getAllproject(){
		return projectrepository.findAll();
	}
	
	public boolean deleteProject(int id) {

		int count2 = projectrepository.deleteProjectById(id);
		return count2>0;
	}
	
	public Project getProjectById(int id_project) {
		return projectrepository.search(id_project);
	}
	
	public boolean updateProject(String name, Date dateStart, Date dateEnd, int id_project) {
		int count = projectrepository.update(name, dateStart, dateEnd, id_project);
		return count >0;
	}
}
