import java.util.LinkedList;
import java.util.Scanner;

public class Day9_1 {
	private static final int NUMBER_PREVIOUS = 25;

	public static void
	main(String[] args) {
		LinkedList<Integer> previousNumbers = new LinkedList<>();

		Scanner in = new Scanner(System.in);
		while (in.hasNextInt()) {
			Integer newNumber = Integer.valueOf(in.nextInt());
			if (previousNumbers.size() == NUMBER_PREVIOUS) {
				if (!checkNumber(previousNumbers, newNumber)) {
					System.out.println("Answer: " + newNumber + " is not a sum "
									   + "of two of the previous "
									   + NUMBER_PREVIOUS + " numbers.");
					// no need to check the rest after this. could just exit.
				}
				previousNumbers.removeFirst();
			}
			previousNumbers.addLast(newNumber);
		}
		in.close();
	}

	private static boolean
	checkNumber(LinkedList<Integer> numbers, Integer newNumber) {
		if (numbers.size() != NUMBER_PREVIOUS) {
			System.out.println("BAD: numbers list size is " + numbers.size());
		}
		Integer[] num = new Integer[NUMBER_PREVIOUS];
		numbers.toArray(num);
		for (int i = 0; i < num.length - 1; i++) {
			for (int j = i + 1; j < num.length; j++) {
				if (num[i].intValue() + num[j].intValue() == newNumber.intValue()) {
					return true;
				}
			}
		}
		return false;
	}
}