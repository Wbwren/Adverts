package com.ait.adverts;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Timestamp;
import java.time.Instant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AdvertTest {
	Advert advert;
	Timestamp timestamp;
	@BeforeEach
	void setUp() throws Exception {
		advert = new Advert();
		timestamp = Timestamp.from(Instant.now());
	}

	@Test
	void testAdvertGettersAndSetters() {
        advert.setAskingPrice(500);
        advert.setBuyerId("bob@g.com");
        advert.setBuyerLeftRating(false);
        advert.setBuyerRating(5);
        advert.setCategory("Gaming");
        advert.setDatePosted(timestamp);
        advert.setDescription("High End Gaming PC");
        advert.setId(90);
        advert.setImagePrimary("gamingPcImg.png");
        advert.setImageSecondary(null);
        advert.setImageTertiary(null);
        advert.setImageQuaternary(null);
        advert.setLargestOffer(600);
        advert.setOfferAccepted(false);
        advert.setOutForDelivery(false);
        advert.setLocation("Laois");
        advert.setSeller("wren@g.com");
        advert.setSellerLeftRating(false);
        advert.setSellerRating(0);
        advert.setTitle("Gaming PC");
        advert.setWarrantyIncluded(true);
        

        assertEquals(500, advert.getAskingPrice());
        assertEquals("bob@g.com", advert.getBuyerId());
        assertEquals(false, advert.isBuyerLeftRating());
        assertEquals(5, advert.getBuyerRating());
        assertEquals("Gaming", advert.getCategory());
        assertEquals(timestamp, advert.getDatePosted());
        assertEquals("High End Gaming PC", advert.getDescription());
        assertEquals(90, advert.getId());
        assertEquals("gamingPcImg.png", advert.getImagePrimary());
        assertEquals(null, advert.getImageSecondary());
        assertEquals(null, advert.getImageTertiary());
        assertEquals(null, advert.getImageQuaternary());
        assertEquals(600, advert.getLargestOffer());
        assertEquals(false, advert.isOfferAccepted());
        assertEquals(false, advert.isOutForDelivery());
        assertEquals("Laois", advert.getLocation());
        assertEquals("wren@g.com", advert.getSeller());
        assertEquals(false, advert.isSellerLeftRating());
        assertEquals(0, advert.getSellerRating());
        assertEquals("Gaming PC", advert.getTitle());
        assertEquals(true, advert.isWarrantyIncluded());
        























    }
}
