package io.openliberty.guides.rest;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import net.plus.snowjest.cml.FoodItemSessionFactory;
import net.plus.snowjest.cml.model.Meal;

@ApplicationScoped
@Path("meals")
public class MealsResouce {

	@Inject
	private FoodItemSessionFactory foodItemSessionFactory;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Meal> getMeal() {

		List<Meal> meals = foodItemSessionFactory.getSession()
				.createQuery("SELECT a FROM Meal a", Meal.class).getResultList();

		return meals;
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMeal(@PathParam("id") long mealId) {

		Meal meal = foodItemSessionFactory.getSession().get(Meal.class, mealId);
		if (meal != null) {
			return Response.ok(meal).build();
		}

		return Response.status(Status.NOT_FOUND).build();
	}

}
