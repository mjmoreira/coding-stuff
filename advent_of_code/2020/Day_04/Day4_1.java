import java.util.*;

public class Day4_1 {
    // no "cid" since it is optional
    private static final String[] fields = {"byr", "iyr", "eyr", "hgt", "hcl",
                                            "ecl", "pid"};
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String passport = "";
        int validPassportCount = 0;
        while (in.hasNextLine()) {
            String latestLine = in.nextLine();
            passport += " " + latestLine;
            if (latestLine.trim().length() == 0) {
                if (validPassport(passport)) {
                    validPassportCount++;
                }
                passport = "";
            }
        }
        // Because of how the previous loop works, it is required to check
        // the "passport" variable again.
        // This is needed because the last passport may not have an empty line
        // at the end of the input, to signal the end of the passport, so it
        // is not processed inside the loop.
        // Example 1: (regular case, from the middle of the input)
        // [Line 1] asdf:asdf\n {nextLine() reads this}
        // [Line 2] \n {nextLine() reads this -- passport end indicator present}
        // [Line 3] asdf:sadf\n {nextLine() reads this}
        // Example 2: (file ends in a way the makes the previous loop work)
        // [Line 1] sdfa:asdf\n {nextLine() reads this}
        // [Line 2] \n {nextLine() reads this. Terminator of passport.
        //              "passport" is processed.}
        // [Line 3] EOF {hasNextLine() fails and terminates the loop.
        //               Visual cursor is here. There is no need for the next
        //               "if" in this case.
        //               The "passport" used in the "if" is an empty string "".}
        // Example 3: (file ends in a way that requires the next "if")
        // [Line 1] sdfa:asdf\n {nextLine() reads this}
        // [Line 2] EOF {hasNextLine() fails and terminates the loop, but 
        //               the data contained in "passport" is not processed.}
        if (validPassport(passport)) {
            validPassportCount++;
        }
        in.close();
        System.out.println("Valid passports: " + validPassportCount);
    }

    private static boolean validPassport(String passport) {
        Scanner p = new Scanner(passport);
        boolean[] fieldValid = new boolean[fields.length];
        for (int i = 0; i < fieldValid.length; i++) {
            fieldValid[i] = false;
        }
        while (p.hasNext()) {
            String entry = p.next().trim();
            String[] parts = entry.split(":"); // [0] - key, [1] - value
            for (int i = 0; i < fields.length; i++) {
                if (fields[i].equals(parts[0])) {
                    fieldValid[i] = true;
                    break;
                }
            }
        }
        p.close();

        boolean allFound = true;
        for (int i = 0; i < fieldValid.length; i++) {
            allFound = allFound && fieldValid[i];
        }
        return allFound;
    }
}

