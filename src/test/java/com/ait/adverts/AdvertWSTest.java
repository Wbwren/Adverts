package com.ait.adverts;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import javax.ws.rs.core.Response;
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
	private AdvertDao advertDAO;
	private Query query;
	private List<Advert> adverts;
	Response response;

	
	@BeforeEach
	void setUp() throws Exception {
		advert = new Advert();
		adverts = new ArrayList<>();
		advertWS = new AdvertWS();
		advertDAO = new AdvertDao();
		em = mock(EntityManager.class);
		advertDAO.setEntityManager(em);
		query = mock(Query.class);
		adverts.add(advert);
		advertWS.setAdvertDao(advertDAO);
		response = mock(Response.class);
		
	}

	@Test
	void test() {
		when(em.createQuery(Matchers.anyString())).thenReturn(query);
		when(query.getResultList()).thenReturn(adverts);
		List<Advert> queryResult = advertWS.findByKeyword("sam", 4000, true);
		assertEquals(queryResult, adverts);
		
		
//		when(em.createQuery("SELECT a FROM Advert a")).thenReturn(query);
//		when(query.getResultList()).thenReturn(adverts);
//		
//		advertWS.findAll();
		
		
	}

}
