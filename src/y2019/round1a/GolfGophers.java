package y2019.round1a;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class GolfGophers {

    static int[] oneNightReturn(int blade, Scanner scanner) {
        int[] rs = new int[18];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 18; i++) {
            sb.append(blade + " ");
        }
        System.out.println(sb.toString().trim());
        for (int i = 0; i < 18; i++) {
            rs[i] = scanner.nextInt();
        }
        return rs;
    }

    static int chineseRemainder(int[] base, int[] remainder) {
        int n = 1;
        for (int i : base) {
            n *= i;
        }
        int[] ni = new int[base.length];
        int[] mi = new int[base.length];
        for (int i = 0; i < base.length; i++) {
            ni[i] = n / base[i];
        }
        for (int i = 0; i < base.length; i++) {
             int j = 1;
             while ((ni[i] * j) % base[i] != 1) {
                 j++;
             }
             mi[i] = j;
        }
        int rs = 0;
        for (int i = 0; i < base.length; i++) {
            rs += remainder[i] * mi[i] * ni[i];
        }
        return rs % n;
    }

    static int bruteForceChineseRemainder(int[] base, int[] remainder) {
        int result = 1;
        findI:
        for (int i = 1; i < 1000000; i++) {
            for (int j = 0; j < base.length; j++) {
                if (i % base[j] != remainder[j]) {
                    continue findI;
                }
            }
            result = i;
            break;
        }
        return result;
    }





    static int getGoph(Scanner scanner) {
        // All numbers need to be relative prime to each other
        // Only cover up to 510511
//        int[] base = new int[] {2, 3, 5, 7, 11, 13, 17};
        // Cover up to 12252240
        int[] base = new int[] {5, 7, 9, 11, 13, 16, 17};

        int[][] returnCount = new int[7][];
        for (int i = 0; i < base.length; i++) {
            returnCount[i] = oneNightReturn(base[i], scanner);
        }
        int[] remainder = new int[base.length];
        for (int i = 0; i < base.length; i++) {
            int sum = 0;
            for (int j : returnCount[i]) {
                sum += j;
            }
            sum = sum % base[i];
            remainder[i] = sum;
        }
        return chineseRemainder(base, remainder);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int round = scanner.nextInt();
        int night = scanner.nextInt();
        int maxGop = scanner.nextInt();
        for (int i = 0; i < round; i++) {
            int result = getGoph(scanner);
            System.out.println(result);
            int returnInt = scanner.nextInt();
            if (returnInt != 1) {
                break;
            }
        }
    }
}
