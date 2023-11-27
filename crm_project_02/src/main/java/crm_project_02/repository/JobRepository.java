package crm_project_02.repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import crm_project_02.config.MysqlConfig;
import crm_project_02.entity.Job;
import crm_project_02.entity.Project;
import crm_project_02.entity.Status;
import crm_project_02.entity.Users;
import crm_project_02.validation.Validation;

public class JobRepository {

    public List<Job> getAll() {
        List<Job> listJ = new ArrayList<>();

        String query = "SELECT j.id , j.name , p.name as projectName, u.fullName as executor, j.startDate ,j.endDate ,s.name as status\r\n"
        		+ "FROM Job j JOIN Job_Status_Users jsu on j.id = jsu.id_job \r\n"
        		+ "JOIN Status s on jsu.id_status = s.id \r\n"
        		+ "JOIN Users u on u.id =jsu.id_user \r\n"
        		+ "JOIN Project_Users pu  on u.id =pu.id_user AND pu.id_project = j.id_project \r\n"
        		+ "JOIN Project p on p.id =pu.id_project ";
        		

        Connection con = MysqlConfig.getConnection();
        try {
            PreparedStatement stm = con.prepareStatement(query);
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                Job j = new Job();

                j.setId(rs.getInt("id"));
                j.setName(rs.getString("name"));

                Project p = new Project();
                p.setName(rs.getString("projectName"));
                j.setProject(p);

                Users u = new Users();
                u.setFullName(rs.getString("executor"));
                j.setUser(u);

                j.setStartDate(Validation.dateFormatForScreen(rs.getDate("startDate")));
                j.setEndDate(Validation.dateFormatForScreen(rs.getDate("endDate")));

                Status s = new Status();
                s.setName(rs.getString("status"));
                j.setStatus(s);

                listJ.add(j);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listJ;
    }
    
    public Job insertJob(String name, String startDate, String endDate, int id_project) {
        Job job = null;
        String query = "INSERT INTO Job (name, startDate, endDate, id_project) VALUES (?, ?, ?, ?)";
        Connection con = MysqlConfig.getConnection();
        
        try {
            PreparedStatement stm = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, name);
            stm.setString(2, startDate);
            stm.setString(3, endDate);;
            stm.setInt(4, id_project);
            
            int affectedRows = stm.executeUpdate();
            
            if (affectedRows > 0) {
                ResultSet generatedKeys = stm.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int id_job = generatedKeys.getInt(1);
                    job = new Job(id_job, name, startDate, endDate, id_project);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return job;
    }

    
    public int insertJobStatusUser(int id_user, int id_job, int id_status ) {
    	int count =0;
    	String query ="INSERT INTO Job_Status_Users (id_user, id_job,id_status) VALUES (?,?,?)";
    	Connection con = MysqlConfig.getConnection();
    	try {
			PreparedStatement stm = con.prepareStatement(query);
			stm.setInt(1, id_user);
			stm.setInt(2, id_job);
			stm.setInt(3, 1);
			count = stm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
    	return count;
    }
    
    public int insertProjectUser(int id_user,int id_project) {
    	int count =0;
    	String query = "INSERT INTO Project_Users (id_user, id_project) VALUES(?,?)";
    	Connection con = MysqlConfig.getConnection();
    	try {
			PreparedStatement stm = con.prepareStatement(query);
			stm.setInt(1, id_user);
			stm.setInt(2, id_project);
			count = stm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    	return count;
    }
    
    public int checkDuplicatejob(String name, int id_user, int id_project) {
        String query = "SELECT COUNT(*) FROM Job j "
                     + "JOIN Job_Status_Users jsu ON j.id = jsu.id_job "
                     + "JOIN Project_Users pu ON jsu.id_user = pu.id_user AND pu.id_project = j.id_project "
                     + "JOIN Project p ON pu.id_project = p.id "
                     + "WHERE j.name = ? AND jsu.id_user = ? AND p.id = ?";
        
        int duplicateCount = 0;
        
        try (Connection con = MysqlConfig.getConnection();
             PreparedStatement stm = con.prepareStatement(query)) {
            
            stm.setString(1, name);
            stm.setInt(2, id_user);
            stm.setInt(3, id_project);
            
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    duplicateCount = rs.getInt(1);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
        
        return duplicateCount;
    }
    
    public int updateJobStatus(int id_job, int id_status) {
        String query = "UPDATE Job_Status_Users SET id_status = ? WHERE id_job = ?";
        int count = 0;
        
        try (Connection con = MysqlConfig.getConnection();
             PreparedStatement stm = con.prepareStatement(query)) {
            
            stm.setInt(1, id_status);
            stm.setInt(2, id_job);
            
            count = stm.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return count;
    }

    
    public int deleteJobStatusUserrbyId(int id_job) {
		String query = "DELETE FROM Job_Status_Users WHERE id_job =?";
		int count =0;
		Connection con = MysqlConfig.getConnection();
		try {
			PreparedStatement stm = con.prepareStatement(query);
			stm.setInt(1, id_job);
			count = stm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
    
    public int deleteJobbyId(int id_job) {
		String query = "DELETE FROM Job where id = ?";
		int count =0;
		Connection con = MysqlConfig.getConnection();
		try {
			PreparedStatement stm = con.prepareStatement(query);
			stm.setInt(1, id_job);
			count = stm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
    
    public List<Status> getStatusOptions() {
        String query = "SELECT id, name FROM Status";  
        List<Status> listS = new ArrayList<>();
        Connection con = MysqlConfig.getConnection();
        try {
            PreparedStatement stm = con.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Status s = new Status();
                s.setId(rs.getInt("id"));  
                s.setName(rs.getString("name"));
                listS.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listS;
    }
    public int getCurrentStatusId(int id_job) {
        int currentStatusId = 0;
        
        String query = "SELECT id_status FROM Job_Status_Users WHERE id_job = ?";
        try (Connection con = MysqlConfig.getConnection();
             PreparedStatement stm = con.prepareStatement(query)) {
            
            stm.setInt(1, id_job);
            
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    currentStatusId = rs.getInt("id_status");
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return currentStatusId;
    }
    
    public Job getJob(int id_job) {
        Job job = null;
        String query = "SELECT j.id, j.name, p.name as projectName, u.fullName as executor, j.startDate, j.endDate, s.name as status "
                     + "FROM Job j "
                     + "JOIN Job_Status_Users jsu ON j.id = jsu.id_job "
                     + "JOIN Status s ON jsu.id_status = s.id "
                     + "JOIN Users u ON u.id = jsu.id_user "
                     + "JOIN Project_Users pu ON u.id = pu.id_user AND pu.id_project = j.id_project "
                     + "JOIN Project p ON p.id = pu.id_project "
                     + "WHERE j.id = ?";

        Connection con = MysqlConfig.getConnection();
        try {
            PreparedStatement stm = con.prepareStatement(query);
            stm.setInt(1, id_job);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                job = new Job();

                job.setId(rs.getInt("id"));
                job.setName(rs.getString("name"));

                Project project = new Project();
                project.setName(rs.getString("projectName"));
                job.setProject(project);

                Users user = new Users();
                user.setFullName(rs.getString("executor"));
                job.setUser(user);

                job.setStartDate(Validation.dateFormatForScreen(rs.getDate("startDate")));
                job.setEndDate(Validation.dateFormatForScreen(rs.getDate("endDate")));

                Status status = new Status();
                status.setName(rs.getString("status"));
                job.setStatus(status);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return job;
    }


  
    

}
