package io.openliberty.guides.rest;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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

}
