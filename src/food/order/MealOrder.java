package food.order;
import java.util.HashMap;
import java.util.Map;

import food.order.MealOrder.MealType;

public class MealOrder {
	public enum MealType {
		NORMAL,
		VEGETARIAN,
		GLUTEN_FREE,
		NUT_FREE,
		FISH_FREE
	}
	private Map<MealType, Integer> mealCounts = new HashMap<>();
	
	public void setMealCount(MealType mealType, int count) {
		if (count < 0) {
			throw new RuntimeException("Negative number of meals is not allowed");
		}
		mealCounts.put(mealType, count);
	}
	
	public int getMealCount(MealType mealType) {
		if (mealCounts.containsKey(mealType)) {
			return mealCounts.get(mealType);
		}
		return 0;
	}
	
	public int getTotalCount() {
		int total = 0;
		for (int count : mealCounts.values()) {
			total += count;
		}
		return total;
	}
	
	public MealOrder fulfillOrder(MealOrder order) {
		MealOrder result = new MealOrder();
		for (MealType mealType : MealType.values()) {
			if (order.getMealCount(mealType) > 0 &&  getMealCount(mealType) > 0) { 
					result.setMealCount(mealType, Math.min(order.getMealCount(mealType), 
									getMealCount(mealType)));
			}
		}
		return result;
	}
	
	public MealOrder calculateRemaining(MealOrder order) {
		MealOrder result = new MealOrder();
		for (MealType mealType : MealType.values()) {
			if (getMealCount(mealType) < order.getMealCount(mealType)) {
				throw new RuntimeException("Cannot calculate remaining");
			}
			
			if (getMealCount(mealType) > 0 && 
					getMealCount(mealType) > order.getMealCount(mealType)) {
				result.setMealCount(mealType, getMealCount(mealType) - order.getMealCount(mealType)); 
			}
		}
		return result;
	}

	@Override
	public String toString() {
		String result = "MealOrder [";
		for (MealType mealType: MealType.values()) {
			if (getMealCount(mealType) > 0) {
				result += mealType.name().toLowerCase() + ":" + getMealCount(mealType) + " ";
			}
		}
		return result + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mealCounts == null) ? 0 : mealCounts.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MealOrder other = (MealOrder) obj;
		if (mealCounts == null) {
			if (other.mealCounts != null)
				return false;
		} else if (!mealCounts.equals(other.mealCounts))
			return false;
		return true;
	}

	@Override
	public MealOrder clone() {
		MealOrder clone = new MealOrder();
		clone.mealCounts = new HashMap<>(this.mealCounts);
		return clone;
	}

	
}
