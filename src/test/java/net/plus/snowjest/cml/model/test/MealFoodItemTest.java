package net.plus.snowjest.cml.model.test;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.plus.snowjest.cml.FoodItemSessionFactory;

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
	}

}
