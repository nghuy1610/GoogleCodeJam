package y2019.round1b;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class MahattanCrepe {

    // Brute force, update value of all matrix base on position and direction of each people
    // O(p * q^2), only pass test set 1
    static int[] findStore(int[][] pos, String[] dir, int p, int q) {
        int[][] grid = new int[q+1][q+1];
        for (int i = 0; i < p; i++) {
            int x = pos[i][0];
            int y = pos[i][1];
            String cDir = dir[i];
            if (cDir.equals("S")) {
                for (int m = 0; m < x; m++) {
                    for (int n = 0; n <= q; n++) {
                        grid[m][n]++;
                    }
                }
            } else if (cDir.equals("N")) {
                for (int m = q; m > x; m--) {
                    for (int n = 0; n <= q; n++) {
                        grid[m][n]++;
                    }
                }
            } else if (cDir.equals("W")) {
                for (int m = 0; m <= q ; m++) {
                    for (int n = 0; n < y; n++) {
                        grid[m][n]++;
                    }
                }
            } else {
                for (int m = 0; m <= q ; m++) {
                    for (int n = q; n > y; n--) {
                        grid[m][n]++;
                    }
                }
            }
        }
        int max = 0;
        int[] maxPos = new int[2];
        for (int i = 0; i <= q; i++) {
            for (int j = 0; j <= q; j++) {
                if (grid[i][j] > max) {
                    maxPos[0] = j;
                    maxPos[1] = i;
                    max = grid[i][j];
                }
            }
        }
        return maxPos;
    }

    // Handle separately x and y dimension because of independence
    // O(p^2), can pass test set 2
    static int[] findStoreV2(int[][] pos, String[] dir, int p, int q) {
        int[] storePos = new int[p];
        int[] storePoint = new int[p];
        for (int i = 0; i < p; i++) {
            if (dir[i].equals("S")) {
                storePos[i] = 0;
                int count = 0;
                for (int j = 0; j < p; j++) {
                    if (dir[j].equals("S")) {
                        count++;
                    }
                }
                storePoint[i] = count;
            } else if (dir[i].equals("N")) {
                storePos[i] = pos[i][0] + 1;
                int count = 0;
                for (int j = 0; j < p; j++ ) {
                    if ((dir[j].equals("N") && pos[j][0] <= pos[i][0]) || (dir[j].equals("S") && pos[j][0] > storePos[i])) {
                        count++;
                    }
                }
                storePoint[i] = count;
            }
        }
        int max = 0;
        int yPos = 0;
        for (int i = 0; i < p; i++) {
            if (storePoint[i] > max) {
                max = storePoint[i];
                yPos = storePos[i];
            } else if (storePoint[i] == max) {
                if (storePos[i] < yPos) {
                    yPos = storePos[i];
                }
            }
        }

        storePoint = new int[p];
        storePos = new int[p];
        for (int i = 0; i < p; i++) {
            if (dir[i].equals("W")) {
                storePos[i] = 0;
                int count = 0;
                for (int j = 0; j < p; j++) {
                    if (dir[j].equals("W")) {
                        count++;
                    }
                }
                storePoint[i] = count;
            } else if (dir[i].equals("E")) {
                storePos[i] = pos[i][1] + 1;
                int count = 0;
                for (int j = 0; j < p; j++ ) {
                    if ((dir[j].equals("E") && pos[j][1] <= pos[i][1]) || (dir[j].equals("W") && pos[j][1] > storePos[i])) {
                        count++;
                    }
                }
                storePoint[i] = count;
            }
        }

        int xPos = 0;
        max = 0;
        for (int i = 0; i < p; i++) {
            if (storePoint[i] == max) {
                if (storePos[i] < xPos) {
                    xPos = storePos[i];
                }
            } else if (storePoint[i] > max) {
                max = storePoint[i];
                xPos = storePos[i];
            }
        }
        return new int[] {xPos, yPos};
    }

    static class GeneralDirection {
        int pos;
        int up; // E or N
        int down; // W or S
        GeneralDirection(int pos, int up, int down) {
            this.down =down; this.up = up; this.pos = pos;
        }
        void increaseUp() {this.up++;}
        void increaseDown() {this.down++;}
        int getPos() {return this.pos;}
    }
    static class PosVal {
        int pos;
        int val;
        PosVal(int pos, int val) {
            this.pos = pos;
            this.val = val;
        }
    }

    static int[] findStoreV3(int[][] pos, String[] dir, int p, int q) {
        Map<Integer, GeneralDirection> map = new HashMap<>();
        Map<Integer, PosVal> valMap = new HashMap<>();
        for (int i = 0; i < p; i++) {
            if (dir[i].equals("S") || dir[i].equals("N")) {
                int y = pos[i][0];
                if (map.containsKey(y)) {
                    if (dir[i].equals("S")) {
                        map.get(y).increaseDown();
                    } else {
                        map.get(y).increaseUp();
                    }
                } else {
                    if (dir[i].equals("S")) {
                        map.put(y, new GeneralDirection(y, 0, 1));
                    } else {
                        map.put(y, new GeneralDirection(y, 1, 0));
                    }
                }
            }
        }
        List<GeneralDirection> dirList = new ArrayList<>(map.values());
        Collections.sort(dirList, Comparator.comparing(GeneralDirection::getPos));
        // Suppose value at y = 0 is 0 to not have to calculate the value it is
        // The value of it is not important.
        // We care about how value of other pos changes from this value
        int max = 0;
        int yPos = 0;
        int val = 0;
        for (GeneralDirection direction : dirList) {
            // Value at 0 = number of down;

            val = val - direction.down;
            valMap.put(direction.pos, new PosVal(direction.pos, val));
            val = val + direction.up;
            valMap.put(direction.pos + 1, new PosVal(direction.pos + 1, val));
        }
        for (PosVal posVal : valMap.values()) {
            if (posVal.val > max) {
                max = posVal.val;
                yPos = posVal.pos;
            }
        }

        map.clear();
        valMap.clear();
        for (int i = 0; i < p; i++) {
            if (dir[i].equals("W") || dir[i].equals("E")) {
                int x = pos[i][1];
                if (map.containsKey(x)) {
                    if (dir[i].equals("W")) {
                        map.get(x).increaseDown();
                    } else {
                        map.get(x).increaseUp();
                    }
                } else {
                    if (dir[i].equals("W")) {
                        map.put(x, new GeneralDirection(x, 0, 1));
                    } else {
                        map.put(x, new GeneralDirection(x, 1, 0));
                    }
                }
            }
        }
        dirList = new ArrayList<>(map.values());
        Collections.sort(dirList, Comparator.comparing(GeneralDirection::getPos));
        max = 0;
        int xPos = 0;
        val = 0;
        for (GeneralDirection direction : dirList) {
            // Value at 0 = number of down;

            val = val - direction.down;
            valMap.put(direction.pos, new PosVal(direction.pos, val));
            val = val + direction.up;
            valMap.put(direction.pos+1, new PosVal(direction.pos+1, val));
        }
        for (PosVal posVal : valMap.values()) {
            if (posVal.val > max) {
                max = posVal.val;
                xPos = posVal.pos;
            }
        }
        return new int[] {xPos, yPos};
    }

    public static void main(String[] args) throws Exception{
        // mahattan_crepe.txt
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("mahattan_crepe.txt")));
//        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int round = scanner.nextInt();
        for (int i = 0; i < round; i++) {
            int p = scanner.nextInt();
            int q = scanner.nextInt();
            String[] dir = new String[p];
            int[][] pos = new int[p][2];
            for (int j = 0; j < p; j++) {
                pos[j][1] = scanner.nextInt();
                pos[j][0] = scanner.nextInt();
                dir[j] = scanner.next();
            }
            int[] result = findStoreV3(pos, dir, p, q);
            System.out.printf("Case #%d: %d %d\n", i + 1, result[0], result[1]);
        }
    }
}
