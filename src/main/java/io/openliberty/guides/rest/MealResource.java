package io.openliberty.guides.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import net.plus.snowjest.cml.FoodItemSessionFactory;
import net.plus.snowjest.cml.model.Meal;

@ApplicationScoped
@Path("meal")
public class MealResource {

	@Inject
	private FoodItemSessionFactory foodItemSessionFactory;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Meal getMeal(@QueryParam("id") long mealId) {

		Meal meal = foodItemSessionFactory.getSession().get(Meal.class, mealId);
		return meal;
	}

}
