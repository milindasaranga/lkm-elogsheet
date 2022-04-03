package com.lkm.elogsheet.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtil {

    public static boolean isEmptyOrNull(String param) {
        if (param == null || param.trim().equals("")) {
            return true;
        }
        return false;
    }

    public static String filter(String input) {
        if (!hasSpecialChars(input)) {
            return (input);
        }
        StringBuilder sb = new StringBuilder(input.length());
        char c;

        for (int i = 0; i < input.length(); i++) {
            c = input.charAt(i);
            switch (c) {
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                case '"':
                    sb.append("&quot;");
                    break;
                case '\'':
                    sb.append("&apos;");
                    break;
                case '&':
                    sb.append("&amp;");
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();

    }

    private static boolean hasSpecialChars(String input) {

        Pattern regexSpecialChars = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher inputStr = regexSpecialChars.matcher(input);
        boolean hasSpecialChars = inputStr.find();

        if (!hasSpecialChars) {
            return false;
        }

        return true;
    }

}
