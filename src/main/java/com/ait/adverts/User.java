package com.ait.adverts;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

enum UserType {
    SELLER, BUYER;
}

@Entity
public class User {
	
	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	
//  @OneToMany(mappedBy = "seller")
//  private Set<Advert> adverts = new HashSet<>();
	
	private String email;
	private String password;
	private String saltValue;
	private String hashValue;
    private UserType userType;
	// private static final long serialVersionUID = 3657551019023598980L;

    public void setUserType(UserType userType) {
		this.userType = userType;
	}

    public UserType getUserType() {
		return this.userType;
	}

    public int getId() {
		return this.userId;
	}

	public void setId(int userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return this.email;
	}

	public void setUserId(String email) {
		this.email = email;
	} 

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		Encryption encryption = new Encryption();
		this.saltValue = "gY'4=*J[X?C}p_'}yelMA80+k[}wPd";
		this.hashValue = encryption.createEncryptedPassword(password);
	}
	
	public String getHashValue() {
		return hashValue;
	}
	
	public void setHashValue(String hashValue) {
		this.hashValue = hashValue;
	}

	public String getSaltValue() {
		return saltValue;
	}
	
	public void setSaltValue(String saltValue) {
		this.saltValue = saltValue;
    }

	@Override
	public String toString() {
		return "User{" + "userId=" + userId + ", email='" + email + '\'' + ", password='" + password + '\'' + ", hash='" + hashValue + '\'' + ", salt='" + saltValue + '}';
	}
}
