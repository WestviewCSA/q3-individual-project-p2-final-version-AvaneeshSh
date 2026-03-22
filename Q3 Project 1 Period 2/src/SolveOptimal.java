import java.util.ArrayDeque;
import java.util.Queue;

public class SolveOptimal {

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

        boolean[][][] visitedForward  = new boolean[maps][rows][cols];
        boolean[][][] visitedBackward = new boolean[maps][rows][cols];
        int[][][] parentForward = new int[maps][rows][cols];
        int[][][] parentBackward = new int[maps][rows][cols];

        for (int m = 0; m < maps; m++) {
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    parentForward[m][r][c]  = -1;
                    parentBackward[m][r][c] = -1;
                }
            }
        }

        Queue<int[]> forwardQueue = new ArrayDeque<>();
        Queue<int[]> backwardQueue = new ArrayDeque<>();

        forwardQueue.add(new int[]{start[0], start[1], start[2]});
        visitedForward[start[0]][start[1]][start[2]] = true;
        parentForward[start[0]][start[1]][start[2]]  = -2;

        for (int m = 1; m < maps; m++) {
            int[] otherW = Reader.findCharInMaze(grid, "W", m);
            if (otherW != null) {
                visitedForward[otherW[0]][otherW[1]][otherW[2]] = false;
            }
        }

        backwardQueue.add(new int[]{goal[0], goal[1], goal[2]});
        visitedBackward[goal[0]][goal[1]][goal[2]] = true;
        parentBackward[goal[0]][goal[1]][goal[2]]  = -2;

        int[] meetPoint = null;

        while (!forwardQueue.isEmpty() && !backwardQueue.isEmpty()) {

            if (meetPoint != null) break;

            // ===== ONE STEP FORWARD =====
            int[] curF = forwardQueue.poll();
            int mF = curF[0];
            int rF = curF[1];
            int cF = curF[2];

            // North forward
            int northRowF  = rF - 1;
            int northColF  = cF;
            int northMazeF = mF;
            if (northRowF >= 0 && northRowF < rows && northColF >= 0 && northColF < cols) {
                String northCellF = grid[northMazeF][northRowF][northColF];
                if (!northCellF.equals("@") && !visitedForward[northMazeF][northRowF][northColF]) {
                    visitedForward[northMazeF][northRowF][northColF] = true;
                    parentForward[northMazeF][northRowF][northColF]  = mF * 10000 + rF * 100 + cF;

                    if (visitedBackward[northMazeF][northRowF][northColF]) {
                        meetPoint = new int[]{northMazeF, northRowF, northColF};
                    }

                    if (northCellF.equals("|")) {
                        forwardQueue.add(new int[]{northMazeF, northRowF, northColF});
                        for (int om = 0; om < maps; om++) {
                            if (om != northMazeF) {
                                for (int wc = 0; wc < cols; wc++) {
                                    if (grid[om][northRowF][wc].equals("W") && !visitedForward[om][northRowF][wc]) {
                                        visitedForward[om][northRowF][wc] = true;
                                        parentForward[om][northRowF][wc]  = mF * 10000 + rF * 100 + cF;
                                        forwardQueue.add(new int[]{om, northRowF, wc});
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    } else {
                        forwardQueue.add(new int[]{northMazeF, northRowF, northColF});
                    }
                }
            }

            // South forward
            int southRowF  = rF + 1;
            int southColF  = cF;
            int southMazeF = mF;
            if (southRowF >= 0 && southRowF < rows && southColF >= 0 && southColF < cols) {
                String southCellF = grid[southMazeF][southRowF][southColF];
                if (!southCellF.equals("@") && !visitedForward[southMazeF][southRowF][southColF]) {
                    visitedForward[southMazeF][southRowF][southColF] = true;
                    parentForward[southMazeF][southRowF][southColF]  = mF * 10000 + rF * 100 + cF;

                    if (visitedBackward[southMazeF][southRowF][southColF]) {
                        meetPoint = new int[]{southMazeF, southRowF, southColF};
                    }

                    if (southCellF.equals("|")) {
                        forwardQueue.add(new int[]{southMazeF, southRowF, southColF});
                        for (int om = 0; om < maps; om++) {
                            if (om != southMazeF) {
                                for (int wc = 0; wc < cols; wc++) {
                                    if (grid[om][southRowF][wc].equals("W") && !visitedForward[om][southRowF][wc]) {
                                        visitedForward[om][southRowF][wc] = true;
                                        parentForward[om][southRowF][wc]  = mF * 10000 + rF * 100 + cF;
                                        forwardQueue.add(new int[]{om, southRowF, wc});
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    } else {
                        forwardQueue.add(new int[]{southMazeF, southRowF, southColF});
                    }
                }
            }

            // East forward
            int eastRowF  = rF;
            int eastColF  = cF + 1;
            int eastMazeF = mF;
            if (eastRowF >= 0 && eastRowF < rows && eastColF >= 0 && eastColF < cols) {
                String eastCellF = grid[eastMazeF][eastRowF][eastColF];
                if (!eastCellF.equals("@") && !visitedForward[eastMazeF][eastRowF][eastColF]) {
                    visitedForward[eastMazeF][eastRowF][eastColF] = true;
                    parentForward[eastMazeF][eastRowF][eastColF]  = mF * 10000 + rF * 100 + cF;

                    if (visitedBackward[eastMazeF][eastRowF][eastColF]) {
                        meetPoint = new int[]{eastMazeF, eastRowF, eastColF};
                    }

                    if (eastCellF.equals("|")) {
                        forwardQueue.add(new int[]{eastMazeF, eastRowF, eastColF});
                        for (int om = 0; om < maps; om++) {
                            if (om != eastMazeF) {
                                for (int wc = 0; wc < cols; wc++) {
                                    if (grid[om][eastRowF][wc].equals("W") && !visitedForward[om][eastRowF][wc]) {
                                        visitedForward[om][eastRowF][wc] = true;
                                        parentForward[om][eastRowF][wc]  = mF * 10000 + rF * 100 + cF;
                                        forwardQueue.add(new int[]{om, eastRowF, wc});
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    } else {
                        forwardQueue.add(new int[]{eastMazeF, eastRowF, eastColF});
                    }
                }
            }

            // West forward
            int westRowF  = rF;
            int westColF  = cF - 1;
            int westMazeF = mF;
            if (westRowF >= 0 && westRowF < rows && westColF >= 0 && westColF < cols) {
                String westCellF = grid[westMazeF][westRowF][westColF];
                if (!westCellF.equals("@") && !visitedForward[westMazeF][westRowF][westColF]) {
                    visitedForward[westMazeF][westRowF][westColF] = true;
                    parentForward[westMazeF][westRowF][westColF]  = mF * 10000 + rF * 100 + cF;

                    if (visitedBackward[westMazeF][westRowF][westColF]) {
                        meetPoint = new int[]{westMazeF, westRowF, westColF};
                    }

                    if (westCellF.equals("|")) {
                        forwardQueue.add(new int[]{westMazeF, westRowF, westColF});
                        for (int om = 0; om < maps; om++) {
                            if (om != westMazeF) {
                                for (int wc = 0; wc < cols; wc++) {
                                    if (grid[om][westRowF][wc].equals("W") && !visitedForward[om][westRowF][wc]) {
                                        visitedForward[om][westRowF][wc] = true;
                                        parentForward[om][westRowF][wc]  = mF * 10000 + rF * 100 + cF;
                                        forwardQueue.add(new int[]{om, westRowF, wc});
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    } else {
                        forwardQueue.add(new int[]{westMazeF, westRowF, westColF});
                    }
                }
            }

            if (meetPoint != null) break;

            // ===== ONE STEP BACKWARD =====
            int[] curB = backwardQueue.poll();
            int mB = curB[0];
            int rB = curB[1];
            int cB = curB[2];

            // North backward
            int northRowB  = rB - 1;
            int northColB  = cB;
            int northMazeB = mB;
            if (northRowB >= 0 && northRowB < rows && northColB >= 0 && northColB < cols) {
                String northCellB = grid[northMazeB][northRowB][northColB];
                if (!northCellB.equals("@") && !visitedBackward[northMazeB][northRowB][northColB]) {
                    visitedBackward[northMazeB][northRowB][northColB] = true;
                    parentBackward[northMazeB][northRowB][northColB]  = mB * 10000 + rB * 100 + cB;

                    if (visitedForward[northMazeB][northRowB][northColB]) {
                        meetPoint = new int[]{northMazeB, northRowB, northColB};
                    }

                    if (northCellB.equals("|")) {
                        backwardQueue.add(new int[]{northMazeB, northRowB, northColB});
                        for (int om = 0; om < maps; om++) {
                            if (om != northMazeB) {
                                for (int wc = 0; wc < cols; wc++) {
                                    if (grid[om][northRowB][wc].equals("W") && !visitedBackward[om][northRowB][wc]) {
                                        visitedBackward[om][northRowB][wc] = true;
                                        parentBackward[om][northRowB][wc]  = mB * 10000 + rB * 100 + cB;
                                        backwardQueue.add(new int[]{om, northRowB, wc});
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    } else {
                        backwardQueue.add(new int[]{northMazeB, northRowB, northColB});
                    }
                }
            }

            // South backward
            int southRowB  = rB + 1;
            int southColB  = cB;
            int southMazeB = mB;
            if (southRowB >= 0 && southRowB < rows && southColB >= 0 && southColB < cols) {
                String southCellB = grid[southMazeB][southRowB][southColB];
                if (!southCellB.equals("@") && !visitedBackward[southMazeB][southRowB][southColB]) {
                    visitedBackward[southMazeB][southRowB][southColB] = true;
                    parentBackward[southMazeB][southRowB][southColB]  = mB * 10000 + rB * 100 + cB;

                    if (visitedForward[southMazeB][southRowB][southColB]) {
                        meetPoint = new int[]{southMazeB, southRowB, southColB};
                    }

                    if (southCellB.equals("|")) {
                        backwardQueue.add(new int[]{southMazeB, southRowB, southColB});
                        for (int om = 0; om < maps; om++) {
                            if (om != southMazeB) {
                                for (int wc = 0; wc < cols; wc++) {
                                    if (grid[om][southRowB][wc].equals("W") && !visitedBackward[om][southRowB][wc]) {
                                        visitedBackward[om][southRowB][wc] = true;
                                        parentBackward[om][southRowB][wc]  = mB * 10000 + rB * 100 + cB;
                                        backwardQueue.add(new int[]{om, southRowB, wc});
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    } else {
                        backwardQueue.add(new int[]{southMazeB, southRowB, southColB});
                    }
                }
            }

            // East backward
            int eastRowB  = rB;
            int eastColB  = cB + 1;
            int eastMazeB = mB;
            if (eastRowB >= 0 && eastRowB < rows && eastColB >= 0 && eastColB < cols) {
                String eastCellB = grid[eastMazeB][eastRowB][eastColB];
                if (!eastCellB.equals("@") && !visitedBackward[eastMazeB][eastRowB][eastColB]) {
                    visitedBackward[eastMazeB][eastRowB][eastColB] = true;
                    parentBackward[eastMazeB][eastRowB][eastColB]  = mB * 10000 + rB * 100 + cB;

                    if (visitedForward[eastMazeB][eastRowB][eastColB]) {
                        meetPoint = new int[]{eastMazeB, eastRowB, eastColB};
                    }

                    if (eastCellB.equals("|")) {
                        backwardQueue.add(new int[]{eastMazeB, eastRowB, eastColB});
                        for (int om = 0; om < maps; om++) {
                            if (om != eastMazeB) {
                                for (int wc = 0; wc < cols; wc++) {
                                    if (grid[om][eastRowB][wc].equals("W") && !visitedBackward[om][eastRowB][wc]) {
                                        visitedBackward[om][eastRowB][wc] = true;
                                        parentBackward[om][eastRowB][wc]  = mB * 10000 + rB * 100 + cB;
                                        backwardQueue.add(new int[]{om, eastRowB, wc});
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    } else {
                        backwardQueue.add(new int[]{eastMazeB, eastRowB, eastColB});
                    }
                }
            }

            // West backward
            int westRowB  = rB;
            int westColB  = cB - 1;
            int westMazeB = mB;
            if (westRowB >= 0 && westRowB < rows && westColB >= 0 && westColB < cols) {
                String westCellB = grid[westMazeB][westRowB][westColB];
                if (!westCellB.equals("@") && !visitedBackward[westMazeB][westRowB][westColB]) {
                    visitedBackward[westMazeB][westRowB][westColB] = true;
                    parentBackward[westMazeB][westRowB][westColB]  = mB * 10000 + rB * 100 + cB;

                    if (visitedForward[westMazeB][westRowB][westColB]) {
                        meetPoint = new int[]{westMazeB, westRowB, westColB};
                    }

                    if (westCellB.equals("|")) {
                        backwardQueue.add(new int[]{westMazeB, westRowB, westColB});
                        for (int om = 0; om < maps; om++) {
                            if (om != westMazeB) {
                                for (int wc = 0; wc < cols; wc++) {
                                    if (grid[om][westRowB][wc].equals("W") && !visitedBackward[om][westRowB][wc]) {
                                        visitedBackward[om][westRowB][wc] = true;
                                        parentBackward[om][westRowB][wc]  = mB * 10000 + rB * 100 + cB;
                                        backwardQueue.add(new int[]{om, westRowB, wc});
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    } else {
                        backwardQueue.add(new int[]{westMazeB, westRowB, westColB});
                    }
                }
            }
        }

        if (meetPoint == null) {
            System.out.println("The Wolverine Store is closed.");
            return false;
        }

        int tm = meetPoint[0];
        int tr = meetPoint[1];
        int tc = meetPoint[2];
        while (parentForward[tm][tr][tc] != -2) {
            if (!grid[tm][tr][tc].equals("W") && !grid[tm][tr][tc].equals("$")) {
                grid[tm][tr][tc] = "+";
            }
            int prev = parentForward[tm][tr][tc];
            tm = prev / 10000;
            tr = (prev % 10000) / 100;
            tc = prev % 100;
        }

        tm = meetPoint[0];
        tr = meetPoint[1];
        tc = meetPoint[2];
        while (parentBackward[tm][tr][tc] != -2) {
            if (!grid[tm][tr][tc].equals("W") && !grid[tm][tr][tc].equals("$")) {
                grid[tm][tr][tc] = "+";
            }
            int prev = parentBackward[tm][tr][tc];
            tm = prev / 10000;
            tr = (prev % 10000) / 100;
            tc = prev % 100;
        }

        return true;
    }
}