package model;

public class User {
	
    private int ID;
    private int accessLevel; 
    private Double balance;
    private String name;
    private String email;
    private String password;
    
	public User(int iD, int accessLevel, Double balance, String name, String email, String password) {
		super();
		ID = iD;
		this.accessLevel = accessLevel;
		this.balance = balance;
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(int accessLevel) {
		this.accessLevel = accessLevel;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@Override
	public String toString() {
		return "User [ID=" + ID + ", accessLevel=" + accessLevel + ", balance=" + balance + ", name=" + name
				+ ", email=" + email + ", password=" + password + "]";
	}
    
    
}
