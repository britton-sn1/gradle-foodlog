package net.plus.snowjest.cml.model.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.plus.snowjest.cml.FoodItemSessionFactory;
import net.plus.snowjest.cml.model.FoodItem;

public class FoodItemTest {

	private Session session;
	static private final String FOOD_ITEM_NAME = "Pitted Kalamata Olives";
	static private final double CARBS = 0.5;
	static private final double FATS = 21.4;
	static private final double PROTEIN = 1.6;
	static private final double SATURATES = 2.7;
	static private final double SODIUM = 3.18 * 0.4;
	static private final double SUGARS = 0.5;
	static private final double FIBRE = 3.2;
	static private final String UNITS = "100g";
	static private final boolean ERROR_IF_NOT_EXISTS = true;

	private FoodItemSessionFactory foodItemSessionFactory = new FoodItemSessionFactory();

	@BeforeEach
	public void setUp() throws Exception {
		session = foodItemSessionFactory.getSession();
		delete(ERROR_IF_NOT_EXISTS);
		FoodItem foodItem = session.get(FoodItem.class, FOOD_ITEM_NAME);
		assertNull(foodItem, "Food item not null");

	}

	@AfterEach
	public void tearDown() throws Exception {
		session.close();
	}

	@Test
	public void test() {

		create();
		read();
		update();
		read2();
		delete(!ERROR_IF_NOT_EXISTS);
		read3();
	}

	private void create() {
		session.beginTransaction();
		FoodItem foodItem = new FoodItem(FOOD_ITEM_NAME);
		foodItem.setCarbs(CARBS);
		foodItem.setFats(FATS);
		foodItem.setProtein(PROTEIN);
		foodItem.setSaturates(SATURATES);
		foodItem.setSodium(SODIUM);
		foodItem.setSugars(SUGARS);
		foodItem.setFibre(FIBRE);
		foodItem.setUnits(UNITS);
		session.persist(foodItem);
		try {
			session.getTransaction().commit();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void read() {
		FoodItem foodItem = session.get(FoodItem.class, FOOD_ITEM_NAME);
		assertEquals(CARBS, foodItem.getCarbs(), "Inccorect carbs value");
		assertEquals(FATS, foodItem.getFats(), "Inccorect fats value");
		assertEquals(PROTEIN, foodItem.getProtein(), "Inccorect protein value");
		assertEquals(SATURATES, foodItem.getSaturates(),
				"Inccorect saturates value");
		assertEquals(SODIUM, foodItem.getSodium(), "Inccorect sodium value");
		assertEquals(SUGARS, foodItem.getSugars(), "Inccorect sugars value");
		assertEquals(FIBRE, foodItem.getFibre(), "Inccorect fibre value");
		assertEquals(UNITS, foodItem.getUnits(), "Inccorect units value");
	}

	private void update() {
		session.beginTransaction();
		FoodItem foodItem = session.get(FoodItem.class, FOOD_ITEM_NAME);
		foodItem.setProtein(PROTEIN * 2);
		session.getTransaction().commit();
	}

	private void read2() {
		FoodItem foodItem = session.get(FoodItem.class, FOOD_ITEM_NAME);

		assertEquals(CARBS, foodItem.getCarbs(), "Inccorect carbs value");
		assertEquals(FATS, foodItem.getFats(), "Inccorect fats value");
		assertEquals(PROTEIN * 2, foodItem.getProtein(),
				"Inccorect protein value");
		assertEquals(SATURATES, foodItem.getSaturates(),
				"Inccorect saturates value");
		assertEquals(SODIUM, foodItem.getSodium(), "Inccorect sodium value");
		assertEquals(SUGARS, foodItem.getSugars(), "Inccorect sugars value");
		assertEquals(FIBRE, foodItem.getFibre(), "Inccorect fibre value");
		assertEquals(UNITS, foodItem.getUnits(), "Inccorect units value");

	}

	private void delete(boolean ignoreIfNull) {
		FoodItem foodItem = session.get(FoodItem.class, FOOD_ITEM_NAME);
		if (ignoreIfNull && foodItem == null) {
			return;
		}

		session.beginTransaction();
		session.delete(foodItem);
		session.getTransaction().commit();

	}

	private void read3() {
		FoodItem foodItem = session.get(FoodItem.class, FOOD_ITEM_NAME);
		assertNull(foodItem, "Food item not null");
	}

}
