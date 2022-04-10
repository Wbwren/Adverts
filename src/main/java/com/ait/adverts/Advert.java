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
@Table(name = "adverts")
public class Advert {
	
	@Override
	public String toString() {
		return "Advert [askingPrice=" + askingPrice + ", buyer=" + buyer + ", buyerRating=" + buyerRating
				+ ", category=" + category + ", datePosted=" + datePosted + ", description=" + description + ", id="
				+ id + ", imagePrimary=" + imagePrimary + ", imageQuaternary=" + imageQuaternary + ", imageSecondary="
				+ imageSecondary + ", imageTertiary=" + imageTertiary + ", largestOffer=" + largestOffer + ", location="
				+ location + ", offerAccepted=" + offerAccepted + ", seller=" + seller + ", title=" + title + "]";
	}

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
	
//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name="sellerId", nullable=false)
    
    private String title;
    private double askingPrice;
    private String category;
    private String description;
    private String location;
    private String imagePrimary;
	private String imageSecondary;
	private String imageTertiary;


	private String imageQuaternary;
    private Date datePosted;
    private double largestOffer;
    private boolean offerAccepted;
    private String seller;
	private String buyer;
	private int buyerRating;

	public int getBuyerRating() {
		return buyerRating;
	}

	public void setBuyerRating(int buyerRating) {
		this.buyerRating = buyerRating;
	}

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

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
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

	public String getImagePrimary() {
		return imagePrimary;
	}

	public void setImagePrimary(String imagePrimary) {
		this.imagePrimary = imagePrimary;
	}

	public String getImageSecondary() {
		return imageSecondary;
	}

	public void setImageSecondary(String imageSecondary) {
		this.imageSecondary = imageSecondary;
	}

	public String getImageTertiary() {
		return imageTertiary;
	}

	public void setImageTertiary(String imageTertiary) {
		this.imageTertiary = imageTertiary;
	}

	public String getImageQuaternary() {
		return imageQuaternary;
	}

	public void setImageQuaternary(String imageQuaternary) {
		this.imageQuaternary = imageQuaternary;
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

