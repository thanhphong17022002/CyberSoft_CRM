package crm_project_02.entity;

public class Job {
	private int id;
    private String name;
    private String projectName;
    private String executor;
    private String startDate;
    private String endDate;
    private String statusName;

    private Users user;
    private Status status;
    private Project project;

    public Job() {
    }

    public Job(int id,String name,String startDate, String endDate, int id_project) {
    	this.id = id;
    	this.name = name;
    	this.startDate = startDate;
    	this.endDate = endDate;
    	
    }
    
    public Job(int id, String name, String projectName, String executor, String startDate, String endDate, String statusName) {
        this.id = id;
        this.name = name;
        this.projectName = projectName;
        this.executor = executor;
        this.startDate = startDate;
        this.endDate = endDate;
        this.statusName = statusName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }



}