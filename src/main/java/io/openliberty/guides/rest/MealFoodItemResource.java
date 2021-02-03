package io.openliberty.guides.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import net.plus.snowjest.cml.FoodItemSessionFactory;
import net.plus.snowjest.cml.model.MealFoodItem;

@ApplicationScoped
@Path("mealfooditem")
public class MealFoodItemResource {

	@Inject
	private FoodItemSessionFactory foodItemSessionFactory;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public MealFoodItem getMealFoodItem(@QueryParam("id") long id) {

		MealFoodItem mealFoodItem = foodItemSessionFactory.getSession()
				.get(MealFoodItem.class, id);
		return mealFoodItem;
	}

}
