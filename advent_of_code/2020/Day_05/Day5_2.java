import java.util.Arrays;
import java.util.Scanner;

public class Day5_2 {
    private static final int ROWS = 128; // airplane rows
    private static final int COLS = 8; // airplane seats in a row
    private static final int ROWS_LENGTH = (int) (Math.log(ROWS)/Math.log(2));
    private static final int COLS_LENGTH = (int) (Math.log(COLS)/Math.log(2));
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int[] seatIDs = new int[ROWS * COLS];
        int index = 0;
        while (in.hasNext()) {
            String seat = in.next();
            if (seat.length() != COLS_LENGTH + ROWS_LENGTH
                || !seat.matches("[FBLR]*")) {
                System.out.println("Unexpected seat value: " + seat);
            }
            String seatRow = seat.substring(0, ROWS_LENGTH);
            String seatCol = seat.substring(ROWS_LENGTH);
            int indexRow = findLocation(seatRow, ROWS, 'F', 'B');
            int indexCol = findLocation(seatCol, COLS, 'L', 'R');
            seatIDs[index] = indexRow * COLS + indexCol;
            index++;
        }
        in.close();

        // Not all seats are used, but it is guaranteed that it is not the first
        // nor the last available. So the array doesn't have the unused IDs, it
        // is just 0's. Searching in the non zero values, searching the sorted
        // array of the IDs, the two non consecutive IDs indicate what is our
        // seat ID.
        Arrays.sort(seatIDs);
        System.out.println(Arrays.toString(seatIDs));
        int lastValue = 0;
        for (int i = 0; i < seatIDs.length; i++) {
            if (lastValue == 0) {
                lastValue = seatIDs[i];
            }
            else {
                if (seatIDs[i] == lastValue + 1) {
                    lastValue++;
                }
                else {
                    System.out.println("Found seat: " + (lastValue + 1));
                    lastValue = seatIDs[i];
                }
            }
        }
    }

    /**
     * 
     * @param pos passenger position string
     * @param indexLen [0, indexLen - 1]
     * @param lower indicator in the string that the index is in the first half
     * @param higher indicator in the string that the index is in the second half
     * @return
     */
    private static int findLocation(String pos, int indexLen, char lower,
                                    char higher) {
        int indexUpper = indexLen - 1;
        int indexLower = 0;
        for (int i = 0; i < pos.length(); i++) {
            if (pos.charAt(i) == lower) {
                indexUpper = indexLower + (indexUpper - indexLower + 1) / 2 - 1;
            }
            else if (pos.charAt(i) == higher) {
                indexLower = indexLower + (indexUpper - indexLower + 1) / 2;
            }
            // else {
            //     System.out.println("findLocation: " + pos + " " + indexLower +
            //                        " " + indexUpper + " no match!");
            // }
        }
        // if (indexLower != indexUpper) {
        //     System.out.println("lower != upper: lower=" + indexLower + 
        //                        " upper=" + indexUpper);
        // }
        return indexLower;
    }
}