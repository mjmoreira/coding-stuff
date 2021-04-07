import java.util.ArrayList;
import java.util.Scanner;

public class Day11_2 {
	private static char EMPTY = 'L';
	private static char USED = '#';
	private static char FLOOR = '.';

	private static int MAX_USED = 5;

	public static void
	main(String[] args)
	{
		ArrayList<String> seatsPositions = new ArrayList<String>();
		Scanner in = new Scanner(System.in);
		while (in.hasNextLine())
		{
			String line = in.nextLine().trim();
			if (line.length() > 0)
			{
				seatsPositions.add(line);
			}
		}
		in.close();

		char[][] seatsBefore =
			new char[seatsPositions.size()][seatsPositions.get(0).length()];
		char[][] seatsNow =
			new char[seatsPositions.size()][seatsPositions.get(0).length()];
		resetSeats(seatsPositions, seatsBefore);

		while (updatePositions(seatsBefore, seatsNow))
		{
			//System.out.println("Seats occupied: " + seatsOccupied(seatsNow));
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
	private static void resetSeats(ArrayList<String> start, char[][] reset)
	{
		for (int row = 0; row < start.size(); row++)
		{
			for (int col = 0; col < start.get(0).length(); col++)
			{
				reset[row][col] = start.get(row).charAt(col);
			}
		}
	}

	private static int
	seatsOccupied(char[][] seats)
	{
		int seatsOccupied = 0;
		for (int i = 0; i < seats.length; i++)
		{
			for (int j = 0; j < seats[i].length; j++)
			{
				if (seats[i][j] == USED) { seatsOccupied++; }
			}
		}
		return seatsOccupied;
	}

	private static boolean
	updatePositions(char[][] seatsBefore, char[][] seatsNow)
	{
		boolean updated = false;
		for (int i = 0; i < seatsBefore.length; i++)
		{
			for (int j = 0; j < seatsBefore[i].length; j++)
			{
				if (seatsBefore[i][j] == EMPTY
				    && positionOccupation(seatsBefore, i, j) == 0)
				{
					seatsNow[i][j] = USED;
					updated = true;
				}
				else if (seatsBefore[i][j] == USED
				         && positionOccupation(seatsBefore, i, j) >= MAX_USED)
				{
					seatsNow[i][j] = EMPTY;
					updated = true;
				}
				else
				{
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
	private static int
	positionOccupation(char[][] seats, int row, int col) {
		int count = 0;

		// up
		if (firstSeatDirection(seats, -1, 0, row, col) == USED) { count++; }

		// up left
		if (firstSeatDirection(seats, -1, -1, row, col) == USED) { count++; }
		
		// left
		if (firstSeatDirection(seats, 0, -1, row, col) == USED) { count++; }

		// down left
		if (firstSeatDirection(seats, 1, -1, row, col) == USED) { count++; }

		// down
		if (firstSeatDirection(seats, 1, 0, row, col) == USED) { count++; }

		// down right
		if (firstSeatDirection(seats, 1, 1, row, col) == USED) { count++; }

		// right
		if (firstSeatDirection(seats, 0, 1, row, col) == USED) { count++; }

		// up right
		if (firstSeatDirection(seats, -1, 1, row, col) == USED) { count++; }
		
		return count;
	}

	/**
	 * Find the first seat in direction given
	 * @param seats
	 * @param rowDir search direction {-1,0,1}
	 * @param colDir search direction {-1,0,1}
	 * @param rowPos seat position
	 * @param colPos seat position
	 * @return
	 */
	private static char
	firstSeatDirection(char[][] seats, int rowDir, int colDir, int rowPos,
	                   int colPos)
	{
		int row = rowPos + rowDir;
		int col = colPos + colDir;
		while (row >= 0 && row < seats.length
		       && col >= 0 && col < seats[0].length)
		{
			if (seats[row][col] == EMPTY || seats[row][col] == USED)
			{
				return seats[row][col];
			}
			row += rowDir;
			col += colDir;
		}
		return FLOOR;
	}
}
