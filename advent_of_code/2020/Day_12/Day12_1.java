import java.util.Scanner;

public class Day12_1 {
	public static final char LEFT = 'L';
	public static final char RIGHT = 'R';
	public static final char FORWARD = 'F';
	public static final char NORTH = 'N';
	public static final char EAST = 'E';
	public static final char SOUTH = 'S';
	public static final char WEST = 'W';
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int positionX = 0;
		int positionY = 0;
		char heading = EAST;
		while (in.hasNext()) {
			String entry = in.next();
			char action = entry.charAt(0);
			int value = Integer.parseInt(entry.substring(1));
			if ((action == LEFT || action == RIGHT)) {
				heading = updateHeading(heading, action, value);
			}
			else {
				switch (action) {
					case NORTH:
						positionX += value;
						break;
					case SOUTH:
						positionX -= value;
						break;
					case WEST:
						positionY -= value;
						break;
					case EAST:
						positionY += value;
						break;
					case FORWARD:
						switch (heading) {
							case NORTH:
								positionX += value;
								break;
							case SOUTH:
								positionX -= value;
								break;
							case WEST:
								positionY -= value;
								break;
							case EAST:
								positionY += value;
								break;
							default:
								System.out.println("Inner switch: " + action);
								break;
						}
						break;
					default:
						System.out.println("Outer switch: " + action);
						break;
				}
			}
		}
		in.close();
		System.out.println("Position: (" + positionX + "," + positionY + ")");
		System.out.println("Answer:" + (Math.abs(positionX) + Math.abs(positionY)));
	}

	private static char updateHeading(char curHeading, char headingDirection,
	                                  int headingChange) {
		int heading = convertHeading(curHeading);
		if (headingDirection == LEFT) {
			heading = (360 + heading - headingChange) % 360;
		}
		else { // == RIGHT
			heading = (360 + heading + headingChange) % 360;
		}
		return convertHeading(heading); 
	}

	private static int convertHeading(char heading) {
		if (heading == NORTH) { return 0; }
		if (heading == EAST) { return 90; }
		if (heading == SOUTH) { return 180; }
		return 270;
	}

	private static char convertHeading(int heading) {
		if (heading == 0) { return NORTH; }
		if (heading == 90) { return EAST; }
		if (heading == 180) { return SOUTH; }
		return WEST;
	}
}
