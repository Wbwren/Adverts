package com.ait.adverts;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.sql.Date;

import javax.ejb.Local;
import javax.persistence.CascadeType;
//import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;



@Entity
@XmlRootElement
@Table(name = "ratings")
public class Rating {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String userId;    
    private double rating;

    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
}

