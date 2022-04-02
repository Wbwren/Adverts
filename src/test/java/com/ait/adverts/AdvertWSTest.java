package com.ait.adverts;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Matchers;

class AdvertWSTest {
	private EntityManager em;
	private Advert advert;
	private AdvertWS advertWS;
	private AdvertDAO advertDAO;
	private Query query;
	private List<Advert> adverts; 
	
	@BeforeEach
	void setUp() throws Exception {
		advert = new Advert();
		adverts = new ArrayList<>();
		advertWS = new AdvertWS();
		advertDAO = new AdvertDAO();
		em = mock(EntityManager.class);
		query = mock(Query.class);
		adverts.add(advert);
		advertWS.setAdvertDao(advertDAO);
		advertDAO.setEntityManager(em);

		
	}

	@Test
	void test() {
		when(em.createQuery(Matchers.anyString())).thenReturn(query);
		when(query.getResultList()).thenReturn(adverts);
		List<Advert> queryResult = advertWS.findByKeyword("sam");
		assertEquals(queryResult, adverts);
	}

}
