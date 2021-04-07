import java.util.*;

public class Day2_1 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int validPasswordCount = 0;
        while (in.hasNextLine()) {
            String line = in.nextLine();
            if (validPassword(line)) {
                validPasswordCount++;
                // System.out.println("Valid: " + line);
            }
        }
        System.out.println("Valid passwords: " + validPasswordCount);
    }

    private static boolean validPassword(String line) {
        // split policy from password
        String[] parts = line.split(":");
        if (parts.length != 2) {
            System.out.println("Bad line:" + line + ";" + Arrays.toString(parts));
            return false;
        }
        String policy = parts[0];
        String password = parts[1].stripLeading();
        
        // read policy
        char policyLetter = policy.charAt(policy.length() - 1);
        String[] policyRange = policy.split(" ");
        policyRange = policyRange[0].split("-");
        int policyRangeStart = Integer.parseInt(policyRange[0]);
        int policyRangeEnd = Integer.parseInt(policyRange[1]);

        // check password
        int count = 0;
        for (int i = 0; i < password.length(); i++) {
            if (password.charAt(i) == policyLetter) {
                count++;
            }
        }
        if (count >= policyRangeStart && count <= policyRangeEnd) {
            return true;
        }
        return false;
    }
}