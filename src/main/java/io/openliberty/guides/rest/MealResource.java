package io.openliberty.guides.rest;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;

import net.plus.snowjest.cml.FoodItemSessionFactory;
import net.plus.snowjest.cml.model.Meal;

@ApplicationScoped
@Path("meals")
public class MealResource {

	@Inject
	private FoodItemSessionFactory foodItemSessionFactory;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Meal> getMeals() {
		Session session = foodItemSessionFactory.getSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Meal> criteria = builder.createQuery(Meal.class);
		criteria.from(Meal.class);
		List<Meal> data = session.createQuery(criteria).getResultList();
		return data;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void addMeals(List<Meal> meals) {
		Session session = foodItemSessionFactory.getSession();
		session.beginTransaction();
		meals.stream().forEach(meal -> meal.setMealId(null));
		meals.stream().forEach(meal -> session.save(meal));
		session.getTransaction().commit();
	}
}
