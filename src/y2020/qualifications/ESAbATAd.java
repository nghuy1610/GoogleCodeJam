package y2020.qualifications;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ESAbATAd {

    static String find10(Scanner scanner) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 10; i++) {
            System.out.println(i);
            String res = scanner.next();
            if (res.equals("N")) {
                System.exit(-1);
            }
            sb.append(res);
        }
        return sb.toString();
    }

    static String flip(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            sb.append(c == '0' ? '1' : '0');
        }
        return sb.toString();
    }
    static String reverse(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            sb.insert(0, c);
        }
        return sb.toString();
    }

    static String find20(Scanner scanner) {
        List<List<String>> returnQuery = new ArrayList<>();
        int round = 0;
        for (int i = 0; i < 2; i++) {
            StringBuilder sb = new StringBuilder();
            String res1;
            String res2;
            for (int j = 1; j <= 5; j++) {
                System.out.println(j + round*5);
                sb.append(scanner.next());
            }
            res1 = sb.toString();
            sb = new StringBuilder();
            for (int j = 4; j >= 0; j--) {
                System.out.println(20-j - round * 5);
                sb.append(scanner.next());
            }
            res2 = sb.toString();
            List<String> resList = new ArrayList<>();
            resList.add(res1);
            resList.add(res2);
            returnQuery.add(resList);
            round++;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 10; i++) {
            System.out.println(i);
            sb.append(scanner.next());
        }
        String first4 = sb.toString().substring(0, 5);
        String sec4 = sb.toString().substring(5);
        if (sec4.equals(returnQuery.get(1).get(0))) {
            sb.append(returnQuery.get(1).get(1));
        } else if (sec4.equals(flip(returnQuery.get(1).get(0)))) {
            sb.append(flip(returnQuery.get(1).get(1)));
        } else if (sec4.equals(reverse(returnQuery.get(1).get(1)))) {
            sb.append(reverse(returnQuery.get(1).get(0)));
        } else {
            sb.append(flip(reverse(returnQuery.get(1).get(0))));
        }

        if (first4.equals(returnQuery.get(0).get(0))) {
            sb.append(returnQuery.get(0).get(1));
        } else if (first4.equals(flip(returnQuery.get(0).get(0)))) {
            sb.append(flip(returnQuery.get(0).get(1)));
        } else if (first4.equals(reverse(returnQuery.get(0).get(1)))) {
            sb.append(reverse(returnQuery.get(0).get(0)));
        } else {
            sb.append(flip(reverse(returnQuery.get(0).get(0))));
        }
        return sb.toString();
    }

    static String findRespectiveRightPart(String current, List<String> history) {
        if (current.equals(history.get(0))) {
            return history.get(1);
        } else if (current.equals(reverse(history.get(1)))) {
            return reverse(history.get(0));
        } else if (current.equals(flip(history.get(0)))) {
            return flip(history.get(1));
        } else {
            return reverse(flip(history.get(0)));
        }
    }

    // Require (b / 10 * 2 - 1) * 10 communication -- b = 100 => n = 190 (over limit of 150)
    static String findWithAnyB(Scanner scanner, int b) {
        List<List<String>> queryResult = new ArrayList<>();
        for (int i = 0; i < b / 10; i++) {
            List<String> resultList = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            for(int j = 1; j <= 5; j++) {
                System.out.println(j + i * 5);
                sb.append(scanner.next());
            }
            resultList.add(sb.toString());
            sb = new StringBuilder();
            for (int j = 4; j >= 0; j--) {
                System.out.println(b - j - i*5 );
                sb.append(scanner.next());
            }
            resultList.add(sb.toString());
            queryResult.add(resultList);
        }
        if (b == 10) {
            return queryResult.get(0).get(0) + queryResult.get(0).get(1);
        }
        List<String> leftParts = new ArrayList<>();
        for (int i = 0; i < b / 10 - 1; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 1; j <= 10; j++) {
                System.out.println(j + i * 5);
                sb.append(scanner.next());
            }
            leftParts.add(sb.toString());
        }
        int nPart = b / 5;
        String[] parts = new String[nPart];
        parts[nPart / 2 - 1] = leftParts.get(nPart / 2 - 2).substring(5);
        parts[nPart / 2 - 2] = leftParts.get(nPart / 2 - 2).substring(0, 5);
        parts[nPart / 2] = findRespectiveRightPart(parts[nPart / 2 - 1], queryResult.get(nPart / 2 -1));
        parts[nPart /2 + 1] = findRespectiveRightPart(parts[nPart / 2 -2], queryResult.get(nPart / 2 -2));
        for (int i = nPart / 2 - 3; i >= 0; i--) {
            String leftPart = leftParts.get(i);
            String subLeft = leftPart.substring(0, 5);
            String subRight = leftPart.substring(5);
            String respLeft = findRespectiveRightPart(subLeft, queryResult.get(i));
            String respRight = findRespectiveRightPart(subRight, queryResult.get(i+1));
            if (parts[i+1].equals(subRight)) {
                parts[i] = subLeft;
                parts[nPart - 1 - i] = respLeft;
            } else if (parts[i+1].equals(flip(subRight))) {
                parts[i] = flip(subLeft);
                parts[nPart - 1 -i] = flip(respLeft);
            } else if (parts[i+1].equals(reverse(respRight))) {
                parts[i] = reverse(respLeft);
                parts[nPart - 1 - i] = reverse(subLeft);
            } else {
                parts[i] = flip(reverse(respLeft));
                parts[nPart - 1 - i] = flip(reverse(subLeft));
            }
        }
        StringBuilder sb = new StringBuilder();
        for (String part : parts) {
            sb.append(part);
        }
        return sb.toString();
    }


    static String findRespectiveRightPartV2(String currentSubLeft, List<String> history) {
        String subLeft = history.get(0).substring(0, 2);
        String subRight = history.get(1).substring(0, 2);
        if (currentSubLeft.equals(subLeft)) {
            return history.get(1);
        } else if (currentSubLeft.equals(reverse(subRight))) {
            return reverse(history.get(0));
        } else if (currentSubLeft.equals(flip(subLeft))) {
            return flip(history.get(1));
        } else {
            return reverse(flip(history.get(0)));
        }
    }

    static String findCurrentLeftPart(String currentSubLeft, List<String> history) {
        String subLeft = history.get(0).substring(0, 2);
        String subRight = history.get(1).substring(0, 2);
        if (currentSubLeft.equals(subLeft)) {
            return history.get(0);
        } else if (currentSubLeft.equals(flip(subLeft))) {
            return flip(history.get(0));
        } else if (currentSubLeft.equals(reverse(subRight))) {
            return reverse(history.get(1));
        } else {
            return flip(reverse(history.get(1)));
        }
    }

    // Require b + (b/10) * 2 + ceil(b/10/4) * 2 -- b = 100 => n = 100 + 20 + 6 = 131
    static String findWithAnyBV2(Scanner scanner, int b) {
        List<List<String>> queryResult = new ArrayList<>();
        for (int i = 0; i < b / 10; i++) {
            List<String> resultList = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            for(int j = 1; j <= 5; j++) {
                System.out.println(j + i * 5);
                sb.append(scanner.next());
            }
            resultList.add(sb.toString());
            sb = new StringBuilder();
            for (int j = 4; j >= 0; j--) {
                System.out.println(b - j - i*5 );
                sb.append(scanner.next());
            }
            resultList.add(sb.toString());
            queryResult.add(resultList);
        }
        if (b == 10) {
            return queryResult.get(0).get(0) + queryResult.get(0).get(1);
        }
        int x = -1, y = -1;
        findDiff:
        for (int i = 0; i < queryResult.size(); i++) {
            for (int j = 0; j < 5; j++) {
                if (queryResult.get(i).get(0).charAt(j) != queryResult.get(i).get(1).charAt(j)) {
                    x = i * 5 + j + 1;
                    if (x % 5 == 0) {
                        y = x;
                        x--;
                    } else {
                        y = x + 1;
                    }
                    break findDiff;
                }
            }
        }

        if (x == -1) {
            x = 1; y = 2;
        }
        int diffPartPos = x / 5;

        List<String> leftParts = new ArrayList<>();
        StringBuilder sb;
        for (int i = 0; 4 * i <= (b / 10 - 1) / 4; i++) {
            sb = new StringBuilder();
            for (int j = -1; j < 4  && i * 20 + j * 5 < b / 2; j++) {
                if (j == -1) {
                    System.out.println(x);
                    System.out.println(y);
                } else {
                    System.out.println(20 * i + 5 * j + 1);
                    System.out.println(20 * i + 5 * j + 2);
                }
                sb.append(scanner.next());
                sb.append(scanner.next());
            }
            leftParts.add(sb.toString());
        }

        int nPart = b / 5;
        String[] parts = new String[nPart];
        int lSize = leftParts.size();
        String lastLeftPart = leftParts.get(lSize - 1);
        for (int i = 0; i < lastLeftPart.length() / 2; i++) {
            String representString = lastLeftPart.substring(i * 2, i * 2 + 2);
            int posPart;
            if (i == 0) {
                posPart = diffPartPos;
            } else {
                posPart = (lSize - 1) * 4 + (i-1);
            }
            parts[posPart] = findCurrentLeftPart(representString, queryResult.get(posPart));
            parts[parts.length - posPart - 1] = findRespectiveRightPartV2(representString, queryResult.get(posPart));
        }

        for (int i = 0; i < leftParts.size() - 1; i++) {
            String leftPartString = leftParts.get(i);
            String diffString = leftPartString.substring(0, 2);
            String currentDiffPart = findCurrentLeftPart(diffString, queryResult.get(diffPartPos));
            for (int j = 1; j < 5; j++) {
                String representString = leftPartString.substring(j * 2, j * 2 + 2);
                int posPart = i * 4 + j - 1;
                String currentLeftPart = findCurrentLeftPart(representString, queryResult.get(posPart));
                String currentRightPart = findRespectiveRightPartV2(representString, queryResult.get(posPart));
                if (currentDiffPart.equals(parts[diffPartPos])) {
                    parts[posPart] = currentLeftPart;
                    parts[b / 10 - posPart - 1] = currentRightPart;
                } else if (currentDiffPart.equals(flip(parts[diffPartPos]))) {
                    parts[posPart] = flip(currentLeftPart);
                    parts[b/10 - posPart - 1] = flip(currentRightPart);
                } else if (currentDiffPart.equals(reverse(parts[b/10 - diffPartPos - 1]))) {
                    parts[posPart] = reverse(currentRightPart);
                    parts[b/10 - posPart - 1] = reverse(currentLeftPart);
                } else {
                    parts[posPart] = flip(reverse(currentRightPart));
                    parts[b/10 - posPart - 1] = flip(reverse(currentLeftPart));
                }
            }
        }
        StringBuilder resultBuilder = new StringBuilder();
        for (String part : parts) {
            resultBuilder.append(part);
        }
        return resultBuilder.toString();
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int round = scanner.nextInt();
        int b = scanner.nextInt();
        for (int i = 0; i < round; i++) {
            System.out.println(findWithAnyBV2(scanner, b));
            String result = scanner.next();
            if (result.equals("N")) {
                break;
            }
        }
    }
}
