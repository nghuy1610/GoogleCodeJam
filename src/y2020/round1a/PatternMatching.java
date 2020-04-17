package y2020.round1a;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class PatternMatching {

    public static void main(String[] args) throws Exception{
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("pattern_matching.txt")));
//        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int nr = scanner.nextInt();
        for(int i = 0; i < nr; i++) {
            System.out.printf("Case #%d: %s\n", i + 1, solve(scanner));
        }
    }

    private static String solve(Scanner scanner) {
        int n = scanner.nextInt();
        String[] patterns  = new String[n];
        for (int i = 0; i < n; i++) {
            patterns[i] = scanner.next();
        }
        int maxSf = 0;
        int idMaxSf = 0;
        int maxPf = 0;
        int idMaxPf = 0;
        String[] prefixes = new String[n];
        String[] suffixes = new String[n];
        String[] middle = new String[n];
        for (int i = 0; i < n; i++) {
            String curStr = patterns[i];
            int firstAst = curStr.indexOf('*');
            prefixes[i] = curStr.substring(0, firstAst);
            if (firstAst > maxPf) {
                maxPf = firstAst;
                idMaxPf = i;
            }
            int lastAst = curStr.lastIndexOf('*');
            suffixes[i] = curStr.substring(lastAst+1);
            if (curStr.length() - lastAst - 1 > maxSf) {
                maxSf = curStr.length() - lastAst - 1;
                idMaxSf = i;
            }
            if (lastAst > firstAst) {
                middle[i] = curStr.substring(firstAst + 1, lastAst);
            } else {
                middle[i] = "";
            }
        }
        StringBuilder midStr = new StringBuilder();
        int lenPf = prefixes[idMaxPf].length();
        int lenSf = suffixes[idMaxSf].length();
        for (int i = 0; i < n; i++) {
            int prLen = prefixes[i].length();
            int sfLen = suffixes[i].length();
            for (int j = 1; j <= prLen; j++) {
                if (prefixes[idMaxPf].charAt(j) != prefixes[i].charAt(j)) {
                    return "*";
                }
            }
            for (int j = 1; j <= sfLen; j++) {
                if (suffixes[idMaxSf].charAt(lenSf - j) != suffixes[i].charAt(sfLen - j)) {
                    return "*";
                }
            }
            midStr.append(middle[i].replaceAll("\\*", ""));
        }
        return prefixes[idMaxPf] +  midStr + suffixes[idMaxSf];
    }
}
