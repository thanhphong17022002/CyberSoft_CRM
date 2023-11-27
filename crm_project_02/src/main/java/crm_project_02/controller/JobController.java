package crm_project_02.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import crm_project_02.entity.Job;
import crm_project_02.entity.Project;
import crm_project_02.entity.Status;
import crm_project_02.entity.Users;
import crm_project_02.service.JobService;
import crm_project_02.service.ProjectService;
import crm_project_02.service.UserService;

@WebServlet(name="jobController", urlPatterns = {"/task", "/task-add","/task-update"})
public class JobController extends HttpServlet{

	private JobService jobservice = new JobService();
	private ProjectService projectservice = new ProjectService();
	private UserService userservice = new UserService();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getServletPath();

        if (path.equals("/task-add")) {
            loadProjectAndUsersToRequest(req);
            req.getRequestDispatcher("task-add.jsp").forward(req, resp);
        } else if (path.equals("/task")) {
            List<Job> listJob = new ArrayList<>();
            try {
                listJob = jobservice.getAllJob();
            } catch (Exception e) {
                System.out.println(e.getLocalizedMessage());
            }

            req.setAttribute("listJob", listJob);
            req.getRequestDispatcher("task.jsp").forward(req, resp);
        }else if(path.equals("/task-update")){
        	
        	
   
        	int id_job = Integer.parseInt(req.getParameter("id"));
        	Job job = jobservice.getJob(id_job);
        	List<Status> listS = new ArrayList<>();
	        listS = jobservice.getStatusOptions();
	        int currentStatusId = jobservice.getCurrentStatusId(id_job);
	        
	        req.setAttribute("job", job);
	        req.setAttribute("listS", listS);
	        req.setAttribute("currentStatusId", currentStatusId);
        	req.getRequestDispatcher("profile-edit.jsp").forward(req, resp);
        }
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			
			req.setCharacterEncoding("UTF-8");
		    resp.setContentType("text/html; charset=UTF-8");
			String path =req.getServletPath();
			if(path.equals("/task-add")) {
				String name = req.getParameter("name");
			    String startdateStr = req.getParameter("startDate");
			    String endDateStr = req.getParameter("endDate");
			    int id_user = Integer.parseInt(req.getParameter("id_user"));
			    int id_project = Integer.parseInt(req.getParameter("id_project"));

			    Date startD = null;
			    Date endD = null;

			    if (startdateStr.isEmpty()) {
			        req.setAttribute("errStartD", "Wrong format date");
			    } else {
			        startD = Date.valueOf(startdateStr);
			    }
			    if (endDateStr.isEmpty()) {
			        req.setAttribute("errEndD", "wrong format date");
			    } else {
			        endD = Date.valueOf(endDateStr);
			    }
			    if (startD != null && endD != null && startD.after(endD)) {
			        req.setAttribute("errDate", "The end date must be after the start date");
			    }

			    loadProjectAndUsersToRequest(req);
			    
			    boolean isSuccess = false; 
			    
			    if (jobservice.checkDuplicateJob(name, id_user, id_project)) {
			        req.setAttribute("dup", "can't give the same job to one person");
			    } else {
			        isSuccess = jobservice.addJob(name, startdateStr, endDateStr, id_project, id_user);
			    }
			    
			    
			    req.setAttribute("isSuccess", isSuccess);

			    req.getRequestDispatcher("task-add.jsp").forward(req, resp);
			}else if(path.equals("/task-update")) {
				int id_job = Integer.parseInt(req.getParameter("id"));
		        int id_status = Integer.parseInt(req.getParameter("id_status"));  
		        boolean isSuccess = jobservice.updateStatusJob(id_job, id_status);
		        
		        if (isSuccess) {
		            isSuccess =true;
		            req.setAttribute("updateSuccess", "Job status updated successfully.");
		        } else {
		            isSuccess = false;
		            req.setAttribute("updateError", "Failed to update job status.");
		        }
		        List<Status> listS = new ArrayList<>();
		        listS = jobservice.getStatusOptions();
		        int currentStatusId = jobservice.getCurrentStatusId(id_job);
		        
		        req.setAttribute("listS", listS);
		        req.setAttribute("currentStatusId", currentStatusId);
		        req.getRequestDispatcher("profile-edit.jsp").forward(req, resp);


			}
		
			

		    
}
	
	private void loadProjectAndUsersToRequest(HttpServletRequest req) {
        List<Project> listP = projectservice.getAllproject();
        List<Users> listU = userservice.getUsers();
        req.setAttribute("listP", listP);
        req.setAttribute("listU", listU);
    }
}
