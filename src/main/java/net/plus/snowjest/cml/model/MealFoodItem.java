package net.plus.snowjest.cml.model;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class MealFoodItem {

	@ManyToOne
	@JoinColumn(name = "id", nullable = false)
	private Meal meal;

	@ManyToOne
	@JoinColumn(name = "name", nullable = false)
	private FoodItem foodItem;
}
