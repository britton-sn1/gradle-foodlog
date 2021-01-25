package net.plus.snowjest.cml.model.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.plus.snowjest.cml.FoodItemSessionFactory;
import net.plus.snowjest.cml.model.Meal;

public class MealTest {

	private static final long MEAL_DATE_1 = 32979238929L;
	private static final long MEAL_DATE_2 = 74838328392L;
	private FoodItemSessionFactory foodItemSessionFactory = new FoodItemSessionFactory();
	private Session session;

	@BeforeEach
	public void setUp() throws Exception {
		session = foodItemSessionFactory.getSession();
	}

	@AfterEach
	public void tearDown() throws Exception {
		session.close();
	}

	@Test
	public void test() {
		Serializable mealId = create();
		read(mealId, MEAL_DATE_1);
		update(mealId);
		read(mealId, MEAL_DATE_2);
		delete(mealId);
		read(mealId);
	}

	private void read(Serializable mealId) {
		Meal meal = session.get(Meal.class, mealId);
		assertNull(meal, "meal not deleted");
	}

	private void delete(Serializable mealId) {
		session.beginTransaction();
		Meal meal = session.get(Meal.class, mealId);
		session.delete(meal);
		session.getTransaction().commit();
	}

	private void update(Serializable mealId) {
		session.beginTransaction();
		Meal meal = session.get(Meal.class, mealId);
		meal.setMealDateTime(new Date(MEAL_DATE_2));
		session.getTransaction().commit();
	}

	private void read(Serializable mealId, Serializable expectedDate) {
		Meal meal = session.get(Meal.class, mealId);
		assertEquals(mealId, meal.getMealId(), "Incorrect meal id");
		assertEquals(expectedDate, meal.getMealDateTime().getTime(),
				"Inccorect Date");
	}

	private Serializable create() {
		session.beginTransaction();
		Meal meal = new Meal();
		meal.setMealDateTime(new Date(MEAL_DATE_1));
		Serializable id = session.save(meal);
		session.getTransaction().commit();
		return id;
	}

}
