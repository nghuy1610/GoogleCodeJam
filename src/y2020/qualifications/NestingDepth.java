package y2020.qualifications;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class NestingDepth {

    // ONly for 0 and 1
    static String nestingDepth(String s) {
        StringBuilder sb = new StringBuilder(s);
        int count;
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) == '0') {
                continue;
            } else {
                sb.insert(i, "(");
                i += 2;
                while (i < sb.length() && sb.charAt(i) == '1' ) {
                    i++;
                }
                sb.insert(i, ')');
            }
        }
        return sb.toString();
    }

    // If s consist of 0, separate string by 0 and add parenthesis for part without 0
    // f(1023) = f(1) + 0 + f(023)
    // not consist of zero then find min number and add the same amount of parenthesis at start and end
    // then minus every number in string by the min number and find f of string after minus min
    // f(123) = ( + f(012) + ) = ( + 0 + f(12) + ) = ( + 0 + ( + f(01) + ) + ) = (0(0(0)))
    // finally replace 0 by character in origin string ony by one from left to right
    static String addParenthesis(String s) {
        if (s.equals("")) {return "";}
        if (s.equals("0")) {return "0";}
        StringBuilder sb = new StringBuilder();
        int idx;
        if ((idx = s.indexOf('0')) != -1) {
            String subString = s.substring(0, idx);
            sb.append(addParenthesis(subString));
            s = s.substring(idx+1);
            idx = s.indexOf('0');
            while (idx != -1) {
                sb.append("0" + addParenthesis(s.substring(0, idx)));
                s = s.substring(idx+1);
                idx = s.indexOf("0");
            }
            sb.append("0" + addParenthesis(s));
        } else {
            int min = 10;
            int pos = -1;
            for (char c : s.toCharArray()) {
                if (c - '0' < min) {
                    min = c - '0';
                }
            }
            StringBuilder strBuilder = new StringBuilder();
            for (char c : s.toCharArray()) {
                strBuilder.append((char)(c - min));
            }
            for (int i = 0; i < min; i++) {
                sb.append("(");
            }
            sb.append(addParenthesis(strBuilder.toString()));
            for (int i = 0; i < min; i++) {
                sb.append(")");
            }
        }
        return sb.toString();
    }

    static String generalNestingDepth(String s) {
        StringBuilder sb = new StringBuilder(addParenthesis(s));
        int count = 0;
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) == '0') {
                sb.setCharAt(i, s.charAt(count));
                count++;
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception{
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("nesting_dept.txt")));
        int round = scanner.nextInt();
        for (int i = 0; i < round; i++) {
            String s = scanner.next();
            System.out.printf("Case #%d: %s\n", i + 1, generalNestingDepth(s));
        }
    }
}
