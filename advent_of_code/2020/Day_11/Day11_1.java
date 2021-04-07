import java.util.ArrayList;
import java.util.Scanner;

public class Day11_1 {
	private static char EMPTY = 'L';
	private static char USED = '#';
	private static char FLOOR = '.';

	public static void main(String[] args) {
		ArrayList<String> seatsPositions = new ArrayList<String>();
		Scanner in = new Scanner(System.in);
		while (in.hasNextLine()) {
			String line = in.nextLine().trim();
			if (line.length() > 0) {
				seatsPositions.add(line);
			}
		}
		in.close();

		char[][] seatsBefore =
			new char[seatsPositions.size()][seatsPositions.get(0).length()];
		char[][] seatsNow =
			new char[seatsPositions.size()][seatsPositions.get(0).length()];
		resetSeats(seatsPositions, seatsBefore);

		while (updatePositions(seatsBefore, seatsNow)) {
			// System.out.println("Seats occupied: " + seatsOccupied(seatsNow));
			char[][] seats = seatsNow;
			seatsNow = seatsBefore;
			seatsBefore = seats;
		}
		System.out.println("Seats occupied: " + seatsOccupied(seatsNow));
	}

	/**
	 * 
	 * @param start Positions to which "reset" should be set to
	 * @param reset Array that should be updated to reflect the positions of
	 * "start".
	 */
	private static void resetSeats(ArrayList<String> start, char[][] reset) {
		for (int row = 0; row < start.size(); row++) {
			for (int col = 0; col < start.get(0).length(); col++) {
				reset[row][col] = start.get(row).charAt(col);
			}
		}
	}

	private static int seatsOccupied(char[][] seats) {
		int seatsOccupied = 0;
		for (int i = 0; i < seats.length; i++) {
			for (int j = 0; j < seats[i].length; j++) {
				if (seats[i][j] == USED) {
					seatsOccupied++;
				}
			}
		}
		return seatsOccupied;
	}

	private static boolean updatePositions(char[][] seatsBefore,
	                                       char[][] seatsNow) {
		boolean updated = false;
		for (int i = 0; i < seatsBefore.length; i++) {
			for (int j = 0; j < seatsBefore[i].length; j++) {
				if (seatsBefore[i][j] == EMPTY
				    && positionOccupation(seatsBefore, i, j) == 0) {
					seatsNow[i][j] = USED;
					updated = true;
				}
				else if (seatsBefore[i][j] == USED
				         && positionOccupation(seatsBefore, i, j) >= 4) {
					seatsNow[i][j] = EMPTY;
					updated = true;
				}
				else {
					seatsNow[i][j] = seatsBefore[i][j];
				}
			}
		}
		return updated;
	}

	/**
	 * How many seats around parameter position are used.
	 * @param seats
	 * @param row
	 * @param col
	 * @return
	 */
	private static int positionOccupation(char[][] seats, int row, int col) {
		int count = 0;

		// up
		int r = row - 1;
		int c = col;
		if (r >= 0 && seats[r][c] == USED) { count++; }

		// up left
		r = row - 1;
		c = col - 1;
		if (r >= 0 && c >= 0 && seats[r][c] == USED) { count++; }
		
		// left
		r = row;
		c = col - 1;
		if (c >= 0 && seats[r][c] == USED) { count++; }

		// down left
		r = row + 1;
		c = col - 1;
		if (r < seats.length && c >= 0 && seats[r][c] == USED) { count++; }

		// down
		r = row + 1;
		c = col;
		if (r < seats.length && seats[r][c] == USED) { count++; }

		// down right
		r = row + 1;
		c = col + 1;
		if (r < seats.length && c < seats[r].length && seats[r][c] == USED) { count++; }

		// right
		r = row;
		c = col + 1;
		if (c < seats[r].length && seats[r][c] == USED) { count++; }

		// up right
		r = row - 1;
		c = col + 1;
		if (r >= 0 && c < seats[r].length && seats[r][c] == USED) { count++; }
		
		return count;
	}
}
