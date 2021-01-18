package net.plus.snowjest.cml.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Meal")
public class Meal {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@OneToMany(mappedBy = "name")
	private List<FoodItem> foodItens = new ArrayList<>();

	private Date mealDateTime;

	public List<FoodItem> getFoodItens() {
		return foodItens;
	}

	public void setFoodItens(List<FoodItem> foodItens) {
		this.foodItens = foodItens;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getMealDateTime() {
		return mealDateTime;
	}

	public void setMealDateTime(Date mealDateTime) {
		this.mealDateTime = mealDateTime;
	}

}
