package com.ait.adverts;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Produces; 
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


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
		System.out.println("Saving user in db: "+user);
		List<User> users = userDao.findAllUsers();
		for(User u : users) {
			if(user.getUserId().equals(u.getUserId())) {
				return Response.status(409).build();
			}
		}
		System.out.println("Saving user now..");
		userDao.register(user);
		System.out.println("user saved, retruing 201...");
		return Response.status(201).entity(user).build();
	}
	
	@POST
    @Path("/login")
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
