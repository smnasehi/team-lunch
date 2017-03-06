package food.order;
import java.util.Comparator;
import java.util.Map;

import food.order.MealOrder.MealType;

public class Restaurant implements Comparable<Restaurant> {	
	private String name;
	private int rating;
	private MealOrder dailyTotalMealOrder;
	
	public Restaurant(String name, int rating, MealOrder dailyTotalMealOrder) {
		this.name = name;
		this.rating = rating;
		this.dailyTotalMealOrder = dailyTotalMealOrder;
	}
	
	public MealOrder fulfillOrder(MealOrder order) {
		return dailyTotalMealOrder.fulfillOrder(order);
	}

	@Override
	public int compareTo(Restaurant other) {
		if (other.rating != this.rating) {
			return other.rating - this.rating;
		}
		return other.dailyTotalMealOrder.getTotalCount() - this.dailyTotalMealOrder.getTotalCount(); 
	}

	@Override
	public String toString() {
		return "Restaurant [name=" + name + ", rating=" + rating + "]";
	}

	
}
