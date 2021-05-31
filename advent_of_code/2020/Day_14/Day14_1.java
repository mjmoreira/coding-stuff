import java.util.*;

public class Day14_1 {
	// See readMask for more information about bitmaskSize, andMask and orMask.
	public static int bitmaskSize = 36;
	private static long andMask;
	private static long orMask;

	public static void main(String[] args) {
		HashMap<Integer, Long> memory = new HashMap<>();

		Scanner in = new Scanner(System.in);
		while(in.hasNext()) {
			Scanner line = new Scanner(in.nextLine());
			String instruction = line.next();
			if (instruction.equals("mask")) {
				line.next(); // eat '='
				readMask(line.next());
			}
			else if (instruction.startsWith("mem")) {
				String s = instruction.substring("mem[".length());
				s = s.substring(0, s.length() - 1); // remove ']'
				Integer memoryPosition = Integer.valueOf(s);
				line.next(); // eat '=''
				Long value = Long.valueOf(applyMask(line.nextLong()));
				memory.put(memoryPosition, value);
			}
			line.close();
		}
		in.close();


		System.out.println("Sum: " + sum(memory.values()));

	}

	private static long sum(Collection<Long> values) {
		long sum = 0;
		for (Long v: values) {
			sum += v.longValue();
		}
		return sum;
	}

	private static long applyMask(long value) {
		return (value & andMask) | orMask;
	}

	/**
	 * Updates the mask.
	 * 
	 * @param mask
	 */
	private static void readMask(String mask) {
		// The original mask information is split in two masks, one to force 0s
		// and the other to force 1s.

		// The andMask holds the bits to be set to 0 and is applied with a
		// bitwise AND to the value.
		andMask = (1L << bitmaskSize) - 1; // Holds the bits to be set to 0.
		// The orMask holds the bits to be set to 1 and is applied with a
		// bitwise OR to the value. 
		orMask = 0;

		char[] m = mask.toCharArray();
		for (int i = 0; i < m.length; i++) {
			if (m[m.length - i - 1] == '1') {
				orMask = orMask | (1L << i);
			}
			else if (m[m.length - i - 1] == '0') {
				andMask = ~(~andMask | (1L << i));
			}
		}
	}
}
