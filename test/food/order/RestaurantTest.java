package food.order;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import food.order.MealOrder.MealType;

public class RestaurantTest {

	@Test
	public void testOrdering_restaurantWithHigherRateGoesFirst() {
		MealOrder mealOrder1 = new MealOrder();
		mealOrder1.setMealCount(MealType.NORMAL, 10);
		Restaurant restaurant1 = new Restaurant("A", 5, mealOrder1);
		MealOrder mealOrder2 = new MealOrder();
		mealOrder2.setMealCount(MealType.NORMAL, 10);
		Restaurant restaurant2 = new Restaurant("B", 4, mealOrder2);
		List<Restaurant> restaurants = new ArrayList<>();
		restaurants.add(restaurant2);
		restaurants.add(restaurant1);
		

		Collections.sort(restaurants);
		assertEquals(restaurant1, restaurants.get(0));
		assertEquals(restaurant2, restaurants.get(1));
	}
	
	@Test
	public void testOrdering_restaurantWithHigherTotalGoesFirstWhenRatesAreSame() {
		MealOrder mealOrder1 = new MealOrder();
		mealOrder1.setMealCount(MealType.NORMAL, 10);
		Restaurant restaurant1 = new Restaurant("A", 5, mealOrder1);
		MealOrder mealOrder2 = new MealOrder();
		mealOrder2.setMealCount(MealType.NORMAL, 10);
		Restaurant restaurant2 = new Restaurant("B", 4, mealOrder2);
		MealOrder mealOrder3 = new MealOrder();
		mealOrder3.setMealCount(MealType.NORMAL, 20);
		Restaurant restaurant3 = new Restaurant("C", 4, mealOrder3);
		List<Restaurant> restaurants = new ArrayList<>();
		restaurants.add(restaurant2);
		restaurants.add(restaurant3);
		restaurants.add(restaurant1);
		

		Collections.sort(restaurants);
		assertEquals(restaurant1, restaurants.get(0));
		assertEquals(restaurant3, restaurants.get(1));
		assertEquals(restaurant2, restaurants.get(2));
	}

}
