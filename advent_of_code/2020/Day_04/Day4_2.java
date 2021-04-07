import java.util.*;

public class Day4_2 {
    // no "cid" since it is optional
    private static final String[] FIELDS = {"byr", "iyr", "eyr", "hgt", "hcl",
                                            "ecl", "pid"};
    private static final String[] EYE_COLORS = {"amb", "blu", "brn", "gry", 
                                                "grn", "hzl", "oth"};
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
        // Hash map contains entries for all the fields. It the entry is true,
        // it means that the field is present and valid. If it is false, it
        // means that either the field is not present or is present but invalid.
        HashMap<String,Boolean> fieldValid = new HashMap<>();
        for (String field: FIELDS) {
            fieldValid.put(field, Boolean.FALSE);
        }
        while (p.hasNext()) {
            String entry = p.next().trim();
            String[] parts = entry.split(":"); // [0] - key, [1] - value
            String key = parts[0].trim();
            String value = parts[1].trim();
            if (fieldValid.containsKey(parts[0])) {
                switch (key) {
                    case "byr":
                        fieldValid.put(key, Boolean.valueOf(validateBYR(value)));
                        break;
                    case "iyr":
                        fieldValid.put(key, Boolean.valueOf(validateIYR(value)));
                        break;
                    case "eyr":
                        fieldValid.put(key, Boolean.valueOf(validateEYR(value)));
                        break;
                    case "hgt":
                        fieldValid.put(key, Boolean.valueOf(validateHGT(value)));
                        break;
                    case "hcl":
                        fieldValid.put(key, Boolean.valueOf(validateHCL(value)));
                        break;
                    case "ecl":
                        fieldValid.put(key, Boolean.valueOf(validateECL(value)));
                        break;
                    case "pid":
                        fieldValid.put(key, Boolean.valueOf(validatePID(value)));
                        break;
                    default:
                        System.out.println("Default case! (something wrong)");
                        break;
                }
            } else if (!parts[0].equals("cid")){
                System.out.println("Unverified key: " + parts[0]);
            }
        }
        p.close();

        // Passport valid if all the verifications of the fields returned true
        return !fieldValid.containsValue(Boolean.FALSE);
    }

    private static boolean isInteger(String value) {
        return value.matches("[0-9]*");
    }

    private static boolean validateBYR(String value) {
        value = value.trim();
        if (!isInteger(value)) {
            return false;
        }
        int byr = Integer.valueOf(value);
        return (byr >= 1920) && (byr <= 2002);
    }

    private static boolean validateIYR(String value) {
        value = value.trim();
        if (!isInteger(value)) {
            return false;
        }
        int iyr = Integer.valueOf(value);
        return (iyr >= 2010) && (iyr <= 2020);
    }

    private static boolean validateEYR(String value) {
        value = value.trim();
        if (!isInteger(value)) {
            return false;
        }
        int eyr = Integer.valueOf(value);
        return (eyr >= 2020) && (eyr <= 2030);
    }

    private static boolean validateHGT(String value) {
        value = value.trim();
        if (value.length() <= 2) {
            return false;
        }
        String heightString = value.substring(0, value.length() - 2);
        String units = value.substring(value.length() - 2, value.length());
        if (!isInteger(heightString)) {
            return false;
        }
        int height = Integer.valueOf(heightString);
        if (units.equals("cm")) {
            return (height >= 150) && (height <= 193);
        }
        else if (units.equals("in")) {
            return (height >= 59) && (height <= 76);
        }
        return false;
    }

    private static boolean validateHCL(String value) {
        value = value.trim();
        if (!value.startsWith("#")) {
            return false;
        }
        String color = value.substring(1);
        if (color.length() == 6 && color.matches("[0-9a-f]*")) {
            return true;
        }
        return false;
    }

    private static boolean validateECL(String value) {
        value = value.trim();
        for (String color: EYE_COLORS) {
            if (color.equals(value)) {
                return true;
            }
        }
        return false;
    }

    private static boolean validatePID(String value) {
        value = value.trim();
        return (value.length() == 9) && (value.matches("[0-9]*"));
    }
}

