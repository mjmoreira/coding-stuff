import java.util.Scanner;

public class Day12_2 {
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
		int waypointX = 10;
		int waypointY = 1;
		while (in.hasNext()) {
			String entry = in.next();
			char action = entry.charAt(0);
			int value = Integer.parseInt(entry.substring(1));
			if (action == LEFT || action == RIGHT) {
				int[] wp = rotateWaypoint(action, value, waypointX, waypointY);
				waypointX = wp[0];
				waypointY = wp[1];
			}
			else if (action == FORWARD) {
				positionX += value * waypointX;
				positionY += value * waypointY;
			}
			else {
				switch (action) {
					case NORTH:
						waypointY += value;
						break;
					case SOUTH:
						waypointY -= value;
						break;
					case WEST:
						waypointX -= value;
						break;
					case EAST:
						waypointX += value;
						break;
					default:
						System.out.println("Switch: " + action);
						break;
				}
			}
			// System.out.println("" + action + " " + value);
			// System.out.println("Position: (" + positionX + "," + positionY + ")");
			// System.out.println("Waypoint: (" + waypointX + "," + waypointY + ")");
			// System.out.println("---");
		}
		in.close();
		System.out.println("Position: (" + positionX + "," + positionY + ")");
		System.out.println("Answer:" + (Math.abs(positionX) + Math.abs(positionY)));
	}

	private static int[] rotateWaypoint(char action, int value, int waypointX,
	                                    int waypointY) {
		int rotation = 0;
		if (action == LEFT) {
			rotation = (360 - value) % 360;
		}
		else { // == RIGHT
			rotation = (360 + value) % 360;
		}
		int[] result = new int[2];
		if (rotation == 0) {
			result[0] = waypointX;
			result[1] = waypointY;
		}
		else if (rotation == 90) {
			result[0] = waypointY;
			result[1] = -waypointX;
		}
		else if (rotation == 180) {
			result[0] = -waypointX;
			result[1] = -waypointY;
		}
		else { // rotation == 270
			result[0] = -waypointY;
			result[1] = waypointX;
		}
		return result;
	}
}
