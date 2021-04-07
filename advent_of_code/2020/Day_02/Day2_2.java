import java.util.*;

public class Day2_2 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int validPasswordCount = 0;
        while (in.hasNextLine()) {
            String line = in.nextLine();
            if (validPassword(line)) {
                validPasswordCount++;
                System.out.println("Valid: " + line);
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
        // 1 based indexing
        int policyPosition1 = Integer.parseInt(policyRange[0]) - 1;
        int policyPosition2 = Integer.parseInt(policyRange[1]) - 1;

        // check password
        boolean match1 = false;
        boolean match2 = false;
        if (policyPosition1 < password.length()
            && password.charAt(policyPosition1) == policyLetter) {
            match1 = true;
        }
        if (policyPosition2 < password.length()
            && password.charAt(policyPosition2) == policyLetter) {
            match2 = true;
        }

        // check rule match1 XOR match2
        if ((match1 && !match2) || (!match1 && match2)) {
            return true;
        }
        return false;
    }
}