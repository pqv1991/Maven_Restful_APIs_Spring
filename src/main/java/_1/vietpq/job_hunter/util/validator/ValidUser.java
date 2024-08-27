package _1.vietpq.job_hunter.util.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidUser {
    public final static String REGEX_VALID_EMAIL = "[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+";
    public final static String REGEX_VALID_PHONE_NUMBER = "0(3[2-9]|5[689]|7[06-9]|8[1-68-9]|9[0-46-9])[0-9]{7}";

    public static boolean isValidEmail(String email) {
        Matcher matcher = Pattern.compile(REGEX_VALID_EMAIL).matcher(email);
        return matcher.matches();
    }



}
