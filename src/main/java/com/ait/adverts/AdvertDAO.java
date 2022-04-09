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

	//For mocking purposes
	public boolean setEntityManager(EntityManager em) {
		this.em = em;
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public List<Advert> getAllAdverts() {
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
		System.out.println("Attempting to update advert");

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

	@SuppressWarnings("unchecked")
    public List<Advert> getAdvertsBySeller(String username) {
        Query query = em.createQuery("SELECT w FROM Advert w WHERE w.seller LIKE ?1");
		query.setParameter(1, "%"+username.toUpperCase()+"%");
		return query.getResultList();
    }

    public void acceptOffer(int id) {
		Query query = em.createQuery("ALTER table Advert set offerAccepted = true WHERE w.id LIKE ?1");
		query.setParameter(1, "%"+id+"%");
		Advert advert = em.find(Advert.class, id);
		advert.setOfferAccepted(true);
		// return query.getResultList();
    }
}
