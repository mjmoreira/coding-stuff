import java.util.Scanner;
import java.util.ArrayList;

class Operation {
	static enum operation {ACC, JMP, NOP};
	operation op;
	int value;
	Operation(operation op, int value) {
		this.op = op;
		this.value = value;
	}

	public String toString() {
		return "" + this.op.toString() + " " + value;
	}
}

class RunResults {
	int lastInstructionIndex; // instruction where it is repeated
	int previousInstructionIndex; // jmp instruction that lead to the repeat
	int accumulator;
	boolean repeated;

	RunResults(int lastInstructionIndex, int previousInstructionIndex,
	           int accumulator, boolean repeated) {
		this.lastInstructionIndex = lastInstructionIndex;
		this.previousInstructionIndex = previousInstructionIndex;
		this.accumulator = accumulator;
		this.repeated = repeated;
	}

	public String toString() {
		return "(index: " + lastInstructionIndex
			   + ", previousIndex: " + previousInstructionIndex
			   + ", acc: " + accumulator
		       + ", repeated: " + Boolean.toString(repeated) + ")";
	}
}


public class Day8_2 {
	public static void main(String[] args) {
		ArrayList<Operation> instructions = new ArrayList<>();
		readInstructions(instructions);
		// System.out.println(instructions);

		RunResults results = runInstructions(instructions);
		System.out.println(results);

		// Change NOP only if the value is != 0.
		// The jumps must be tried one by one. Changing just the one that sends
		// back does not necessarily fix the problem, but this jump indicates
		// that the problem is either in this jump or in one of the previous
		// jumps. (The problem is necessarily in this set of instructions,
		// because only one of the instructions is wrong.)

		// Start by trying to change the NOP (!= 0) until the problematic
		// instruction. If it doesn't work try to change the JMP.

		int startIndex = 0;
		int endIndex = 
			results.lastInstructionIndex < results.previousInstructionIndex
			? results.previousInstructionIndex : results.lastInstructionIndex;
		while (startIndex <= endIndex) {
			ArrayList<Operation> instructions2 = new ArrayList<>(instructions);
			int replaceIndex = replaceNOP(instructions2, startIndex, endIndex);
			System.out.println("Replace index (NOP->JMP): " + replaceIndex);
			RunResults results2 = runInstructions(instructions2);
			System.out.println("Results: " + results2);
			if (!results2.repeated) { return; }
			if (replaceIndex == -1) { break; }
			startIndex = replaceIndex + 1;
		}

		startIndex = 0;
		while (startIndex <= endIndex) {
			ArrayList<Operation> instructions2 = new ArrayList<>(instructions);
			int replaceIndex = replaceJMP(instructions2, startIndex, endIndex);
			System.out.println("Replace index (JMP->NOP): " + replaceIndex);
			RunResults results2 = runInstructions(instructions2);
			System.out.println("Results: " + results2);
			if (!results2.repeated) { return; }
			if (replaceIndex == -1) { break; }
			startIndex = replaceIndex + 1;
		}
	}

	/**
	 * Replace first instruction NOP in the interval [startIndex, endIndex]
	 * @param instructions
	 * @param startIndex
	 * @param endIndex
	 * @return index of the NOP instruction replaced with JMP or -1 if no
	 * instruction was replaced.
	 */
	private static int
	replaceNOP(ArrayList<Operation> instructions, int startIndex, int endIndex) {
		for (int index = startIndex; index <= endIndex; index++) {
			Operation op = instructions.get(index);
			if (op.op == Operation.operation.NOP && op.value != 0) {
				Operation op2 = new Operation(Operation.operation.JMP, op.value);
				instructions.set(index, op2);
				return index;
			}
		}
		return -1;
	}

	/**
	 * Replace first instruction JMP in the interval [startIndex, endIndex]
	 * @param instructions
	 * @param startIndex
	 * @param endIndex
	 * @return index of the JMP instruction replaced with NOP or -1 if no
	 * instruction was replaced.
	 */
	private static int
	replaceJMP(ArrayList<Operation> instructions, int startIndex, int endIndex) {
		for (int index = startIndex; index <= endIndex; index++) {
			Operation op = instructions.get(index);
			if (op.op == Operation.operation.JMP) {
				Operation op2 = new Operation(Operation.operation.NOP, op.value);
				instructions.set(index, op2);
				return index;
			}
		}
		return -1;
	}

	private static RunResults runInstructions(ArrayList<Operation> instructions) {
		boolean[] executed = new boolean[instructions.size()];
		
		int acc = 0;
		int instruction = 0;
		int previousInstruction = 0;
		while (instruction < instructions.size()) {
			if (instruction < 0) {
				System.out.println("Invalid instruction index: " + instruction);
				break;
			}
			if (executed[instruction]) { // can be used for the while loop test
				break;
			}
			previousInstruction = instruction;
			Operation op = instructions.get(instruction);
			if (op.op == Operation.operation.NOP) {
				executed[instruction] = true;
				instruction++;
			}
			else if (op.op == Operation.operation.ACC) {
				executed[instruction] = true;
				instruction++;
				acc += op.value;
			}
			else {
				executed[instruction] = true;
				instruction += op.value;
			}
		}
		// instruction index is 0 based!
		if (instruction >= instructions.size()) {
			return new RunResults(instruction, previousInstruction, acc, false);
		}
		return new RunResults(instruction, previousInstruction, acc,
		                      executed[instruction]);
	}


	private static void readInstructions(ArrayList<Operation> instructions) {
		Scanner in = new Scanner(System.in);
		while (in.hasNextLine()) {
			Scanner line = new Scanner(in.nextLine());
			String op = line.next();
			int value = line.nextInt();
			line.close();
			switch (op) {
				case "acc":
					instructions.add(new Operation(Operation.operation.ACC, value));
					break;
				case "jmp":
					instructions.add(new Operation(Operation.operation.JMP, value));
					break;
				case "nop":
					instructions.add(new Operation(Operation.operation.NOP, value));
					break;
				default:
					System.out.println("Switch default: " + op);
			}
		}
		in.close();
	}
}
