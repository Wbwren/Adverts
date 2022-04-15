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


public class UserDaoTest {
	private EntityManager em;
    private Query query;
    private User user;
    private UserDao userDao;
    ArrayList<User> users;
    
    @BeforeEach
    void setUp() {
    	em = mock(EntityManager.class);
    	query = mock(Query.class);
    	user = new User();
    	user.setEmail("w@g.com");
    	user.setHashValue("123kdsjhf");
    	userDao = new UserDao();
    	userDao.setEntityManager(em);
    	users = new ArrayList<>();
    	users.add(user);
    }
    
    @Test
    void testUserDao() {
    	when(em.createQuery("SELECT u FROM User u")).thenReturn(query);
    	when(query.getResultList()).thenReturn(users);
    	assertEquals(user, userDao.login(user));
    	
    	
    	doNothing().when(em).persist(user);
    	assertTrue(userDao.register(user));
    	
    	when(em.createQuery("SELECT u FROM User u")).thenReturn(query);
    	when(query.getResultList()).thenReturn(users);
    	assertEquals(users, userDao.findAllUsers());
    	
    	
    	when(em.find(User.class, "w@g.com")).thenReturn(user);
    	when(em.merge(user)).thenReturn(user);
    	userDao.leaveRating("w@g.com", 5);

    }
    
}
