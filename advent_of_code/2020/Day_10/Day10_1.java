import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Day10_1 {
	public static void main(String[] args) {
		ArrayList<Integer> adapters = new ArrayList<>();
		
		Scanner in = new Scanner(System.in);
		while (in.hasNextInt()) {
			adapters.add(Integer.valueOf(in.nextInt()));
		}
		in.close();

		adapters.sort(null);
		System.out.println(adapters);

		int[] diffCount = new int[4];
		diffCount[3]++; // last adapter to device
		diffCount[adapters.get(0) - 0]++; // port to first adapter
		for (int i = 1; i < adapters.size(); i++) {
			int diff = adapters.get(i) - adapters.get(i - 1);
			diffCount[diff]++;
		}
		System.out.println("Differences: " + Arrays.toString(diffCount));
		System.out.println("Answer: " + (diffCount[1] * diffCount[3]));
	}
}
