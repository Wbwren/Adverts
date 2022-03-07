package com.ait.adverts;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
@LocalBean
public class AdvertDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<Advert> getAllAdverts() {
		System.out.println("got here");
		Query query=em.createQuery("SELECT a FROM Advert a");
		return query.getResultList();
	}

	public Advert getAdvert(int id) {
		return em.find(Advert.class, id);
	}

	public void save(Advert advert) {
		em.persist(advert);
	}

	public void update(Advert advert) {
		em.merge(advert);
	}

	public void delete(int id) {
		em.remove(getAdvert(id));
	}

	@SuppressWarnings("unchecked")
	public List<Advert> getAdvertsByKeyword(String keyword) {
		Query query = em.createQuery("SELECT w FROM Advert w WHERE w.title LIKE ?1");
		query.setParameter(1, "%"+keyword.toUpperCase()+"%");
		return query.getResultList();
	}
}
