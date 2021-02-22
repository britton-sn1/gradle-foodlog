package io.openliberty.guides.rest;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
	public List<FoodItem> getFoodItems() {

		List<FoodItem> foodItems = foodItemSessionFactory.getSession()
				.createQuery("SELECT a FROM FoodItem a", FoodItem.class)
				.getResultList();

		return foodItems;
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
