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

	@EJB
	private AdvertDAO advertDao = new AdvertDAO();

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
		
		
		int rating = Integer.parseInt((String)req.get("rating"));
		String userId = (String) req.get("buyerId");
		int advertId = (Integer) req.get("advertId");
		String userType = (String) req.get("userType");
		
		

		userDao.leaveRating(userId, rating);
		advertDao.toggleRating(userId, advertId, userType);
		return Response.status(200).build(); 
	}

	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAllUsers() {
		List<User> users = userDao.findAllUsers();
		return Response.status(200).entity(users).build(); 
	}
}
