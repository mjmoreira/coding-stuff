import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Map;

public class Day7_1 {
	public static void main(String[] args) {
		HashMap<String, LinkedList<String>> contains = readInput();

		// isContained(K,V): the bag K is contained by bag V
		HashMap<String, HashSet<String>> isContained = 
			generateIsContained(contains);
		System.out.println(isContained);

		// The set of all bags X that contain the bag of interest Y or any of
		// the bags Z that contain bags X, and so forth.
		HashSet<String> containerBags = new HashSet<>();

		// Bags found to contain but whose parent bags have not been explored.
		// Check against the Hash Set containerBags to see if the entry has
		// already been processed (before putting here)
		LinkedList<String> notProcessed = new LinkedList<>();

		String FIRST_BAG = "shiny gold";
		for (String bag: isContained.get(FIRST_BAG)) {
			if (!containerBags.contains(bag)) {
				notProcessed.add(bag);
			}
		}
		containerBags.add(FIRST_BAG);
		while (notProcessed.size() > 0) {
			String currentBag = notProcessed.pop();
			if (containerBags.contains(currentBag)) {
				// this bag was a duplicate on the linked list
				continue;
			}
			// System.out.println("current bag: " + currentBag);
			HashSet<String> currentBagSet = isContained.get(currentBag);
			if (currentBagSet != null) {
				for (String bag: isContained.get(currentBag)) {
					// if the bag has not been processed
					if (!containerBags.contains(bag)) {
						notProcessed.add(bag);
					}
				}
			}
			containerBags.add(currentBag);
		}
		// must discount the FIRST_BAG in the count for the answer.
		System.out.println("Answer: " + (containerBags.size() - 1));
	}

	private static HashMap<String, LinkedList<String>> readInput() {
		HashMap<String, LinkedList<String>> contains = new HashMap<>();

		Scanner in = new Scanner(System.in);
		while (in.hasNextLine()) {
			String entry = in.nextLine().trim();
			
			// String entry = "light red bags contain 1 bright white bag, 2 muted yellow bags."
			// String [] split = { "light red ", " 1 bright white", " 2 muted yellow" }
			String[] split = entry.substring(0, entry.length() - 1)
			                      .replaceAll("( bag\\b| bags)", "")
			                      .split("(contain|,)");

			// System.out.println(Arrays.toString(split));

			LinkedList<String> bags = new LinkedList<>();
			for (int i = 1; i < split.length; i++) {
				bags.add(split[i].replaceAll("[0-9]*", "").trim());
			}
			contains.put(split[0].trim(), bags);
		}
		in.close();

		return contains;
	}

	// Reverses the information provided:
	// - original: bag X contains bag Y, bag Z
	// - generated: bag Y is contained by bag X
	//              bag Z is contained by bag X
	private static HashMap<String,HashSet<String>>
	generateIsContained(HashMap<String,LinkedList<String>> contains) {
		HashMap<String, HashSet<String>> isContained = new HashMap<>();
		for (Map.Entry<String, LinkedList<String>> entry: contains.entrySet()) {
			String outerBag = entry.getKey();
			LinkedList<String> innerBagList = entry.getValue();
			for (String innerBag: innerBagList) {
				HashSet<String> innerBagContainedBy = isContained.get(innerBag);
				if (innerBagContainedBy == null) {
					innerBagContainedBy = new HashSet<>();
					isContained.put(innerBag, innerBagContainedBy);
				}
				innerBagContainedBy.add(outerBag);
			}
			
		}
		return isContained;
	}
}
