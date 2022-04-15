package com.ait.adverts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AdvertDaoTest {
    private EntityManager em;
    private Query query;
    private Advert advert;
    private AdvertDao advertDao;
    ArrayList<Advert> adverts;
    
    @BeforeEach
    void setUp() {
    	em = mock(EntityManager.class);
    	query = mock(Query.class);
    	advert = new Advert();
    	advertDao = new AdvertDao();
    	advertDao.setEntityManager(em);
    	adverts = new ArrayList<>();
    	adverts.add(advert);
    }
    
    @Test
    void testGetAllAdverts() {
    	when(em.createQuery("SELECT a FROM Advert a")).thenReturn(query);
    	when(query.getResultList()).thenReturn(adverts);
    	
    	assertEquals(adverts, advertDao.getAllAdverts());
    	
    	// getAdvert
    	when(em.find(Advert.class, 5)).thenReturn(advert);
    	assertEquals(advert, advertDao.getAdvert(5));
    	
    	// save
    	doNothing().when(em).persist(advert);
    	advertDao.save(advert);

    	
    	//update
    	when(em.merge(advert)).thenReturn(advert);
    	advertDao.update(advert);
    	
    	//delete
    	doNothing().when(em).remove(3);
    	advertDao.delete(3);
    	
    	//getAdvertsByKeyword
    	
    	
    	when(em.createQuery("SELECT w FROM Advert w WHERE w.title LIKE ?1 and w.askingPrice < ?2 and w.warrantyIncluded = true"))
    	.thenReturn(query);
    	when(query.getResultList()).thenReturn(adverts);
    	assertEquals(adverts, advertDao.getAdvertsByKeyword("sam", 0, true));
    	
    	
    	when(em.createQuery("SELECT w FROM Advert w WHERE w.title LIKE ?1 and w.askingPrice < ?2"))
    	.thenReturn(query);
    	when(query.getResultList()).thenReturn(adverts);
    	assertEquals(adverts, advertDao.getAdvertsByKeyword("sam", 0, false));
    	
    	
    	//getAdvertsBySeller
    	when(em.createQuery("SELECT w FROM Advert w WHERE w.seller = ?1")).thenReturn(query);
    	when(query.getResultList()).thenReturn(adverts);
    	assertEquals(adverts, advertDao.getAdvertsBySeller("john"));
    	
    	
    	//acceptOffer
    	when(em.find(Advert.class, 5)).thenReturn(advert);
    	advertDao.acceptOffer(5);
    	
    	
    	//markOutForDelivery
    	when(em.find(Advert.class, 5)).thenReturn(advert);
    	advertDao.markOutForDelivery(5);
    	
    	
    	//placeOffer
    	
    	advert.setLargestOffer(5);
    	when(em.find(Advert.class, 10)).thenReturn(advert);
    	assertTrue(advertDao.placeOffer(10, "b@g.com", 10));
    	
    	advert.setLargestOffer(5);
    	when(em.find(Advert.class, 10)).thenReturn(advert);
    	assertFalse(advertDao.placeOffer(10, "b@g.com", 4));
    	
    	
    	//getAdvertsByBuyer
    	when(em.createQuery("SELECT w FROM Advert w WHERE w.buyerId = ?1")).thenReturn(query);
    	when(query.getResultList()).thenReturn(adverts);
    	assertEquals(adverts, advertDao.getAdvertsByBuyer("b@g.com"));
    	
    	
    	//toggleRating
    	when(em.find(Advert.class, 10)).thenReturn(advert);
    	advertDao.toggleRating("b@g.com", 10, "seller");
    	assertTrue(advert.isBuyerLeftRating());
    	
    	when(em.find(Advert.class, 10)).thenReturn(advert);
    	advertDao.toggleRating("b@g.com", 10, "buyer");
    	assertTrue(advert.isSellerLeftRating());
    	
    	//withdrawOffer
    	when(em.find(Advert.class, 10)).thenReturn(advert);
    	when(em.merge(advert)).thenReturn(advert);
    	advertDao.withdrawOffer(10);
    	
    }
    
    
}
