package crm_project_02.service;

import java.util.List;

import crm_project_02.entity.Job;
import crm_project_02.entity.Project;
import crm_project_02.entity.Status;
import crm_project_02.entity.Users;
import crm_project_02.repository.JobRepository;
import crm_project_02.repository.ProjectRepository;
import crm_project_02.repository.UserRepository;

public class JobService {

	private JobRepository jobrepository = new JobRepository();
	private UserRepository userrepositiory = new UserRepository();
	private ProjectRepository projectRepository = new ProjectRepository();

	public List<Job> getAllJob() {
		return jobrepository.getAll();
	}

	public List<Users> getAllUser() {
		return userrepositiory.getAllUser();
	}

	public List<Project> getAllProject() {
		return projectRepository.findAll();
	}

	public boolean addJob(String name, String startDate, String endDate, int id_project, int id_user) {
		Job j = jobrepository.insertJob(name, startDate, endDate, id_project);

		if (j != null) {
			jobrepository.insertProjectUser(id_user, id_project);
			int count = jobrepository.insertJobStatusUser(id_user, j.getId(), 2);

			if (count <= 0) {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	public boolean checkDuplicateJob(String name, int id_user, int id_project) {
		int count = jobrepository.checkDuplicatejob(name, id_user, id_project);
		return count > 0;

	}
	
	public boolean updateStatusJob(int id_job, int id_status) {
	    int count = jobrepository.updateJobStatus(id_job, id_status);
	    return count > 0;
	}

	
	public boolean deleteJob(int id_job) {
	    int count = jobrepository.deleteJobStatusUserrbyId(id_job);
	    int count1 = jobrepository.deleteJobbyId(id_job);
	    return count > 0 && count1 > 0;
	}
	public List<Status> getStatusOptions() {
        return jobrepository.getStatusOptions();
    }

    public int getCurrentStatusId(int id_job) {
        return jobrepository.getCurrentStatusId(id_job);
    }
    
    public Job getJob(int id_job) {
    	return jobrepository.getJob(id_job);
    }


}
