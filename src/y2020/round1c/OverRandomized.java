package y2020.round1c;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class OverRandomized {
    public static void main(String[] args) throws Exception{
//        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        Scanner sc = new Scanner(new BufferedReader(new FileReader("over_randomized.txt")));
        int nt = sc.nextInt();
        for (int i = 0; i < nt; i++) {
            int u = sc.nextInt();
            sc.nextLine();
            String[][] input = new String[10000][2];
            for (int j = 0; j < 10000; j++) {
                String[] pair = sc.nextLine().split(" ");
                input[j][0] = pair[0];
                input[j][1] = pair[1];
            }
            System.out.printf("Case #%d: %s\n", i+1, solveV2(u, input));
        }
    }

    // TPFOXLUSHB
    // Test set 1 and 2
    private static String solve(int u, String[][] input) {
        Set<Character> allChar = new HashSet<>();
        List<Set<Character>> relativeChars = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            relativeChars.add(new HashSet<>());
        }
        for (String[] pair : input) {
            if (pair[0].length() == pair[1].length()) {
                int digit = pair[0].charAt(0) - '0';
                char c = pair[1].charAt(0);
                relativeChars.get(digit).add(c);
            }
            for (int j = 0; j < pair[1].length();j++) {
                allChar.add(pair[1].charAt(j));
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 9; i++) {
            for (Character c : relativeChars.get(i)) {
                if (!relativeChars.get(i-1).contains(c)) {
                    sb.append(c);
                    break;
                }
            }
        }
        for (Character c : allChar) {
            if (!relativeChars.get(9).contains(c)) {
                sb.insert(0, c);
                break;
            }
        }
        return sb.toString();
    }

    private static String solveV2(int u, String[][] input) {
        int[] times = new int[26];
        Set<Character> allChar = new HashSet<>();
        Set<Character> firstChars = new HashSet<>();
        for (String[] pair : input) {
            times[pair[1].charAt(0) - 'A']++;
            for (int i = 0; i < pair[1].length(); i++) {
                allChar.add(pair[1].charAt(i));
            }
        }
        List<int[]> ls = new ArrayList<>();
        for (int i = 0; i < times.length; i++) {
            if (times[i] != 0) {
                ls.add(new int[] {i, times[i]});
            }
        }
        ls.sort((t1, t2) -> t2[1] - t1[1]);
        StringBuilder sb = new StringBuilder();
        for (int[] t : ls) {
            char c = (char)((t[0]) + 'A');
            sb.append(c);
            firstChars.add(c);
        }
        for (Character c : allChar) {
            if (!firstChars.contains(c)) {
                sb.insert(0, c);
                break;
            }
        }
        return sb.toString();
    }


}
