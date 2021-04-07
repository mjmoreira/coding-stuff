import java.util.Scanner;
import java.util.ArrayList;

class Operation {
	public static enum operation {ACC, JMP, NOP};
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

public class Day8_1 {
	public static void main(String[] args) {
		ArrayList<Operation> instructions = new ArrayList<>();
		readInstructions(instructions);
		// System.out.println(instructions);

		boolean[] executed = new boolean[instructions.size()];
		
		int acc = 0;
		int instruction = 0;
		while (true) {
			if (instruction < 0 || instruction >= instructions.size()) {
				System.out.println("Invalid instruction index: " + instruction);
				break;
			}
			if (executed[instruction]) { // can be used for the while loop test
				break;
			}
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
		// instruction index printed is 0 based!
		System.out.println("Instruction " + instruction  + " is repeated.");
		System.out.println("Acc value: " + acc);
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
