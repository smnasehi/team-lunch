package food.order;

import static org.junit.Assert.*;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import food.order.MealOrder.MealType;

public class MealOrderTest {
	@Rule
    public ExpectedException thrown= ExpectedException.none();
	
	@Test
	public void testMealOrder_noCountSet_returnsZeroForAnyType() {
		MealOrder mealOrder = new MealOrder();
		for (MealType mealType : MealType.values()) {
			assertEquals(0, mealOrder.getMealCount(mealType));
		}
		assertEquals(0, mealOrder.getTotalCount());
	}

	@Test
	public void testMealOrder_countSetForNormalType_returnsThatForNormalType() {
		MealOrder mealOrder = new MealOrder();
		mealOrder.setMealCount(MealType.NORMAL, 20);
		assertEquals(20, mealOrder.getMealCount(MealType.NORMAL));
		assertEquals(0, mealOrder.getMealCount(MealType.VEGETARIAN));
		assertEquals(0, mealOrder.getMealCount(MealType.NUT_FREE));
		assertEquals(0, mealOrder.getMealCount(MealType.FISH_FREE));
		assertEquals(20, mealOrder.getTotalCount());
	}
	
	@Test
	public void testMealOrder_countSetForNormalAndVegetarianTypes_returnsThoseValues() {
		MealOrder mealOrder = new MealOrder();
		mealOrder.setMealCount(MealType.NORMAL, 20);
		mealOrder.setMealCount(MealType.VEGETARIAN, 7);
		assertEquals(20, mealOrder.getMealCount(MealType.NORMAL));
		assertEquals(7, mealOrder.getMealCount(MealType.VEGETARIAN));
		assertEquals(0, mealOrder.getMealCount(MealType.NUT_FREE));
		assertEquals(0, mealOrder.getMealCount(MealType.FISH_FREE));
		assertEquals(27, mealOrder.getTotalCount());
	}
	
	@Test
	public void testMealOrder_countSetForNormalTypeTwice_returnsTheLatter() {
		MealOrder mealOrder = new MealOrder();
		mealOrder.setMealCount(MealType.NORMAL, 20);
		mealOrder.setMealCount(MealType.NORMAL, 30);
		assertEquals(30, mealOrder.getMealCount(MealType.NORMAL));
		assertEquals(0, mealOrder.getMealCount(MealType.VEGETARIAN));
		assertEquals(0, mealOrder.getMealCount(MealType.NUT_FREE));
		assertEquals(0, mealOrder.getMealCount(MealType.FISH_FREE));
		assertEquals(30, mealOrder.getTotalCount());
	}
	
	@Test
	public void testMealOrder_countSetSomeTypesToZero_returnsZero() {
		MealOrder mealOrder = new MealOrder();
		mealOrder.setMealCount(MealType.NORMAL, 20);
		mealOrder.setMealCount(MealType.VEGETARIAN, 0);
		mealOrder.setMealCount(MealType.NUT_FREE, 0);
		mealOrder.setMealCount(MealType.FISH_FREE, 3);
		assertEquals(20, mealOrder.getMealCount(MealType.NORMAL));
		assertEquals(0, mealOrder.getMealCount(MealType.VEGETARIAN));
		assertEquals(0, mealOrder.getMealCount(MealType.NUT_FREE));
		assertEquals(3, mealOrder.getMealCount(MealType.FISH_FREE));
		assertEquals(23, mealOrder.getTotalCount());
	}
	
	@Test
	public void testMealOrder_countSetToNegativeValue_throwsException() {
		MealOrder mealOrder = new MealOrder();
		
		thrown.expect(RuntimeException.class);
		thrown.expectMessage("Negative number of meals is not allowed");
		mealOrder.setMealCount(MealType.NORMAL, -1);
	}
	
	@Test
	public void testFulfillOrder_onlyNormalMeals_dailyMealSimilarToLunchOrder_returnsLunchOrder() {
		MealOrder dailyMealOrder = new MealOrder();
		dailyMealOrder.setMealCount(MealType.NORMAL, 20);
		
		MealOrder order = new MealOrder();
		order.setMealCount(MealType.NORMAL, 20);
		
		assertEquals(order, dailyMealOrder.fulfillOrder(order));
	}
	
	@Test
	public void testFulfillOrder_onlyNormalMeals_dailyMealLessThanLunchOrder_returnsDailyMeal() {
		MealOrder dailyMealOrder = new MealOrder();
		dailyMealOrder.setMealCount(MealType.NORMAL, 20);
		
		MealOrder order = new MealOrder();
		order.setMealCount(MealType.NORMAL, 30);
		
		assertEquals(dailyMealOrder, dailyMealOrder.fulfillOrder(order));
	}
	
	@Test
	public void testFulfillOrder_onlyNormalMeals_dailyMealGreaterThanLunchOrder_returnsLunchOrder() {
		MealOrder dailyMealOrder = new MealOrder();
		dailyMealOrder.setMealCount(MealType.NORMAL, 40);
		
		MealOrder order = new MealOrder();
		order.setMealCount(MealType.NORMAL, 30);
		
		assertEquals(order, dailyMealOrder.fulfillOrder(order));
	}
	
	@Test
	public void testFulfillOrder_differentTypesOfMeals_returnsMinimumOfLunchOrderAndDailyMealCount() {
		MealOrder dailyMealOrder = new MealOrder();
		dailyMealOrder.setMealCount(MealType.NORMAL, 40);
		dailyMealOrder.setMealCount(MealType.VEGETARIAN, 10);
		dailyMealOrder.setMealCount(MealType.NUT_FREE, 8);
		dailyMealOrder.setMealCount(MealType.FISH_FREE, 2);
		
		MealOrder order = new MealOrder();
		order.setMealCount(MealType.NORMAL, 30);
		order.setMealCount(MealType.VEGETARIAN, 15);
		order.setMealCount(MealType.NUT_FREE, 5);
		order.setMealCount(MealType.FISH_FREE, 5);
		
		MealOrder expected = new MealOrder();
		expected.setMealCount(MealType.NORMAL, 30);
		expected.setMealCount(MealType.VEGETARIAN, 10);
		expected.setMealCount(MealType.NUT_FREE, 5);
		expected.setMealCount(MealType.FISH_FREE, 2);
		
		assertEquals(expected, dailyMealOrder.fulfillOrder(order));
	}
	
	@Test
	public void testFulfillOrder_differentTypesOfMeals_someTypesDontExist_returnsMinimumOfLunchOrderAndDailyMealCountWhenBothExist() {
		MealOrder dailyMealOrder = new MealOrder();
		dailyMealOrder.setMealCount(MealType.NORMAL, 40);
		dailyMealOrder.setMealCount(MealType.VEGETARIAN, 10);
		dailyMealOrder.setMealCount(MealType.FISH_FREE, 2);
		
		MealOrder order = new MealOrder();
		order.setMealCount(MealType.NORMAL, 30);
		order.setMealCount(MealType.VEGETARIAN, 15);
		order.setMealCount(MealType.NUT_FREE, 5);
		
		MealOrder expected = new MealOrder();
		expected.setMealCount(MealType.NORMAL, 30);
		expected.setMealCount(MealType.VEGETARIAN, 10);
		
		assertEquals(expected, dailyMealOrder.fulfillOrder(order));
	}
	
	@Test
	public void testCalculateRemaining_sameOrdersWithOnlyNormalMeals_returnsOrderWithZeroTotalCount() {
		MealOrder order1 = new MealOrder();
		order1.setMealCount(MealType.NORMAL, 30);
		
		MealOrder order2 = new MealOrder();
		order2.setMealCount(MealType.NORMAL, 30);
		
		assertEquals(new MealOrder(), order1.calculateRemaining(order2));
	}
	
	@Test
	public void testCalculateRemaining_ordersWithOnlyNormalMeals_returnsOrderWithDifference() {
		MealOrder order1 = new MealOrder();
		order1.setMealCount(MealType.NORMAL, 30);
		
		MealOrder order2 = new MealOrder();
		order2.setMealCount(MealType.NORMAL, 20);
		
		MealOrder expected = new MealOrder();
		expected.setMealCount(MealType.NORMAL, 10);
		assertEquals(expected, order1.calculateRemaining(order2));
	}
	
	@Test
	public void testCalculateRemaining_sameOrdersContainingDifferentTypeOfMeals_returnsOrderWithZeroTotalCount() {
		MealOrder order1 = new MealOrder();
		order1.setMealCount(MealType.NORMAL, 30);
		order1.setMealCount(MealType.VEGETARIAN, 10);
		order1.setMealCount(MealType.NUT_FREE, 5);
		order1.setMealCount(MealType.FISH_FREE, 7);
		
		MealOrder order2 = new MealOrder();
		order2.setMealCount(MealType.NORMAL, 30);
		order2.setMealCount(MealType.VEGETARIAN, 10);
		order2.setMealCount(MealType.NUT_FREE, 5);
		order2.setMealCount(MealType.FISH_FREE, 7);
		
		assertEquals(new MealOrder(), order1.calculateRemaining(order2));
	}
	
	@Test
	public void testCalculateRemaining_differentOrdersContainingDifferentTypeOfMeals_allcountsAreGreaterInFirstOne_returnsOrderWithCountEqualsDifference() {
		MealOrder order1 = new MealOrder();
		order1.setMealCount(MealType.NORMAL, 30);
		order1.setMealCount(MealType.VEGETARIAN, 10);
		order1.setMealCount(MealType.NUT_FREE, 5);
		order1.setMealCount(MealType.FISH_FREE, 7);
		
		MealOrder order2 = new MealOrder();
		order2.setMealCount(MealType.NORMAL, 20);
		order2.setMealCount(MealType.VEGETARIAN, 10);
		order2.setMealCount(MealType.NUT_FREE, 3);
		order2.setMealCount(MealType.FISH_FREE, 3);
		
		MealOrder expected = new MealOrder();
		expected.setMealCount(MealType.NORMAL, 10);
		expected.setMealCount(MealType.NUT_FREE, 2);
		expected.setMealCount(MealType.FISH_FREE, 4);
		assertEquals(expected, order1.calculateRemaining(order2));
	}
	
	@Test
	public void testCalculateRemaining_differentOrdersContainingDifferentTypeOfMeals_notAllCountsAreGreaterInFirstOne_throwsException() {
		MealOrder order1 = new MealOrder();
		order1.setMealCount(MealType.NORMAL, 30);
		order1.setMealCount(MealType.VEGETARIAN, 10);
		
		MealOrder order2 = new MealOrder();
		order2.setMealCount(MealType.NORMAL, 40);
		order2.setMealCount(MealType.VEGETARIAN, 10);

		thrown.expect(RuntimeException.class);
		thrown.expectMessage("Cannot calculate remaining");
		order1.calculateRemaining(order2);
	}
	
}
