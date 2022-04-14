package com.ait.adverts;

import java.util.LinkedHashMap;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;


@Stateless
@LocalBean
@Path("/users")
public class UserService {
	
	@EJB
	private UserDao userDao = new UserDao();

	@POST
    @Path("/register")
	@Produces({MediaType.APPLICATION_JSON})
	public Response saveUser(User user) {
		List<User> users = userDao.findAllUsers();
		for(User u : users) {
			if(user.getEmail().equals(u.getEmail())) {
				return Response.status(409).build();
			}
		}
		System.out.println("here");
		System.out.println(user.getEmail());
		System.out.println(user.getUserType());
		userDao.register(user);
		return Response.status(201).entity(user).build();
	}
	
	@POST
    @Path("/login")
	@Produces({MediaType.APPLICATION_JSON})
	public Response loginUser(User user) {
		User userFound = userDao.login(user);
		if(userFound != null) {
			return Response.status(200).entity(userFound).build(); 
		} else {
			return Response.status(401).build();
		}
	}
	
	@PUT
    @Path("/rating")
	@Produces({MediaType.APPLICATION_JSON})
	public Response rateUser(LinkedHashMap req) {
		
		System.out.println(req.get("buyerId").getClass());
		System.out.println(req.get("rating").getClass());
		int rating = Integer.parseInt((String)req.get("rating"));
		String userId = (String) req.get("buyerId");
		

		userDao.leaveRating(userId, rating);
		System.out.println("here in user service");
		System.out.println(userId);
		System.out.println(rating);
		return Response.status(200).build(); 
	}

	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAllUsers() {
		List<User> users = userDao.findAllUsers();
		return Response.status(200).entity(users).build(); 
	}
}
