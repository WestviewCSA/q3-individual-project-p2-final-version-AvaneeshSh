import java.util.ArrayDeque;
import java.util.Queue;

public class SolveQueue {

    public static boolean solve(String[][][] grid) {

        int[] start = Reader.findChar(grid, "W");
        int[] goal  = Reader.findChar(grid, "$");

        if (start == null || goal == null) {
            System.out.println("The Wolverine Store is closed.");
            return false;
        }

        int maps = grid.length;
        int rows = grid[0].length;
        int cols = grid[0][0].length;

        boolean[][][] visited = new boolean[maps][rows][cols];
        int[][][] parent      = new int[maps][rows][cols];

        for (int m = 0; m < maps; m++) {
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    parent[m][r][c] = -1;
                }
            }
        }

        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{start[0], start[1], start[2]});
        visited[start[0]][start[1]][start[2]] = true;
        parent[start[0]][start[1]][start[2]]  = -2;

        for (int m = 1; m < maps; m++) {
            int[] otherW = Reader.findCharInMaze(grid, "W", m);
            if (otherW != null) {
                visited[otherW[0]][otherW[1]][otherW[2]] = false;
            }
        }

        while (!queue.isEmpty()) {

            int[] cur = queue.poll();
            int m = cur[0];
            int r = cur[1];
            int c = cur[2];

            // North
            int northRow  = r - 1;
            int northCol  = c;
            int northMaze = m;
            if (northRow >= 0 && northRow < rows && northCol >= 0 && northCol < cols) {
                String northCell = grid[northMaze][northRow][northCol];
                if (!northCell.equals("@") && !visited[northMaze][northRow][northCol]) {
                    visited[northMaze][northRow][northCol] = true;
                    parent[northMaze][northRow][northCol]  = m * 10000 + r * 100 + c;

                    if (northCell.equals("$")) {
                        int tm = northMaze;
                        int tr = northRow;
                        int tc = northCol;
                        while (parent[tm][tr][tc] != -2) {
                            if (!grid[tm][tr][tc].equals("W") && !grid[tm][tr][tc].equals("$"))
                                grid[tm][tr][tc] = "+";
                            int prev = parent[tm][tr][tc];
                            tm = prev / 10000;
                            tr = (prev % 10000) / 100;
                            tc = prev % 100;
                        }
                        return true;
                    }

                    if (northCell.equals("|")) {
                        queue.add(new int[]{northMaze, northRow, northCol});
                        for (int om = 0; om < maps; om++) {
                            if (om != northMaze) {
                                for (int wc = 0; wc < cols; wc++) {
                                    if (grid[om][northRow][wc].equals("W") && !visited[om][northRow][wc]) {
                                        visited[om][northRow][wc] = true;
                                        parent[om][northRow][wc]  = m * 10000 + r * 100 + c;
                                        queue.add(new int[]{om, northRow, wc});
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    } else {
                        queue.add(new int[]{northMaze, northRow, northCol});
                    }
                }
            }

            // South
            int southRow  = r + 1;
            int southCol  = c;
            int southMaze = m;
            if (southRow >= 0 && southRow < rows && southCol >= 0 && southCol < cols) {
                String southCell = grid[southMaze][southRow][southCol];
                if (!southCell.equals("@") && !visited[southMaze][southRow][southCol]) {
                    visited[southMaze][southRow][southCol] = true;
                    parent[southMaze][southRow][southCol]  = m * 10000 + r * 100 + c;

                    if (southCell.equals("$")) {
                        int tm = southMaze;
                        int tr = southRow;
                        int tc = southCol;
                        while (parent[tm][tr][tc] != -2) {
                            if (!grid[tm][tr][tc].equals("W") && !grid[tm][tr][tc].equals("$"))
                                grid[tm][tr][tc] = "+";
                            int prev = parent[tm][tr][tc];
                            tm = prev / 10000;
                            tr = (prev % 10000) / 100;
                            tc = prev % 100;
                        }
                        return true;
                    }

                    if (southCell.equals("|")) {
                        queue.add(new int[]{southMaze, southRow, southCol});
                        for (int om = 0; om < maps; om++) {
                            if (om != southMaze) {
                                for (int wc = 0; wc < cols; wc++) {
                                    if (grid[om][southRow][wc].equals("W") && !visited[om][southRow][wc]) {
                                        visited[om][southRow][wc] = true;
                                        parent[om][southRow][wc]  = m * 10000 + r * 100 + c;
                                        queue.add(new int[]{om, southRow, wc});
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    } else {
                        queue.add(new int[]{southMaze, southRow, southCol});
                    }
                }
            }

            // East
            int eastRow  = r;
            int eastCol  = c + 1;
            int eastMaze = m;
            if (eastRow >= 0 && eastRow < rows && eastCol >= 0 && eastCol < cols) {
                String eastCell = grid[eastMaze][eastRow][eastCol];
                if (!eastCell.equals("@") && !visited[eastMaze][eastRow][eastCol]) {
                    visited[eastMaze][eastRow][eastCol] = true;
                    parent[eastMaze][eastRow][eastCol]  = m * 10000 + r * 100 + c;

                    if (eastCell.equals("$")) {
                        int tm = eastMaze;
                        int tr = eastRow;
                        int tc = eastCol;
                        while (parent[tm][tr][tc] != -2) {
                            if (!grid[tm][tr][tc].equals("W") && !grid[tm][tr][tc].equals("$"))
                                grid[tm][tr][tc] = "+";
                            int prev = parent[tm][tr][tc];
                            tm = prev / 10000;
                            tr = (prev % 10000) / 100;
                            tc = prev % 100;
                        }
                        return true;
                    }

                    if (eastCell.equals("|")) {
                        queue.add(new int[]{eastMaze, eastRow, eastCol});
                        for (int om = 0; om < maps; om++) {
                            if (om != eastMaze) {
                                for (int wc = 0; wc < cols; wc++) {
                                    if (grid[om][eastRow][wc].equals("W") && !visited[om][eastRow][wc]) {
                                        visited[om][eastRow][wc] = true;
                                        parent[om][eastRow][wc]  = m * 10000 + r * 100 + c;
                                        queue.add(new int[]{om, eastRow, wc});
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    } else {
                        queue.add(new int[]{eastMaze, eastRow, eastCol});
                    }
                }
            }

            // West
            int westRow  = r;
            int westCol  = c - 1;
            int westMaze = m;
            if (westRow >= 0 && westRow < rows && westCol >= 0 && westCol < cols) {
                String westCell = grid[westMaze][westRow][westCol];
                if (!westCell.equals("@") && !visited[westMaze][westRow][westCol]) {
                    visited[westMaze][westRow][westCol] = true;
                    parent[westMaze][westRow][westCol]  = m * 10000 + r * 100 + c;

                    if (westCell.equals("$")) {
                        int tm = westMaze;
                        int tr = westRow;
                        int tc = westCol;
                        while (parent[tm][tr][tc] != -2) {
                            if (!grid[tm][tr][tc].equals("W") && !grid[tm][tr][tc].equals("$"))
                                grid[tm][tr][tc] = "+";
                            int prev = parent[tm][tr][tc];
                            tm = prev / 10000;
                            tr = (prev % 10000) / 100;
                            tc = prev % 100;
                        }
                        return true;
                    }

                    if (westCell.equals("|")) {
                        queue.add(new int[]{westMaze, westRow, westCol});
                        for (int om = 0; om < maps; om++) {
                            if (om != westMaze) {
                                for (int wc = 0; wc < cols; wc++) {
                                    if (grid[om][westRow][wc].equals("W") && !visited[om][westRow][wc]) {
                                        visited[om][westRow][wc] = true;
                                        parent[om][westRow][wc]  = m * 10000 + r * 100 + c;
                                        queue.add(new int[]{om, westRow, wc});
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    } else {
                        queue.add(new int[]{westMaze, westRow, westCol});
                    }
                }
            }
        }

        System.out.println("The Wolverine Store is closed.");
        return false;
    }
}