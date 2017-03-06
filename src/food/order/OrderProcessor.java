package food.order;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderProcessor {
	private final List<Restaurant> orderedRestaurants;

	public OrderProcessor(Collection<Restaurant> restaurants) {
		orderedRestaurants = new ArrayList<>(restaurants);
		Collections.sort(orderedRestaurants);
	}
	
	public Map<Restaurant, MealOrder> process(MealOrder lunchOrder) {
		Map<Restaurant, MealOrder> result = new HashMap<>();
		MealOrder remainingOrder = lunchOrder.clone();
		for (Restaurant restaurant : orderedRestaurants) {
			MealOrder fullFilled = restaurant.fulfillOrder(remainingOrder);
			if (fullFilled.getTotalCount() > 0) {
				result.put(restaurant, fullFilled);
				remainingOrder = remainingOrder.calculateRemaining(fullFilled);
				if (remainingOrder.getTotalCount() == 0) {
					break;
				}
			}
		}
		if (remainingOrder.getTotalCount() > 0) {
			return new HashMap<>();
		}
		return result;
	}
}
