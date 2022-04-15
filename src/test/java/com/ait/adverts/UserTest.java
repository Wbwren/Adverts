package com.ait.adverts;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {
    // private EntityManager em;
	// private Advert advert;
	// private AdvertWS advertWS;
	// private AdvertDao advertDAO;
	// private Query query;
	// private List<Advert> adverts; 
    private User user;
	
	@BeforeEach
	void setUp() throws Exception {
		user = new User();
	}

	@Test
	void test() {
        user.setEmail("w@g.com");
        user.setNumberOfRatings(10);
        user.setRatingTotal(50);
        user.setPassword("wren");

        assertEquals(user.getEmail(), "w@g.com");
        assertEquals(user.getNumberOfRatings(), 10);
        assertEquals(user.getRatingTotal(), 50);
        assertEquals(user.getHashValue(), "6Gy9ZV7YuIqJxBcVhF7IaXGhsHbUba6l2ONl6X9/vz/ox4lXMLmR6PlPQ3EATtobSW3GyBxzPcsoEmJME3xpOw==");


    }
}
