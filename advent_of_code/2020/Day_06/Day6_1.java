import java.util.Scanner;
import java.util.HashSet;

public class Day6_1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        HashSet<Character> groupAnswers = new HashSet<>();
        int answersCount = 0;
        while (in.hasNextLine()) {
            String personAnswers = in.nextLine().trim();
            if (personAnswers.length() == 0) {
                // end of group info
                answersCount += groupAnswers.size();
                groupAnswers.clear();
            }
            else {
                for (char c: personAnswers.toCharArray()) {
                    groupAnswers.add(Character.valueOf(c));
                }
            }
        }
        in.close();

        // Consider the answers from the last group. If the input does not end
        // with the group terminator, the loop finished without running this
        // for the last group.
        answersCount += groupAnswers.size();

        System.out.println("Answers: " + answersCount);
    }
}
