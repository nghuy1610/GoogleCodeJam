package y2020.round1a;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class SquareDance {
    static class Pair {
        int x;
        int y;
        Pair(int x, int y) {
            this.x = x; this.y = y;
        }
    }

    static int solve(Scanner scanner) {
        int r = scanner.nextInt();
        int c = scanner.nextInt();
        int[][] mat = new int[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0;j < c; j++) {
                mat[i][j] = scanner.nextInt();
            }
        }
        List<Pair> outP = new ArrayList<>();
        int interest = 0;
        do {
            for (Pair pair : outP) {
                mat[pair.x][pair.y] = 0;
            }
            outP.clear();
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    if (mat[i][j] == 0) {
                        continue;
                    }
                    interest += mat[i][j];
                    int sum = 0;
                    int count = 0;
                    int temp = 1;
                    while (i + temp < r && mat[i + temp][j] == 0 ) {
                        temp++;
                    }
                    if (i + temp < r && mat[i + temp][j] != 0) {
                        sum += mat[i+temp][j];
                        count++;
                    }
                    temp = 1;
                    while (i - temp >= 0 && mat[i - temp][j] == 0 ) {
                        temp++;
                    }
                    if (i - temp >= 0 && mat[i - temp][j] != 0) {
                        sum += mat[i-temp][j];
                        count++;
                    }
                    temp = 1;
                    while (j + temp < c && mat[i][j + temp] == 0) {
                        temp++;
                    }
                    if (j + temp < c && mat[i][j + temp] != 0) {
                        sum += mat[i][j+temp];
                        count++;
                    }
                    temp = 1;
                    while (j- temp >= 0 && mat[i][j-temp] == 0) {
                        temp++;
                    }
                    if (j- temp >= 0 && mat[i][j-temp] != 0) {
                        sum += mat[i][j-temp];
                        count++;
                    }
                    if (mat[i][j] * count < sum) {
                        outP.add(new Pair(i, j));
                    }
                }
            }
        } while (!outP.isEmpty());
        return interest;
    }


    static int solveV2(Scanner scanner) {
        int r = scanner.nextInt();
        int c = scanner.nextInt();
        int[][] mat = new int[r][c];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                mat[i][j] = scanner.nextInt();
            }
        }
        List<Pair> out = new ArrayList<>();
        boolean[][] outCan = new boolean[r][c];
        int interest = 0 ;
        while(true) {
            out.clear();
            for (int i = 0; i < r; i++) {
                for (int j = 0; j < c; j++) {
                    if (outCan[i][j]) {
                        continue;
                    }
                    interest += mat[i][j];
                    int count = 0;
                    int sum = 0;
                    int tx = i + 1, ty = j;
                    while (tx < r && outCan[tx][ty]) {
                        tx++;
                    }
                    if (tx < r && !outCan[tx][ty]) {
                        sum += mat[tx][ty];
                        count++;
                    }
                    tx = i -1;
                    while(tx >= 0 && outCan[tx][ty]) {
                        tx--;
                    }
                    if (tx >= 0 && !outCan[tx][ty]) {
                        sum += mat[tx][ty];
                        count++;
                    }
                    tx = i;
                    ty = j +1 ;
                    while(ty < c && outCan[tx][ty]) {
                        ty++;
                    }
                    if (ty < c && !outCan[tx][ty]) {
                        sum += mat[tx][ty];
                        count++;
                    }
                    ty = j - 1;
                    while(ty >= 0 && outCan[tx][ty]) {
                        ty--;
                    }
                    if (ty >= 0 && !outCan[tx][ty]) {
                        sum += mat[tx][ty];
                        count++;
                    }
                    if (count == 0) {
                        continue;
                    } else if (mat[i][j] * count < sum) {
                        out.add(new Pair(i, j));
                    }
                }
            }
            if (out.isEmpty()) {
                break;
            } else {
                for (Pair pair : out) {
                    outCan[pair.x][pair.y] = true;
                }
            }
        }
        return interest;
    }


    static class Node{
        Node next;
        Node prev;
        int i;
        int j;
    }

    static class Grid {
        Node rNode;
        Node cNode;
    }
    static int solveV3(Scanner scanner) {
        int r = scanner.nextInt();
        int c = scanner.nextInt();
        int[][] mat = new int[r][c];
        int interest = 0;
        List<Pair> chkPos = new ArrayList<>();
        Set<Pair> outPos = new HashSet<>();
        Grid[][] grid = new Grid[r][c];
        for (int i = 0; i < r ; i++) {
            for (int j = 0; j < c; j++) {
                mat[i][j] = scanner.nextInt();
                interest += mat[i][j];
                chkPos.add(new Pair(i, j));
                grid[i][j] = new Grid();
            }
        }
        int lastInt = interest;
        Node[] rNodes = new Node[r];
        Node[] cNodes = new Node[c];
        for (int i = 0; i < r; i++) {
            Node root = new Node();
            Node curN = root;
            for (int j = 0; j < c; j++) {
                if (j == 0) {
                    curN.i = i;
                    curN.j = j;
                } else {
                    Node temp = new Node();
                    temp.i = i;
                    temp.j = j;
                    curN.next = temp;
                    temp.prev = curN;
                    curN = temp;
                }
                grid[i][j].rNode = curN;
            }
            rNodes[i] = root;
        }
        for (int i = 0; i < c; i++) {
            Node root = new Node();
            Node cur = root;
            for (int j = 0; j < r; j++) {
                if (j == 0) {
                    cur.i = j;
                    cur.j = i;
                } else {
                    Node temp = new Node();
                    temp.i = j;
                    temp.j = i;
                    temp.prev = cur;
                    cur.next = temp;
                    cur = temp;
                }
                grid[j][i].cNode = cur;
            }
            cNodes[i] = root;
        }
        while (true) {
            outPos.clear();
            for (Pair pos : chkPos) {
                int count = 0;
                int sum = 0;
                Grid gridPos = grid[pos.x][pos.y];
                if (gridPos == null) {continue;}
                Node rPos = grid[pos.x][pos.y].rNode;
                Node cPos = grid[pos.x][pos.y].cNode;
                if (rPos == null && cPos == null) {
                    continue;
                }
                if (rPos.prev != null) {
                    count++;
                    sum += mat[rPos.prev.i][rPos.prev.j];
                }
                if (rPos.next != null) {
                    count++;
                    sum += mat[rPos.next.i][rPos.next.j];
                }
                if (cPos.prev != null) {
                    count++;
                    sum += mat[cPos.prev.i][cPos.prev.j];
                }
                if (cPos.next != null) {
                    count++;
                    sum += mat[cPos.next.i][cPos.next.j];
                }
                if (mat[pos.x][pos.y] * count < sum) {
                    outPos.add(pos);
                }
            }
            if (outPos.isEmpty()) {
                break;
            }
            int tempInt = lastInt;
            chkPos.clear();
            for (Pair pos : outPos) {
                Grid gridPos = grid[pos.x][pos.y];
                if (gridPos == null) {
                    continue;
                }
                tempInt -= mat[pos.x][pos.y];

                Node prevRNode = grid[pos.x][pos.y].rNode.prev;
                Node nextRNode = grid[pos.x][pos.y].rNode.next;
                Node prevCNode = grid[pos.x][pos.y].cNode.prev;
                Node nextCNode = grid[pos.x][pos.y].cNode.next;
                if (prevCNode != null) {
                    prevCNode.next = nextCNode;
                    chkPos.add(new Pair(prevCNode.i, prevCNode.j));
                }
                if (nextCNode != null) {
                    nextCNode.prev = prevCNode;
                    chkPos.add(new Pair(nextCNode.i, nextCNode.j));
                }
                if (prevRNode != null) {
                    prevRNode.next = nextRNode;
                    chkPos.add(new Pair(prevRNode.i, prevRNode.j));
                }
                if (nextRNode != null) {
                    nextRNode.prev = prevRNode;
                    chkPos.add(new Pair(nextRNode.i, nextRNode.j));
                }
                grid[pos.x][pos.y] = null;
            }
            interest += tempInt;
            lastInt = tempInt;
        }
        return interest;
    }

    public static void main(String[] args) throws Exception{
//        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("square_dance.txt")));
        int nr = scanner.nextInt();
        for (int i = 0; i < nr; i++) {
            System.out.printf("Case #%d: %d\n", i+1, solveV2(scanner));
        }
    }
}
