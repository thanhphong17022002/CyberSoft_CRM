package crm_project_02.entity;

public class Users {
	private int id;
	private String firstName;
	private String lastName;
	private String userName;
	private String email;
	private String password;
	private String fullName;
	private String phone;
	
	
	private Role id_role;

	public Users() {

	}

	public Users(int id, String firstName, String lastName,String fullName, String userName, Role id_role) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.fullName = fullName;
		this.userName = userName;
		this.id_role = id_role;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Users(int id, String email, String password) {
		this.id = id;
		this.email = email;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getId_role() {
		return id_role;
	}

	public void setId_role(Role role) {
		this.id_role = role;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	

}
