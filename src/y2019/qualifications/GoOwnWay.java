package y2019.qualifications;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class GoOwnWay {

    private static String ownWay(int size, String steps) {
        StringBuilder result = new StringBuilder();
        for (char c : steps.toCharArray()) {
            if (c == 'S') {
                result.append('E');
            } else {
                result.append('S');
            }
        }
        return result.toString();
    }

    public static void main (String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int round = scanner.nextInt();
        for (int i = 0; i < round; i++) {
            int size = scanner.nextInt();
            String steps = scanner.next();
            String result = ownWay(size, steps);
            System.out.printf("Case #%d: %s\n", i+1, result);
        }
    }
}
