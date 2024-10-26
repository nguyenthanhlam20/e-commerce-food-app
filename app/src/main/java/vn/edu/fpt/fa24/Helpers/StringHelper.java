package vn.edu.fpt.fa24.Helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringHelper {
    public static boolean isValidEmail(String value) {
        String patternStr = "^[A-Za-z\\d+_.-]+@[A-Za-z\\d.-]+\\.[A-Za-z]{2,6}$";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    public static boolean isValidPhoneNumber(String value) {
        String patternStr = "^(03|05|07|08|09|01[2|6|8|9])+(\\d{8})$";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    public static boolean isValidUsername(String value) {
        String patternStr =  "^[a-z\\d]{8,}$";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    public static boolean isValidPassword(String value) {
        String patternStr =  "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\W).{8,}$";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
}
