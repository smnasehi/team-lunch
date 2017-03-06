package food.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import food.order.MealOrder.MealType;

public class Runner {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		List<Restaurant> restaurants= new ArrayList<>();
		int numberOfRestaurants = scanner.nextInt();
		scanner.nextLine();
		for (int i = 0; i < numberOfRestaurants; i++) {
			String line = scanner.nextLine();
			String[] elements = line.split(",");
			MealOrder dailyTotalMealOrder = new MealOrder();
			int totalOrderCount = Integer.parseInt(elements[2]);
			dailyTotalMealOrder.setMealCount(MealType.VEGETARIAN, Integer.parseInt(elements[3]));
			dailyTotalMealOrder.setMealCount(MealType.GLUTEN_FREE, Integer.parseInt(elements[4]));
			dailyTotalMealOrder.setMealCount(MealType.NUT_FREE, Integer.parseInt(elements[5]));
			dailyTotalMealOrder.setMealCount(MealType.FISH_FREE, Integer.parseInt(elements[6]));
			dailyTotalMealOrder.setMealCount(MealType.NORMAL, totalOrderCount - dailyTotalMealOrder.getTotalCount());
			restaurants.add(new Restaurant(elements[0], Integer.parseInt(elements[1]), dailyTotalMealOrder));
		}
		
		String line = scanner.nextLine();
		String[] elements = line.split(",");
		MealOrder lunchOrder = new MealOrder();
		int totalOrderCount = Integer.parseInt(elements[0]);
		lunchOrder.setMealCount(MealType.VEGETARIAN, Integer.parseInt(elements[1]));
		lunchOrder.setMealCount(MealType.GLUTEN_FREE, Integer.parseInt(elements[2]));
		lunchOrder.setMealCount(MealType.NUT_FREE, Integer.parseInt(elements[3]));
		lunchOrder.setMealCount(MealType.FISH_FREE, Integer.parseInt(elements[4]));
		lunchOrder.setMealCount(MealType.NORMAL, totalOrderCount - lunchOrder.getTotalCount());
		
		Map<Restaurant, MealOrder> result = new OrderProcessor(restaurants).process(lunchOrder);
		if (result.isEmpty()) {
			System.out.println("Cannot fulfill order!");
			return;
		}
		
		for (Map.Entry<Restaurant, MealOrder> entry : result.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
	}

}
