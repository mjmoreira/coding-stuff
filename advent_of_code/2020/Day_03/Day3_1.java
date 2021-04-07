import java.util.*;

public class Day3_1 {
    private static final char TREE_MARK = '#';
    private static int SLOPE_HORIZONTAL = 3;
    private static int SLOPE_VERTICAL = 1;
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Running with problem 1 values!");
        }
        else {
            SLOPE_HORIZONTAL = Integer.valueOf(args[0]);
            SLOPE_VERTICAL = Integer.valueOf(args[1]);
            System.out.print("Horizontal slope: " + SLOPE_HORIZONTAL + " ");
            System.out.println("Vertical slope: " + SLOPE_VERTICAL);
        }
        Scanner in = new Scanner(System.in);
        
        // read map
        ArrayList<String> map = new ArrayList<>();
        while (in.hasNextLine()) {
            map.add(in.nextLine().trim());
        }
        int mapWidth = map.get(0).length();
        int treeCount = 0;
        int horizontalPos = 0;
        for (int verticalPos = 0; verticalPos < map.size(); verticalPos += SLOPE_VERTICAL) {
            if (map.get(verticalPos).charAt(horizontalPos % mapWidth) == TREE_MARK) {
                treeCount++;
            }
            horizontalPos += SLOPE_HORIZONTAL;
        }
        System.out.println("Trees found: " + treeCount);
    }
}