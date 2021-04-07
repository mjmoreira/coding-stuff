import java.util.ArrayList;
import java.util.Scanner;

public class Day9_2 {
	private static final int NUMBER_PREVIOUS = 25;

	public static void
	main(String[] args) {
		ArrayList<Integer> numbers = new ArrayList<>();

		int answer1 = 0;
		boolean foundAnswer1 = false;
		Scanner in = new Scanner(System.in);
		while (in.hasNextInt() && !foundAnswer1) {
			Integer newNumber = Integer.valueOf(in.nextInt());
			if (numbers.size() >= NUMBER_PREVIOUS
			    && !checkNumber(numbers, newNumber)) {
				answer1 = newNumber.intValue();
				foundAnswer1 = true;
			}
			numbers.add(newNumber);
		}
		in.close();

		if (!foundAnswer1) {
			System.out.println("No number matching the conditions was found!");
			return;
		}
		System.out.println("Answer 1: " + answer1 + " is not a sum of two of "
						   + "the previous " + NUMBER_PREVIOUS + " numbers.");

		// find the interval of values which sum to "answer1"
		int firstIndex = 0;
		int lastIndex = 0;
		boolean indicesFound = false;
		for (int i = 0; i < numbers.size() && !indicesFound; i++) {
			int sum = numbers.get(i);
			for (int j = i + 1; j < numbers.size(); j++) {
				sum += numbers.get(j);
				if (sum == answer1) {
					System.out.println("Answer 2: first = " + numbers.get(i)
					                   + ", last = " + numbers.get(j) + "."
					                   + " Indices: " + i + "->" + j + ".");
					firstIndex = i;
					lastIndex = j;
					indicesFound = true;
					break;
				}
				else if (sum > answer1) { break; }
			}
		}

		if (!indicesFound) {
			System.out.println("Indices not found!");
			return;
		}

		// find the largest and the smallest value in the interval
		int smallest = numbers.get(firstIndex);
		int largest = smallest;
		for (int i = firstIndex + 1; i <= lastIndex; i++) {
			if (numbers.get(i) < smallest) {
				smallest = numbers.get(i);
			}
			else if (numbers.get(i) > largest) {
				largest = numbers.get(i);
			}
		}
		System.out.println("smallest = " + smallest + ", largest = " + largest);
		System.out.println("sum = " + (smallest + largest));
	}

	private static boolean
	checkNumber(ArrayList<Integer> numbers, Integer newNumber) {
		int firstPrevious = numbers.size() - NUMBER_PREVIOUS;
		for (int i = firstPrevious; i < numbers.size() - 1; i++) {
			for (int j = i + 1; j < numbers.size(); j++) {
				if (numbers.get(i) + numbers.get(j) == newNumber) {
					return true;
				}
			}
		}
		return false;
	}
}
