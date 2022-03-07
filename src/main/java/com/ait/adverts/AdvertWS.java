package com.ait.adverts;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/adverts")
@Stateless
@LocalBean
public class AdvertWS {
	
	@EJB
	private AdvertDAO advertDao = new AdvertDAO();
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response findAll() {
		List<Advert> adverts = advertDao.getAllAdverts();
		return Response.status(200).entity(adverts).build();
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/{id}")
	public Response findAdvertById(@PathParam("id") int id) {
		Advert advert = advertDao.getAdvert(id);
		return Response.status(200).entity(advert).build();
	}
	
	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	public Response saveAdvert(Advert advert) {
		advertDao.save(advert);
		return Response.status(201).entity(advert).build();
	}
	
	@PUT
	@Path("/{id}")
	@Consumes("application/json")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateAdvert(Advert advert) {
		advertDao.update(advert);
		return Response.status(200).entity(advert).build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response deleteAdvert(@PathParam("id") int id) {
		advertDao.delete(id);
		return Response.status(204).build();
	}
	
	@GET @Path("/search/{query}")
	@Produces({ MediaType.APPLICATION_JSON,  MediaType.APPLICATION_XML })
	public List<Advert> findByKeyword(@PathParam("query") String query) {
		return advertDao.getAdvertsByKeyword(query);
	}
	
}
