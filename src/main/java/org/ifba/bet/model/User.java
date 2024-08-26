package org.ifba.bet.model;

public class User {

	// Atributos de instância
	
	private int id;
	private int accessLevel;
	private Double balance;
	private String name;
	private String email;
	private int favoriteTeam;

	// Atributos estáticos
	
	public static final int REGULAR_USER = 0;
	public static final int ADMIN = 1;
	
	public static final double USER_STANDARD_INITAL_BALANCE = 500d;

	public User(int iD, int accessLevel, Double balance, String name, String email) {
		super();
		this.id = iD;
		this.accessLevel = accessLevel;
		this.balance = balance;
		this.name = name;
		this.email = email;
	}

	public User(int id, int accessLevel, Double balance, String name, String email, int favoriteTeam) {
		super();
		this.id = id;
		this.accessLevel = accessLevel;
		this.balance = balance;
		this.name = name;
		this.email = email;
		this.favoriteTeam = favoriteTeam;
	}

	public int getId() {
		return id;
	}

	public void setId(int iD) {
		this.id = iD;
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

	public int getFavoriteTeam() {
		return favoriteTeam;
	}

	public void setFavoriteTeam(int favoriteTeam) {
		this.favoriteTeam = favoriteTeam;
	}

	@Override
	public String toString() {
		return "User [ID=" + id + ", accessLevel=" + accessLevel + ", balance=" + balance + ", name=" + name
				+ ", email=" + email + "]";
	}

}
