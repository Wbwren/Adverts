package com.ait.adverts;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.core.Response;


@Stateless
@LocalBean
public class UserService {
	
	@EJB
	private UserDao userDao = new UserDao();

	public Response saveUser(User user) {
		List<User> users = userDao.findAllUsers();
		for(User u : users) {
			if(user.getUserId().equals(u.getUserId())) {
				return Response.status(409).build();
			}
		}
		userDao.save(user);
		return Response.status(201).entity(user).build();
	}
	
	public Response loginUser(User user) {
		
		User userFound = userDao.login(user);
		
		if(userFound != null) {
			return Response.status(200).entity(userFound).build(); 
		} else {
			return Response.status(401).build();
		}
	}
	
	public Response getAllUsers() {
		List<User> users = userDao.findAllUsers();
		return Response.status(200).entity(users).build(); 
	}
}
