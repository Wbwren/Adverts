package com.ait.adverts;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Buyer {
	
	@Id	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int buyerId;
	
//	@OneToMany(mappedBy = "seller")
//    private Set<Advert> adverts = new HashSet<>();
	
	private String email;
	private String password;
	private String saltValue;
	private String hashValue;
	private static final long serialVersionUID = 3657551019023598980L;

	public int getId() {
		return this.buyerId;
	}

	public void setId(int buyerId) {
		this.buyerId = buyerId;
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
		return "User{" + "buyerId=" + buyerId + ", email='" + email + '\'' + ", password='" + password + '\'' + ", hash='" + hashValue + '\'' + ", salt='" + saltValue + '}';
	}
}
