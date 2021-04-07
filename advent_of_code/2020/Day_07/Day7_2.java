import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Map;


class BagInfo {
	String bagName;
	int bagCount;

	BagInfo(String bagName, int bagCount) {
		this.bagCount = bagCount;
		this.bagName = bagName;
	}

	public String toString() {
		return bagName + "(" + bagCount + ")";
	}
} 


public class Day7_2 {
	public static final String FIRST_BAG = "shiny gold";
	public static void main(String[] args) {
		HashMap<String, LinkedList<BagInfo>> contains = readInput();

		// System.out.println(contains);
		
		System.out.println("N bags contained in " + FIRST_BAG + ": "
		                   + containsNBags(contains, FIRST_BAG));
	}

	private static int
	containsNBags(HashMap<String, LinkedList<BagInfo>> contains, String bag) {
		LinkedList<BagInfo> bagsContained = contains.get(bag);
		if (bagsContained == null) {
			return 0;
		}
		int sum = 0;
		for (BagInfo info: bagsContained) {
			sum += info.bagCount;
			sum += info.bagCount * containsNBags(contains, info.bagName);
		}
		return sum;
	}


	private static HashMap<String, LinkedList<BagInfo>> readInput() {
		HashMap<String, LinkedList<BagInfo>> contains = new HashMap<>();

		Scanner in = new Scanner(System.in);
		while (in.hasNextLine()) {
			String entry = in.nextLine().trim();
			
			// String entry = "light red bags contain 1 bright white bag, 2 muted yellow bags."
			// String [] split = { "light red ", " 1 bright white", " 2 muted yellow" }
			String[] split = entry.substring(0, entry.length() - 1)
			                      .replaceAll("( bag\\b| bags)", "")
			                      .split("(contain|,)");

			// System.out.println(Arrays.toString(split));

			LinkedList<BagInfo> bags = new LinkedList<>();
			if (!split[1].equals(" no other")) {
				for (int i = 1; i < split.length; i++) {
					Scanner info = new Scanner(split[i]);
					int bagCount = info.nextInt();
					String bagName = info.next() + " " + info.next();
					info.close();
					bags.add(new BagInfo(bagName, bagCount));
				}
			}
			contains.put(split[0].trim(), bags);
		}
		in.close();

		return contains;
	}
}
