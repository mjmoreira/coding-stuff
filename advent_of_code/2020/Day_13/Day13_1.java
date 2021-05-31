import java.util.Scanner;

public class Day13_1 {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		in.useDelimiter("[ \n,]");

		int timestamp = in.nextInt();
		int bestBusID = -1;
		int bestWait = Integer.MAX_VALUE;
		while (in.hasNext()) {
			if (!in.hasNextInt()) {
				in.next(); // discard x
				continue;
			}
			int busID = in.nextInt();
			// Not the wait until the next. Just how long ago the last passed.
			int wait = timestamp % busID;
			if (wait == 0) { // only one may be in this condition
				bestBusID = busID;
				bestWait = wait;
			}
			else {
				// Actual wait time until the next bus.
				wait = busID - wait;
				if (bestWait > wait) {
					bestWait = wait;
					bestBusID = busID;
				}
			}
		}
		in.close();

		System.out.println("First bus: " + bestBusID + " with " + bestWait
		                   + " wait.");
		System.out.println("Answer: " + (bestWait * bestBusID));
	}
}