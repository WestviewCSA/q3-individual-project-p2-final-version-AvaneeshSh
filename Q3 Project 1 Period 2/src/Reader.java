import java.io.File;
import java.io.FileNotFoundException;
import java.util.Stack;
import java.util.Queue;
import java.util.Scanner;

public class Reader {

    public static void main(String[] args) {

    	try {
            String[][][] map = getText("HardMap2");
            for (int m = 0; m < map.length; m++) {
                System.out.println("--- Maze " + m + " ---");
                for (int i = 0; i < map[m].length; i++) {
                    for (int j = 0; j < map[m][0].length; j++) {
                        System.out.print(map[m][i][j]);
                    }
                    System.out.println();
                }
            }

            System.out.println();
            /*
             * 
             
            String[][][] coor = getCords("easyMap2 Coordinates");
            for (int m = 0; m < coor.length; m++) {
                System.out.println("--- Maze " + m + " ---");
                for (int i = 0; i < coor[m].length; i++) {
                    for (int j = 0; j < coor[m][0].length; j++) {
                        System.out.print(coor[m][i][j]);
                    }
                    System.out.println();
                }
            }
            */
            System.out.println("\n=== Stack Result ===");
            String[][][] stackGrid = copyGrid(map);
            boolean stackSolved = solveStack(stackGrid);
            if (stackSolved) {
                for (int m = 0; m < stackGrid.length; m++) {
                    for (int i = 0; i < stackGrid[m].length; i++) {
                        for (int j = 0; j < stackGrid[m][0].length; j++) {
                            System.out.print(stackGrid[m][i][j]);
                        }
                        System.out.println();
                    }
                }
            }

        } catch (IncorrectMapFormatException e) {
            System.out.println("Format error: " + e.getMessage());
        } catch (IllegalMapCharacterException e) {
            System.out.println("Character error: " + e.getMessage());
        } catch (IncompleteMapException e) {
            System.out.println("Incomplete map: " + e.getMessage());
        }
    }

    public static String[][][] getText(String passedFile) throws IncorrectMapFormatException, IllegalMapCharacterException, IncompleteMapException {

        File fileObj = new File(passedFile);

        try {
            Scanner scan = new Scanner(fileObj);

            if (!scan.hasNextInt()) {
                throw new IncorrectMapFormatException("First line must start with 3 positive integers.");
            }
            int rows = scan.nextInt();

            if (!scan.hasNextInt()) {
                throw new IncorrectMapFormatException("First line must have 3 positive integers.");
            }
            int columns = scan.nextInt();

            if (!scan.hasNextInt()) {
                throw new IncorrectMapFormatException("First line must have 3 positive integers.");
            }
            int maps = scan.nextInt();

            if (rows <= 0 || columns <= 0 || maps <= 0) {
                throw new IncorrectMapFormatException("Rows, columns, and maps must all be positive.");
            }
            scan.nextLine();

            String[][][] grid = new String[maps][rows][columns];

            int currentMaze = 0;
            int currentRow  = 0;

            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                if (line.isEmpty()) {
                	continue;
                }

                if (currentMaze >= maps) {
                    throw new IncompleteMapException("More rows in file than expected for " + maps + " maze(s).");
                }
                
                if (!line.matches("[.\\$W@|+]+")) {
                    throw new IllegalMapCharacterException("Invalid character on line: " + line);
                }
                
                if (line.length() != columns) {
                    throw new IncompleteMapException(
                        "Row " + currentRow + " in maze " + currentMaze +
                        " has " + line.length() + " characters but expected " + columns + ".");
                }
                
                for (int col = 0; col < columns; col++) {
                    grid[currentMaze][currentRow][col] = String.valueOf(line.charAt(col));
                }

                currentRow++;

                if (currentRow == rows) {
                    currentRow = 0;
                    currentMaze++;
                }
            }

            if (currentMaze != maps)
                throw new IncompleteMapException(
                    "Expected " + maps + " maze(s) but only found " + currentMaze + ".");

            return grid;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String[][][] getCords(String passedFile) throws IncorrectMapFormatException, IllegalMapCharacterException, IncompleteMapException {

        File fileObj = new File(passedFile);

        try {
        	Scanner scan = new Scanner(fileObj);

            if (!scan.hasNextInt()) {
                throw new IncorrectMapFormatException("First line must start with 3 positive integers.");
            }
            int rows = scan.nextInt();

            if (!scan.hasNextInt()) {
                throw new IncorrectMapFormatException("First line must have 3 positive integers.");
            }
            int columns = scan.nextInt();

            if (!scan.hasNextInt()) {
                throw new IncorrectMapFormatException("First line must have 3 positive integers.");
            }
            int maps = scan.nextInt();

            if (rows <= 0 || columns <= 0 || maps <= 0) {
                throw new IncorrectMapFormatException("Rows, columns, and maps must all be positive.");
            }
            scan.nextLine();

            String[][][] grid = new String[maps][rows][columns];

            while (scan.hasNextLine()) {
                String line = scan.nextLine().trim();
                if (line.isEmpty()) continue;
                
                

                String[] parts = line.split(" ");
                
                if (parts.length != 4) {
                    throw new IncorrectMapFormatException("Each line must have 4 values: char row col maze. Got: " + line);
                }
               
                String character = parts[0];
                int row = 0;
                int col = 0;
                int mazeLevel = 0;
                try {
                    row = Integer.parseInt(parts[1]);
                    col = Integer.parseInt(parts[2]);
                    mazeLevel = Integer.parseInt(parts[3]);
                } catch (NumberFormatException e) {
                    throw new IncorrectMapFormatException("Row, column, maze level must be integers. Got: " + line);
                }

				if (!character.matches("[.\\$W@|]")) {
                    throw new IllegalMapCharacterException("Invalid map character: " + character + " on line: " + line);
                }
                if (row < 0 || row >= rows || col < 0 || col >= columns) {
                    throw new IncompleteMapException("Coordinates out of bounds: " + line);
				}
                if (mazeLevel < 0 || mazeLevel >= maps) {
                    throw new IncompleteMapException("Maze level out of bounds: " + line);
				}
                grid[mazeLevel][row][col] = character;
            }

            for (int m = 0; m < maps; m++)
                for (int r = 0; r < rows; r++)
                    for (int c = 0; c < columns; c++)
                        if (grid[m][r][c] == null)
                            grid[m][r][c] = ".";

            return grid;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    
    public static int[] findChar(String[][][] grid, String target) {
        for (int m = 0; m < grid.length; m++) {
            for (int r = 0; r < grid[m].length; r++) {
            	for (int c = 0; c < grid[m][0].length; c++)
                if (grid[m][r][c].equals(target))
                {
                	int[] vals = new int[3];
                	vals[0] = m;
        			vals[1] = r;
        			vals[2] = c;
        			return vals;
                }
            }
        }
        return null;
    }
    
    public static String[][][] copyGrid(String[][][] grid) {
        String[][][] copy = new String[grid.length][grid[0].length][grid[0][0].length];
        for (int m = 0; m < grid.length; m++)
            for (int r = 0; r < grid[0].length; r++)
                for (int c = 0; c < grid[0][0].length; c++)
                    copy[m][r][c] = grid[m][r][c];
        return copy;
    }

    public static void printGrid(String[][][] grid) {
        for (int m = 0; m < grid.length; m++)
            for (int r = 0; r < grid[m].length; r++) {
                for (int c = 0; c < grid[m][0].length; c++)
                    System.out.print(grid[m][r][c]);
                System.out.println();
            }
    }
    
    public static boolean solveStack(String[][][] grid) {

        int[] start = findChar(grid, "W");
        int[] goal  = findChar(grid, "$");

        if (start == null || goal == null) {
            System.out.println("The Wolverine Store is closed.");
            return false;
        }

        int maps = grid.length;
        int rows = grid[0].length;
        int cols = grid[0][0].length;

        boolean[][][] visited = new boolean[maps][rows][cols];
        int[][][] parent = new int[maps][rows][cols];

        for (int m = 0; m < maps; m++) {
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    parent[m][r][c] = -1;
                }
            }
        }

        Stack<int[]> stack = new Stack<>();
        stack.push(new int[]{start[0], start[1], start[2]});
        visited[start[0]][start[1]][start[2]] = true;
        parent[start[0]][start[1]][start[2]]  = -2;

        while (!stack.isEmpty()) {

            int[] cur = stack.pop();
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
                            if (!grid[tm][tr][tc].equals("W") && !grid[tm][tr][tc].equals("$")) {
                                grid[tm][tr][tc] = "+";
                            }
                            int prev = parent[tm][tr][tc];
                            tm = prev / 10000;
                            tr = (prev % 10000) / 100;
                            tc = prev % 100;
                        }
                        return true;
                    }

                    if (northCell.equals("|")) {
                        stack.push(new int[]{northMaze, northRow, northCol});
                        for (int om = 0; om < maps; om++) {
                            if (om != northMaze) {
                                for (int wc = 0; wc < cols; wc++) {
                                    if (grid[om][northRow][wc].equals("|") && !visited[om][northRow][wc]) {
                                        visited[om][northRow][wc] = true;
                                        parent[om][northRow][wc]  = m * 10000 + r * 100 + c;
                                        stack.push(new int[]{om, northRow, wc});
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    } else {
                        stack.push(new int[]{northMaze, northRow, northCol});
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
                            if (!grid[tm][tr][tc].equals("W") && !grid[tm][tr][tc].equals("$")) {
                                grid[tm][tr][tc] = "+";
                            }
                            int prev = parent[tm][tr][tc];
                            tm = prev / 10000;
                            tr = (prev % 10000) / 100;
                            tc = prev % 100;
                        }
                        return true;
                    }

                    if (southCell.equals("|")) {
                        stack.push(new int[]{southMaze, southRow, southCol});
                        for (int om = 0; om < maps; om++) {
                            if (om != southMaze) {
                                for (int wc = 0; wc < cols; wc++) {
                                    if (grid[om][southRow][wc].equals("|") && !visited[om][southRow][wc]) {
                                        visited[om][southRow][wc] = true;
                                        parent[om][southRow][wc]  = m * 10000 + r * 100 + c;
                                        stack.push(new int[]{om, southRow, wc});
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    } else {
                        stack.push(new int[]{southMaze, southRow, southCol});
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
                            if (!grid[tm][tr][tc].equals("W") && !grid[tm][tr][tc].equals("$")) {
                                grid[tm][tr][tc] = "+";
                            }
                            int prev = parent[tm][tr][tc];
                            tm = prev / 10000;
                            tr = (prev % 10000) / 100;
                            tc = prev % 100;
                        }
                        return true;
                    }

                    if (eastCell.equals("|")) {
                        stack.push(new int[]{eastMaze, eastRow, eastCol});
                        for (int om = 0; om < maps; om++) {
                            if (om != eastMaze) {
                                for (int wc = 0; wc < cols; wc++) {
                                    if (grid[om][eastRow][wc].equals("|") && !visited[om][eastRow][wc]) {
                                        visited[om][eastRow][wc] = true;
                                        parent[om][eastRow][wc]  = m * 10000 + r * 100 + c;
                                        stack.push(new int[]{om, eastRow, wc});
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    } else {
                        stack.push(new int[]{eastMaze, eastRow, eastCol});
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
                            if (!grid[tm][tr][tc].equals("W") && !grid[tm][tr][tc].equals("$")) {
                                grid[tm][tr][tc] = "+";
                            }
                            int prev = parent[tm][tr][tc];
                            tm = prev / 10000;
                            tr = (prev % 10000) / 100;
                            tc = prev % 100;
                        }
                        return true;
                    }

                    if (westCell.equals("|")) {
                        stack.push(new int[]{westMaze, westRow, westCol});
                        for (int om = 0; om < maps; om++) {
                            if (om != westMaze) {
                                for (int wc = 0; wc < cols; wc++) {
                                    if (grid[om][westRow][wc].equals("|") && !visited[om][westRow][wc]) {
                                        visited[om][westRow][wc] = true;
                                        parent[om][westRow][wc]  = m * 10000 + r * 100 + c;
                                        stack.push(new int[]{om, westRow, wc});
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    } else {
                        stack.push(new int[]{westMaze, westRow, westCol});
                    }
                }
            }
        }

        System.out.println("The Wolverine Store is closed.");
        return false;
    }
}