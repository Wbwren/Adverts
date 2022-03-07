package com.ait.adverts;

import java.util.Date;

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
@Table(name = "adverts")
public class Advert {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
	
//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name="sellerId", nullable=false)
    
    private String title;
    private double askingPrice;
    private String category;
    private String description;
    private String location;
    private String image;
    private Date datePosted;
    private double largestOffer;
    private boolean offerAccepted;
    private String seller;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSeller() {
		return seller;
	}
	
	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getAskingPrice() {
		return askingPrice;
	}

	public void setAskingPrice(double askingPrice) {
		this.askingPrice = askingPrice;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Date getDatePosted() {
		return datePosted;
	}

	public void setDatePosted(Date datePosted) {
		this.datePosted = datePosted;
	}

	public double getLargestOffer() {
		return largestOffer;
	}

	public void setLargestOffer(double largestOffer) {
		this.largestOffer = largestOffer;
	}

	public boolean isOfferAccepted() {
		return offerAccepted;
	}

	public void setOfferAccepted(boolean offerAccepted) {
		this.offerAccepted = offerAccepted;
	}
    
   

}

