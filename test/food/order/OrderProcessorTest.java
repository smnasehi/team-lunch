package food.order;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import food.order.MealOrder.MealType;

public class OrderProcessorTest {

	@Test
	public void testProcess_oneResturant_returnsThat() {
		MealOrder dailyTotalMealOrder = new MealOrder();
		dailyTotalMealOrder.setMealCount(MealType.NORMAL, 20);
		Restaurant restaurant1 = new Restaurant("A", 5, dailyTotalMealOrder);
		List<Restaurant> restaurants = new ArrayList<>();
		restaurants.add(restaurant1);
		
		MealOrder lunchOrder = new MealOrder();
		lunchOrder.setMealCount(MealType.NORMAL, 10);
		
		Map<Restaurant, MealOrder> expected = new HashMap<>();
		expected.put(restaurant1, lunchOrder);
		assertEquals(expected, new OrderProcessor(restaurants).process(lunchOrder));
	}
	
	@Test
	public void testProcess_twoResturantsWithDifferentRatings_onlyNormalFoodOrdered_returnsListOfOrdersFromEach() {
		MealOrder dailyTotalMealOrder1 = new MealOrder();
		dailyTotalMealOrder1.setMealCount(MealType.NORMAL, 20);
		Restaurant restaurant1 = new Restaurant("A", 5, dailyTotalMealOrder1);
		MealOrder dailyTotalMealOrder2 = new MealOrder();
		dailyTotalMealOrder2.setMealCount(MealType.NORMAL, 30);
		Restaurant restaurant2 = new Restaurant("B", 4, dailyTotalMealOrder2);
		List<Restaurant> restaurants = new ArrayList<>();
		restaurants.add(restaurant2);
		restaurants.add(restaurant1);
		
		MealOrder lunchOrder = new MealOrder();
		lunchOrder.setMealCount(MealType.NORMAL, 35);
		
		Map<Restaurant, MealOrder> expected = new HashMap<>();
		MealOrder order1 = new MealOrder();
		order1.setMealCount(MealType.NORMAL, 20);
		MealOrder order2 = new MealOrder();
		order2.setMealCount(MealType.NORMAL, 15);
		expected.put(restaurant1, order1);
		expected.put(restaurant2, order2);
		
		assertEquals(expected, new OrderProcessor(restaurants).process(lunchOrder));
	}
	
	@Test
	public void testProcess_twoResturantsWithSameRating_onlyNormalFoodOrdered_returnsListOfOrdersFromEach() {
		MealOrder dailyTotalMealOrder1 = new MealOrder();
		dailyTotalMealOrder1.setMealCount(MealType.NORMAL, 20);
		Restaurant restaurant1 = new Restaurant("A", 4, dailyTotalMealOrder1);
		MealOrder dailyTotalMealOrder2 = new MealOrder();
		dailyTotalMealOrder2.setMealCount(MealType.NORMAL, 30);
		Restaurant restaurant2 = new Restaurant("B", 4, dailyTotalMealOrder2);
		List<Restaurant> restaurants = new ArrayList<>();
		restaurants.add(restaurant2);
		restaurants.add(restaurant1);
		
		MealOrder lunchOrder = new MealOrder();
		lunchOrder.setMealCount(MealType.NORMAL, 35);
		
		Map<Restaurant, MealOrder> expected = new HashMap<>();
		MealOrder order1 = new MealOrder();
		order1.setMealCount(MealType.NORMAL, 30);
		MealOrder order2 = new MealOrder();
		order2.setMealCount(MealType.NORMAL, 5);
		expected.put(restaurant2, order1);
		expected.put(restaurant1, order2);
		
		assertEquals(expected, new OrderProcessor(restaurants).process(lunchOrder));
	}
	
	@Test
	public void testProcess_twoResturantsWithDifferentRatings_onlyNormalFoodOrdered_totalOrderMoreThanWhatRestaurantsCanFulfill_returnsEmptyList() {
		MealOrder dailyTotalMealOrder1 = new MealOrder();
		dailyTotalMealOrder1.setMealCount(MealType.NORMAL, 20);
		Restaurant restaurant1 = new Restaurant("A", 5, dailyTotalMealOrder1);
		MealOrder dailyTotalMealOrder2 = new MealOrder();
		dailyTotalMealOrder2.setMealCount(MealType.NORMAL, 30);
		Restaurant restaurant2 = new Restaurant("B", 4, dailyTotalMealOrder2);
		List<Restaurant> restaurants = new ArrayList<>();
		restaurants.add(restaurant2);
		restaurants.add(restaurant1);
		
		MealOrder lunchOrder = new MealOrder();
		lunchOrder.setMealCount(MealType.NORMAL, 55);
		
		assertTrue(new OrderProcessor(restaurants).process(lunchOrder).isEmpty());
	}
	
	@Test
	public void testProcess_twoResturantsWithDifferentRatings_sameAsExampleInTheEmail_returnsListOfOrdersFromEach() {
		MealOrder dailyTotalMealOrder1 = new MealOrder();
		dailyTotalMealOrder1.setMealCount(MealType.NORMAL, 36);
		dailyTotalMealOrder1.setMealCount(MealType.VEGETARIAN, 4);
		Restaurant restaurant1 = new Restaurant("A", 5, dailyTotalMealOrder1);
		MealOrder dailyTotalMealOrder2 = new MealOrder();
		dailyTotalMealOrder2.setMealCount(MealType.NORMAL, 60);
		dailyTotalMealOrder2.setMealCount(MealType.VEGETARIAN, 20);
		dailyTotalMealOrder2.setMealCount(MealType.GLUTEN_FREE, 20);
		Restaurant restaurant2 = new Restaurant("B", 3, dailyTotalMealOrder2);
		List<Restaurant> restaurants = new ArrayList<>();
		restaurants.add(restaurant2);
		restaurants.add(restaurant1);
		
		MealOrder lunchOrder = new MealOrder();
		lunchOrder.setMealCount(MealType.NORMAL, 38);
		lunchOrder.setMealCount(MealType.VEGETARIAN, 5);
		lunchOrder.setMealCount(MealType.GLUTEN_FREE, 7);
		
		Map<Restaurant, MealOrder> expected = new HashMap<>();
		MealOrder order1 = new MealOrder();
		order1.setMealCount(MealType.NORMAL, 36);
		order1.setMealCount(MealType.VEGETARIAN, 4);
		MealOrder order2 = new MealOrder();
		order2.setMealCount(MealType.NORMAL, 2);
		order2.setMealCount(MealType.VEGETARIAN, 1);
		order2.setMealCount(MealType.GLUTEN_FREE, 7);
		expected.put(restaurant1, order1);
		expected.put(restaurant2, order2);
		
		assertEquals(expected, new OrderProcessor(restaurants).process(lunchOrder));
	}

	@Test
	public void testProcess_fourResturantsWithDifferentRatings_eachPrividesNormalAndOneSpecialTypeOfFood_returnsListOfOrdersFromEach() {
		MealOrder dailyTotalMealOrder1 = new MealOrder();
		dailyTotalMealOrder1.setMealCount(MealType.NORMAL, 20);
		dailyTotalMealOrder1.setMealCount(MealType. VEGETARIAN, 10);
		Restaurant restaurant1 = new Restaurant("A", 5, dailyTotalMealOrder1);
		MealOrder dailyTotalMealOrder2 = new MealOrder();
		dailyTotalMealOrder2.setMealCount(MealType.NORMAL, 30);
		dailyTotalMealOrder2.setMealCount(MealType.GLUTEN_FREE, 10);
		Restaurant restaurant2 = new Restaurant("B", 4, dailyTotalMealOrder2);
		MealOrder dailyTotalMealOrder3 = new MealOrder();
		dailyTotalMealOrder3.setMealCount(MealType.NORMAL, 40);
		dailyTotalMealOrder3.setMealCount(MealType. NUT_FREE, 10);
		Restaurant restaurant3 = new Restaurant("C", 4, dailyTotalMealOrder3);
		MealOrder dailyTotalMealOrder4 = new MealOrder();
		dailyTotalMealOrder4.setMealCount(MealType.NORMAL, 50);
		dailyTotalMealOrder4.setMealCount(MealType.FISH_FREE, 10);
		Restaurant restaurant4 = new Restaurant("D", 3, dailyTotalMealOrder4);
		List<Restaurant> restaurants = new ArrayList<>();
		restaurants.add(restaurant2);
		restaurants.add(restaurant4);
		restaurants.add(restaurant3);
		restaurants.add(restaurant1);
		
		MealOrder lunchOrder = new MealOrder();
		lunchOrder.setMealCount(MealType.NORMAL, 35);
		lunchOrder.setMealCount(MealType.VEGETARIAN, 5);
		lunchOrder.setMealCount(MealType.GLUTEN_FREE, 5);
		lunchOrder.setMealCount(MealType.NUT_FREE, 5);
		lunchOrder.setMealCount(MealType.FISH_FREE, 5);
		
		Map<Restaurant, MealOrder> expected = new HashMap<>();
		MealOrder order1 = new MealOrder();
		order1.setMealCount(MealType.NORMAL, 20);
		order1.setMealCount(MealType.VEGETARIAN, 5);
		MealOrder order2 = new MealOrder();
		order2.setMealCount(MealType.NORMAL, 15);
		order2.setMealCount(MealType.NUT_FREE, 5);
		MealOrder order3 = new MealOrder();
		order3.setMealCount(MealType.GLUTEN_FREE, 5);
		MealOrder order4 = new MealOrder();
		order4.setMealCount(MealType.FISH_FREE, 5);
		expected.put(restaurant1, order1);
		expected.put(restaurant3, order2);
		expected.put(restaurant2, order3);
		expected.put(restaurant4, order4);
		
		assertEquals(expected, new OrderProcessor(restaurants).process(lunchOrder));
	}
}
