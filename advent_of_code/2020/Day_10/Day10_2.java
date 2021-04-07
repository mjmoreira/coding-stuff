import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Day10_2 {
	public static void main(String[] args) {
		ArrayList<Long> adapters = new ArrayList<>();
		
		Scanner in = new Scanner(System.in);
		while (in.hasNextInt()) {
			adapters.add(Long.valueOf(in.nextInt()));
		}
		in.close();

		adapters.add(0l); // the wall port, to make things easier.
		adapters.sort(null);
		System.out.println(adapters);

		HashMap<Long, Long> storage = new HashMap<>();
		System.out.println(
			arrangements(adapters, storage,
			             adapters.get(adapters.size() - 1).intValue()));
	}

	private static long
	arrangements(ArrayList<Long> adapters,
	             HashMap<Long, Long> storage,
	             long start) {
		// memoization of the results for each.
		// The quantity of different ways the current can be done is equal to
		// the sum of the different ways that each of the possible predecessors
		// of the current can be reached.

		if (start == 0l) {
			return 1l;
		}

		long sum = 0l;
		ArrayList<Long> options = options(adapters, start);
		for (Long v: options) {
			if (storage.containsKey(v)) {
				sum += storage.get(v);
			}
			else {
				Long value = arrangements(adapters, storage, v);
				storage.put(v, value);
				sum += value;
			}
		}
		return sum;
	}

	// List the values that are possible to be the previous value in the chain
	// so that the "value" parameter can be used in the chain.
	private static ArrayList<Long>
	options(ArrayList<Long> adapters, long value) {
		int index = adapters.indexOf(Long.valueOf(value));
		ArrayList<Long> values = new ArrayList<>();
		for (int i = index - 1; i >= 0; i--) {
			if (adapters.get(index) - adapters.get(i) <= 3) {
				values.add(adapters.get(i));
			}
		}
		return values;
	}
}
