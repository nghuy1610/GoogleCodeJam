package y2019.qualifications;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ForgoneSolution {

    //  Qualification round 2019 1st problem
    private static String[] forgoneSolution(String sn) {
        int count = 0;
        StringBuilder sa = new StringBuilder();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < sn.length(); i++) {
            char c = sn.charAt(i);
            if (c != '4') {
                sa.append(c); sb.append('0');
            } else {
                sa.append('3'); sb.append('1');
            }
        }
        while(sb.charAt(count) == '0') { count++;}
        return new String[] {sa.toString() , sb.substring(count)};
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int size = scanner.nextInt();
        for (int i = 0; i < size; i++) {
            String n = scanner.next(); // Input integer can be pretty big (10^100), so we should keep it as a string or BigInteger
            String[] result = forgoneSolution(n);
            System.out.printf("Case  #%d: %s %s \n", i+1, result[0], result[1]);
        }
    }
}
