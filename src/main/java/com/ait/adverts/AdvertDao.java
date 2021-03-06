package com.ait.adverts;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Stateless
@LocalBean
public class AdvertDao {
	
	@PersistenceContext
	private EntityManager em;

	//For mocking purposes
	public boolean setEntityManager(EntityManager em) {
		this.em = em;
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public List<Advert> getAllAdverts() {
		System.out.println("in dao");
		
		Query query=em.createQuery("SELECT a FROM Advert a");
		
		System.out.println(query.getClass());
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
	public List<Advert> getAdvertsByKeyword(String keyword, double priceRange, boolean warrantyIncluded) {
		Query query = null;
		
		if(priceRange == 0) {
			priceRange = Double.MAX_VALUE;
		}
		if (warrantyIncluded) {
			query = em.createQuery("SELECT w FROM Advert w WHERE w.title LIKE ?1 and w.askingPrice < ?2 and w.warrantyIncluded = true");

		} else {
			query = em.createQuery("SELECT w FROM Advert w WHERE w.title LIKE ?1 and w.askingPrice < ?2");
		}
		query.setParameter(1, "%"+keyword.toUpperCase()+"%");
		query.setParameter(2, priceRange);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
    public List<Advert> getAdvertsBySeller(String username) {
        Query query = em.createQuery("SELECT w FROM Advert w WHERE w.seller = ?1");
		query.setParameter(1, username);
		return query.getResultList();
    }

    public void acceptOffer(int id) {
		Advert advert = em.find(Advert.class, id);
		advert.setOfferAccepted(true);
		update(advert);
    }

	public void markOutForDelivery(int id) {
		Advert advert = em.find(Advert.class, id);
		advert.setOutForDelivery(true);
		update(advert);
    }

    public boolean placeOffer(int advertId, String buyerId, double offer) {
		Advert advert = em.find(Advert.class, advertId);
		double largestOffer = advert.getLargestOffer();
		if(offer > largestOffer) {
			advert.setLargestOffer(offer);
			advert.setBuyerId(buyerId);
			return true;
		}
		return false;

    }

	public List<Advert> getAdvertsByBuyer(String username) {
		Query query = em.createQuery("SELECT w FROM Advert w WHERE w.buyerId = ?1");
		query.setParameter(1, username);
		return query.getResultList();
	}

    public void toggleRating(String userId, int advertId, String userType) {
		Advert advert = em.find(Advert.class, advertId);
		if (userType.equalsIgnoreCase("seller")) {
			advert.setBuyerLeftRating(true);
		} else if(userType.equalsIgnoreCase("buyer")) {
			advert.setSellerLeftRating(true);
		}
    }

    public void withdrawOffer(int advertId) {
		Advert advert = em.find(Advert.class, advertId);
		advert.setLargestOffer(0);
		advert.setBuyerId(null);
		update(advert);
    }
}
