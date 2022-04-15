package com.ait.adverts;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
	
	@Id
	private String email;	
//  @OneToMany(mappedBy = "seller")
//  private Set<Advert> adverts = new HashSet<>();
	
	private String saltValue;
	private String hashValue;
    private String userType;
	private double ratingTotal;
	private int numberOfRatings;
	private static final long serialVersionUID = 3657551019023598980L;
	
	public double getRatingTotal() {
		return ratingTotal;
	}

	public void setRatingTotal(double ratingTotal) {
		this.ratingTotal = ratingTotal;
	}

	public int getNumberOfRatings() {
		return numberOfRatings;
	}

	public void setNumberOfRatings(int numberOfRatings) {
		this.numberOfRatings = numberOfRatings;
	}

    public void setUserType(String userType) {
		this.userType = userType;
	}

    public String getUserType() {
		return this.userType;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	
}
