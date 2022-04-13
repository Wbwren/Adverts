package com.ait.adverts;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class UserDao {

	@PersistenceContext
	private EntityManager em;
	
    // allows for mock testing
	public boolean setEntityManager(EntityManager em) {
		this.em = em;
		return true;
	}

	public User login(User user) {
		Query query = em.createQuery("SELECT u FROM User u");
		List<User> u = query.getResultList();
		for(User us: u) {
			if(us.getUserEmail().equals(user.getUserEmail()) && us.getHashValue().equals(user.getHashValue())) {
				return us;
			}
		}
		return null;
		
	}
	
	public boolean register(User user) {
		em.persist(user);
		return true;
	}

	public List<User> findAllUsers() {
		Query query = em.createQuery("SELECT u FROM User u");
		return query.getResultList();
	}

	public void leaveRating(String uid, int r) {
		Rating rating = new Rating();

		Query query = em.createQuery("SELECT r FROM Rating r");
		List<Object> ratings = query.getResultList();
		double total = 0;
		int count = 0;
		double average = 0;
		for (Object rat:ratings) {
			if (rat instanceof Rating) {
				total += ((Rating)rat).getRating();
				count++;
			}
		}

		average = total / count;
		rating.setUserId(uid);
		rating.setRating(average);
		em.persist(rating);

		// User user = em.find(User.class, u.getId());
		// user.



		// user.setRating(rating);


		// Advert advert = em.find(Advert.class, id);
		// advert.setOfferAccepted(true);
		// update(advert);
	}

}
