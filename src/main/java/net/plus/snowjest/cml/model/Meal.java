package net.plus.snowjest.cml.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "meal")
public class Meal implements Serializable {

	private static final long serialVersionUID = -2579377483853572289L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long mealId;

	private Date mealDateTime;

	@OneToMany(targetEntity = MealFoodItem.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<MealFoodItem> mealFoodItem;

	public Long getMealId() {
		return mealId;
	}

	public void setMealId(Long mealId) {
		this.mealId = mealId;
	}

	public Date getMealDateTime() {
		return mealDateTime;
	}

	public void setMealDateTime(Date mealDateTime) {
		this.mealDateTime = mealDateTime;
	}

	public Set<MealFoodItem> getMealFoodItems() {
		return mealFoodItem;
	}
}
