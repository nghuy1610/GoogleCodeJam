package y2019.round1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Pylons {
    static class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static boolean traverse(boolean[][] grid, Point cur, List<Point> path) {
        int row = grid.length;
        int col = grid[0].length;

        if (path.size() == row * col) {
            return true;
        }
        // Use Set instead of List here to randomly pick next possible move and then pass test set 2
        Set<Point> posMoves = new HashSet<>();
        for (int p = 0; p < row; p++) {
            for (int q = 0; q < col; q++) {
                if (grid[p][q]) {
                    continue;
                }
                if (p != cur.x && q != cur.y && p - q != cur.x - cur.y && p + q != cur.x + cur.y) {
                    posMoves.add(new Point(p, q));
                }
            }
        }
        for (Point point : posMoves) {
            int x = point.x;
            int y = point.y;
            path.add(point);
            grid[x][y] = true;
            if (traverse(grid, point, path)) {
                return true;
            } else {
                path.remove(path.size() - 1);
                grid[x][y] = false;
            }
        }
        return false;
    }

    // Backtracking solution
    private static List<Point> findPath(int row, int col) {
        List<Point> path = new ArrayList<>();
        boolean[][] grid = new boolean[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                grid[i][j] = false;
            }
        }
        grid[0][0] = true; // already go through
        path.add(new Point(0, 0));
        if (traverse(grid, new Point(0, 0), path)) {
            System.out.println("POSSIBLE");
            return path;
        } else {
            System.out.println("IMPOSSIBLE");
            return new ArrayList<>();
        }
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int round = scanner.nextInt();
        for (int i = 0; i < round; i++) {
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            System.out.printf("Case #%d: ", i + 1);
            List<Point> result = findPath(row, col);
            for (Point point : result) {
                System.out.printf("%d %d\n", point.x + 1, point.y + 1); // Row and col start from 1
            }

        }
    }
}
