import java.util.Scanner;
import java.util.Arrays;

public class Day6_2 {
    private static final int N_QUESTIONS = 26;
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int[] groupAnswers = new int[N_QUESTIONS];
        int groupPersonCount = 0;
        int answersCount = 0;
        while (in.hasNextLine()) {
            String personAnswers = in.nextLine().trim();
            if (personAnswers.length() == 0) { // end of group info
                answersCount += groupAnswersCount(groupAnswers, groupPersonCount);
                Arrays.setAll(groupAnswers, x -> 0);
                groupPersonCount = 0;
            }
            else {
                groupPersonCount++;
                for (char c: personAnswers.toCharArray()) {
                    groupAnswers[c - 'a']++;
                }
            }
        }
        in.close();

        // Consider the answers from the last group. If the input does not end
        // with the group terminator, the loop finished without running this
        // for the last group. If the loop caught the last group,
        // groupPersonCount will be 0, and groupAnswersCount() will return 0.
        answersCount += groupAnswersCount(groupAnswers, groupPersonCount);

        System.out.println("Answers: " + answersCount);
    }

    private static int groupAnswersCount(int[] groupAnswers,
                                         int groupPersonCount) {
        if (groupPersonCount == 0) {
            return 0;
        }
        int allAnsweredCount = 0;
        for (int nAnswers: groupAnswers) {
            if (nAnswers == groupPersonCount) {
                allAnsweredCount++;
            }
        }
        return allAnsweredCount;
    }
}
