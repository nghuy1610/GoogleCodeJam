package y2020.round1c;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class OversizedPancake {
    public static void main(String[] args) throws Exception {
//        Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        Scanner sc = new Scanner(new BufferedReader(new FileReader("oversized_pancake.txt")));
        int nt = sc.nextInt();
        for (int i = 0; i < nt; i++) {
            int n = sc.nextInt();
            int d = sc.nextInt();
            long[] slices = new long[n];
            for (int j = 0; j < n; j++) {
                slices[j] = sc.nextLong();
            }
            System.out.printf("Case #%d: %d\n", i + 1, solve(n, d, slices));
        }
    }

    static long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    static class Fraction {
        long top;
        long bot;

        Fraction(long top, long bot) {
            this.top = top;
            this.bot = bot;
        }
    }

    // Only for test set 1, 2
    private static int solve(int n, int d, long[] slices) {
        Arrays.sort(slices);
        int minCut = d - 1;
        for (int i = 0; i < slices.length; i++) {
            for (int j = 1; j <= d; j++) {
                long orgSize = slices[i];
                long gcd = gcd(orgSize, j);
                Fraction cutSize = new Fraction(orgSize / gcd, j / gcd);
                int fullCutSlice = 1;
                int cutSlices = j;
                boolean enoughSlice = false;
                for (int k = 0; k < slices.length; k++) {
                    if (k == i) {
                        continue;
                    }
                    if (cutSlices + slices[k] * cutSize.bot / cutSize.top >= d) {
                        enoughSlice = true;
                    }
                    if (cutSlices + slices[k] * cutSize.bot / cutSize.top <= d
                            && slices[k] * cutSize.bot % cutSize.top == 0) {
                        fullCutSlice++;
                        cutSlices += slices[k] * cutSize.bot / cutSize.top;
                    }
                }
                if (enoughSlice && d - fullCutSlice < minCut) {
                    minCut = d - fullCutSlice;
                }
            }
        }
        return minCut;
    }
}
