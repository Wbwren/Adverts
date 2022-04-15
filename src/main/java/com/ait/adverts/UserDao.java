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
			if(us.getEmail().equals(user.getEmail()) && us.getHashValue().equals(user.getHashValue())) {
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
		User user = em.find(User.class, uid);

		double ratingTotal = user.getRatingTotal();
		double newRating = ratingTotal + r;
		user.setRatingTotal(newRating);
		user.setNumberOfRatings(user.getNumberOfRatings() + 1);
		em.merge(user);

	}

}
