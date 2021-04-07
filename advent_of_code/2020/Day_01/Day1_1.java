import java.util.*;

public class Day1_1 {
    private static final int SUM_TARGET = 2020;
    public static void main(String[] args) {
        ArrayList<Integer> data = new ArrayList<Integer>();
        Scanner in = new Scanner(System.in);
        while (in.hasNextLine()) {
            data.add(Integer.valueOf(in.nextInt()));
            in.nextLine();
        }
        System.out.println(data.size());
        in.close();
        boolean found = false;
        for (int i = 0; i < data.size() && !found; i++) {
            for (int j = i + 1; j < data.size() && !found; j++) {
                if (data.get(i) + data.get(j) == SUM_TARGET) {
                    found = true;
                    System.out.println(data.get(i).toString() + " + " +
                                       data.get(j).toString() + " = 2020"); 
                    System.out.println(data.get(i).toString() + " * " +
                                       data.get(j).toString() + " = " +
                                       (data.get(i).intValue() * data.get(j).intValue()));
                }
            }
        }
    }
}