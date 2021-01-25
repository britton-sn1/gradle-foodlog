package net.plus.snowjest.cml.model.test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import net.plus.snowjest.cml.FoodItemSessionFactory;
import net.plus.snowjest.cml.model.FoodItem;
import net.plus.snowjest.cml.model.Meal;

public class MealTest {

	private Session session;
	static private final String FOOD_ITEM_NAME1 = "Pitted Kalamata Olives";
	static private final String FOOD_ITEM_NAME2 = "Cheader Cheese";
	static private final double CARBS = 0.5;
	static private final double FATS = 21.4;
	static private final double PROTEIN = 1.6;
	static private final double SATURATES = 2.7;
	static private final double SODIUM = 3.18 * 0.4;
	static private final double SUGARS = 0.5;
	static private final double FIBRE = 3.2;
	static private final String UNITS = "100g";

	@Autowired
	private FoodItemSessionFactory foodItemSessionFactory;
	
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
		int mealId = create();
		read(mealId);
		update(mealId);
		read2(mealId);
		delete(mealId);
		read3(mealId);
	}

	private int create() {
		session.beginTransaction();
		FoodItem foodItem = new FoodItem(FOOD_ITEM_NAME1);
		foodItem.setCarbs(CARBS);
		foodItem.setFats(FATS);
		foodItem.setProtein(PROTEIN);
		foodItem.setSaturates(SATURATES);
		foodItem.setSodium(SODIUM);
		foodItem.setSugars(SUGARS);
		foodItem.setFibre(FIBRE);
		foodItem.setUnits(UNITS);
		Meal meal = new Meal();
		List<FoodItem> foodItems = new ArrayList<>();
		foodItems.add(foodItem);
		meal.setFoodItens(foodItems);
		meal.setMealDateTime(new Date(19929L));
		session.persist(foodItem);
		try {
			session.getTransaction().commit();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return meal.getId();
	}

	private void read(int mealId) {
		Meal meal = (Meal) session.get(Meal.class, mealId);

		FoodItem foodItem = meal.getFoodItens().get(0);
		assertTrue(foodItem.getCarbs() == CARBS);
		assertTrue(foodItem.getFats() == FATS);
		assertTrue(foodItem.getProtein() == PROTEIN);
		assertTrue(foodItem.getSaturates() == SATURATES);
		assertTrue(foodItem.getSodium() == SODIUM);
		assertTrue(foodItem.getSugars() == SUGARS);
		assertTrue(foodItem.getFibre() == FIBRE);
		assertTrue(foodItem.getUnits().equals(UNITS));
	}

	private void update(int mealId) {
		session.beginTransaction();
		Meal meal = (Meal) session.get(Meal.class, mealId);

		FoodItem foodItem = new FoodItem(FOOD_ITEM_NAME1);
		foodItem.setCarbs(CARBS);
		foodItem.setFats(FATS);
		foodItem.setProtein(PROTEIN);
		foodItem.setSaturates(SATURATES);
		foodItem.setSodium(SODIUM);
		foodItem.setSugars(SUGARS);
		foodItem.setFibre(FIBRE);
		foodItem.setUnits(UNITS);

		meal.getFoodItens().add(foodItem);
		session.getTransaction().commit();
	}

	private void read2(int mealId) {

		Meal meal = (Meal) session.get(Meal.class, mealId);
		FoodItem foodItem = meal.getFoodItens().get(1);

		assertTrue(foodItem.getName() == FOOD_ITEM_NAME2);
	}

	private void delete(int mealId) {
		Meal meal = (Meal) session.get(Meal.class, mealId);
		session.beginTransaction();
		session.delete(meal);
		session.getTransaction().commit();

	}

	private void read3(int mealId) {
		Meal meal = (Meal) session.get(Meal.class, mealId);
		assertTrue(meal == null);
	}

}
