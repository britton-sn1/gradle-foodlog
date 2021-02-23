package io.openliberty.guides.rest;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hibernate.Session;

import net.plus.snowjest.cml.FoodItemSessionFactory;
import net.plus.snowjest.cml.model.FoodItem;

@ApplicationScoped
@Path("fooditems")
public class FoodItemsResource {

	@Inject
	private FoodItemSessionFactory foodItemSessionFactory;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<FoodItem> getFoodItems(
			@DefaultValue("0") @QueryParam("startPage") int startPage,
			@DefaultValue("30") @QueryParam("pageSize") int pageSize,
			@DefaultValue("") @QueryParam("filter") String filter) {

		List<FoodItem> foodItems = getFoodItems(filter);
		return foodItems.subList(startPage * pageSize,
				Math.min(foodItems.size(), (startPage + 1) * pageSize));
	}

	private List<FoodItem> getFoodItems(String filter) {

		String queryString = "SELECT a FROM FoodItem a";
		if (filter.trim().length() > 0) {
			queryString = queryString + " where lower(a.name) like :filter";
		}
		Query query = foodItemSessionFactory.getSession().createQuery(queryString,
				FoodItem.class);
		if (filter.trim().length() > 0) {
			query.setParameter("filter", "%" + filter.toLowerCase() + "%");
		}
		return query.getResultList();
	}

	@GET
	@Path("count")
	@Produces(MediaType.APPLICATION_JSON)
	public int getFoodItemsCount(
			@DefaultValue("") @QueryParam("filter") String filter) {

		return getFoodItems(filter).size();
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getFoodItem(@PathParam("id") long id) {
		FoodItem fi = foodItemSessionFactory.getSession().find(FoodItem.class,
				id);

		if (fi != null) {
			return Response.ok(fi).build();
		}

		return Response.status(Status.NOT_FOUND).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void postFoodItems(List<FoodItem> foodItems) {
		Session session = foodItemSessionFactory.getSession();
		session.beginTransaction();

		for (FoodItem foodItem : foodItems) {
			session.save(foodItem);
		}

		session.getTransaction().commit();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void putFoodItems(FoodItem foodItem) {
		FoodItem fi = foodItemSessionFactory.getSession().find(FoodItem.class,
				foodItem.getId());

		Session session = foodItemSessionFactory.getSession();
		session.beginTransaction();
		fi.update(foodItem);
		session.update(fi);
		session.getTransaction().commit();
	}

}
