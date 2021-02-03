package net.plus.snowjest.cml.model.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.plus.snowjest.cml.FoodItemSessionFactory;
import net.plus.snowjest.cml.model.FoodItem;
import net.plus.snowjest.cml.model.Meal;
import net.plus.snowjest.cml.model.MealFoodItem;

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
		Long mealId = create();
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

	private Meal read(Serializable mealId, Serializable expectedDate) {
		Meal meal = session.get(Meal.class, mealId);
		assertEquals(mealId, meal.getMealId(), "Incorrect meal id");
		assertEquals(expectedDate, meal.getMealDateTime().getTime(),
				"Inccorect Date");
		return meal;
	}

	private Long create() {
		session.beginTransaction();
		Meal meal = new Meal();
		meal.setMealDateTime(new Date(MEAL_DATE_1));
		Long id = (Long) session.save(meal);
		session.getTransaction().commit();
		return id;
	}

	@Test
	public void mealWithFoodItems() {
		Long mealId = create();
		Meal meal = read(mealId, MEAL_DATE_1);
		session.beginTransaction();

		// Create food items
		FoodItem fi1 = new FoodItem("FI1");
		FoodItem fi2 = new FoodItem("FI2");
		session.save(fi1);
		session.save(fi2);

		Set<MealFoodItem> mealFoodItems = new HashSet<>();
		// Create meal food items
		MealFoodItem mealFoodItem1 = new MealFoodItem();
		mealFoodItem1.setFoodItem(fi1);
		mealFoodItem1.setQuantity(Double.valueOf(101.1));
		mealFoodItems.add(mealFoodItem1);

		MealFoodItem mealFoodItem2 = new MealFoodItem();
		mealFoodItem2.setQuantity(Double.valueOf(102.2));
		mealFoodItem2.setFoodItem(fi2);
		mealFoodItems.add(mealFoodItem2);

		MealFoodItem mealFoodItem3 = new MealFoodItem();
		mealFoodItem3.setQuantity(Double.valueOf(103.3));
		mealFoodItem3.setFoodItem(fi1);
		mealFoodItems.add(mealFoodItem3);

		meal.setMealFoodItem(mealFoodItems);
		session.save(meal);
		session.getTransaction().commit();

		// Re-read meal from DB
		meal = read(mealId, MEAL_DATE_1);

		assertEquals(3, meal.getMealFoodItem().size(),
				"Incorrect number of meal food items");
		mealFoodItems = meal.getMealFoodItem();

		assertTrue(checkForFoodItem(mealFoodItems, fi1, 101.1),
				"Meal Food Item 1 missing");
		assertTrue(checkForFoodItem(mealFoodItems, fi2, 102.2),
				"Meal Food Item 2 missing");
		assertTrue(checkForFoodItem(mealFoodItems, fi1, 103.3),
				"Meal Food Item 3 missing");

		// Delete meal
		delete(mealId);

		// Delete fppd items
		session.beginTransaction();
		session.delete(fi1);
		session.delete(fi2);
		session.getTransaction().commit();

	}

	private boolean checkForFoodItem(Set<MealFoodItem> mealFoodItems,
			FoodItem fi, double quanity) {
		boolean found = false;
		for (MealFoodItem mfi : mealFoodItems) {
			if (mfi.getFoodItem().getName().equals(fi.getName())) {
				if (mfi.getQuantity().equals(Double.valueOf(quanity)))
					found = true;
			}
		}

		return found;
	}
}
