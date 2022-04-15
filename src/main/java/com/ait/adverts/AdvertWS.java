package com.ait.adverts;



import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
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

import org.apache.poi.hssf.record.cf.DataBarFormatting;




@Path("/adverts")
@Stateless
@LocalBean
public class AdvertWS {
	
	@EJB
	private AdvertDao advertDao = new AdvertDao();

	
	public void setAdvertDao(AdvertDao advertDao) {
		this.advertDao = advertDao;
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response findAll() {
		System.out.println("in ws");
		List<Advert> adverts = advertDao.getAllAdverts();
		System.out.println("returning: "+adverts);
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
    @Path("/post")
	@Produces({MediaType.APPLICATION_JSON})
	public Response saveAdvert(Advert advert) {

		advert.setDatePosted(Timestamp.from(Instant.now()));
		advertDao.save(advert);
		return Response.status(201).entity(advert).build();
	}

	@PUT
    @Produces({ MediaType.APPLICATION_JSON })
	@Path("/place-offer/")
	public Response placeOffer(LinkedHashMap request) {
		double offer = Double.parseDouble((String)request.get("offer"));
		int advertId = (int) request.get("advertId");
		String buyerId = (String) request.get("buyerId");

		if(advertDao.placeOffer(advertId, buyerId, offer)) {
			return Response.status(200).build();
		}
		return Response.status(409).build();
	}

	@PUT
    @Produces({ MediaType.APPLICATION_JSON })
	@Path("/withdraw-offer/{id}")
	public Response withdrawOffer(@PathParam("id") int id) {
		System.out.println("withdrawing offer on afdvert: "+id);
		advertDao.withdrawOffer(id);
		return Response.status(200).build();
	}


	@PUT
    @Produces({ MediaType.APPLICATION_JSON })
	@Path("/accept-offer/{id}")
	public Response acceptOffer(@PathParam("id") int id) {
		advertDao.acceptOffer(id);
		return Response.status(200).build();
	}

	

	@PUT
    @Produces({ MediaType.APPLICATION_JSON })
	@Path("/out-for-delivery/{id}")
	public Response markOutForDelivery(@PathParam("id") int id) {
		//advert.setDatePosted(LocalDate.of(2012, 02, 02));
		advertDao.markOutForDelivery(id);
		return Response.status(200).build();
	}
	
	@PUT
	@Path("/edit")
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
	
	@GET @Path("/search/{query}/{pricerange}/{warrantyincluded}")
	@Produces({ MediaType.APPLICATION_JSON,  MediaType.APPLICATION_XML })
	public List<Advert> findByKeyword(@PathParam("query") String query, 
	@PathParam("pricerange") double priceRange, @PathParam("warrantyincluded") boolean warrantyIncluded) {
		return advertDao.getAdvertsByKeyword(query, priceRange, warrantyIncluded);
	}

	@GET @Path("/search/seller/{userName}")
	@Produces({ MediaType.APPLICATION_JSON,  MediaType.APPLICATION_XML })
	public List<Advert> findBySeller(@PathParam("userName") String query) {
		return advertDao.getAdvertsBySeller(query);
	}

	@GET @Path("/search/buyer/{userName}")
	@Produces({ MediaType.APPLICATION_JSON,  MediaType.APPLICATION_XML })
	public List<Advert> findByBuyer(@PathParam("userName") String query) {
		return advertDao.getAdvertsByBuyer(query);
	}
	
}
