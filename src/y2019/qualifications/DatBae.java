package y2019.qualifications;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DatBae {
    // For attempts >= 10
    // 0, 1, 2, ... , total (total < 2 ^10)
    private static String simpleDatBae(Scanner scanner, int total, int broken, int attempt) {
        List<String> response = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (int i = attempt - 1; i >= 0; i--) {
            for (int j= 0; j < total; j++) {
                sb.append(j >> i & 1);
            }
            System.out.println(sb.toString());
            sb = new StringBuilder();
            response.add(scanner.next());
        }
        boolean[] exist = new boolean[total];
        for (int i = 0; i < total - broken; i++) {
            int temp = 0;
            for (int j = 0; j < attempt; j++) {
                temp = temp * 2 + response.get(j).charAt(i) - '0';
            }
            exist[temp] = true;
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < total; i++) {
            if (!exist[i]) {
                result.append(i + " ");
            }
        }
        return result.toString().trim();
    }

    // For attempt >= 5
    // 1, 2, 3, ..., 31, 1, 2, 3, ..., 31, ..., 1, ...., (total % 32)
    private static String fullDatBae(Scanner scanner, int total, int broken, int attempt) {
        StringBuilder sender = new StringBuilder();
        List<String> responses = new ArrayList<>();
        for (int i = attempt - 1; i >= 0; i--) {
            for (int j = 0; j < total; j++) {
                sender.append((j % 32) >> i & 1 );
            }
            System.out.println(sender.toString());
            sender = new StringBuilder();
            responses.add(scanner.next());
        }
        boolean[] exist = new boolean[total];
        int lastTemp = -1;
        int block = 0;
        for (int i = 0; i < total - broken; i++) {
            int temp = 0;
            for (int j = 0; j < attempt; j++) {
                temp = temp * 2 + (int)responses.get(j).charAt(i) - '0';
            }
            if (temp < lastTemp) {block++;}
            lastTemp = temp;
            exist[temp + block * 32] = true;
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < total; i++) {
            if (!exist[i]) {
                result.append(i + " ");
            }
        }
        return result.toString().trim();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int round = scanner.nextInt();
        for (int i = 0; i < round; i++) {
            int n = scanner.nextInt();
            int b = scanner.nextInt();
            int f = scanner.nextInt();
            String result = fullDatBae(scanner, n, b, f);
            System.out.println(result);
            int res = scanner.nextInt();
            if (res == -1) {
                break;
            }
        }
    }
}
