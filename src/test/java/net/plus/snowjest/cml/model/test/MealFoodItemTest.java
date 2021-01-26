package net.plus.snowjest.cml.model.test;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.plus.snowjest.cml.FoodItemSessionFactory;
import net.plus.snowjest.cml.model.FoodItem;
import net.plus.snowjest.cml.model.Meal;
import net.plus.snowjest.cml.model.MealFoodItem;

public class MealFoodItemTest {

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
		session.beginTransaction();
		FoodItem foodItem = session.find(FoodItem.class, "Moss");
		if (foodItem == null) {
			foodItem = new FoodItem();
			foodItem.setName("Moss");
			session.save(foodItem);
		}
		Meal meal = new Meal();
		MealFoodItem mealFoodItem = new MealFoodItem();
		mealFoodItem.setMeal(meal);
		mealFoodItem.setFoodItem(foodItem);
		session.save(meal);
		session.save(mealFoodItem);
		session.getTransaction().commit();
	}

}
