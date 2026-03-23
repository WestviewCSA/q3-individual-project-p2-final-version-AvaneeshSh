import java.io.File;
import java.io.FileNotFoundException;
import java.util.Stack;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class Reader {

    public static void main(String[] args) {
    
    
/*
 * try {
    		
    		 
            String[][][] map = getText("HardMap4");
            printGrid(map);
            
            System.out.println("\n=== Queue Result ===");
            String[][][] QueueGrid = copyGrid(map);
            boolean QueueSolved = SolveQueue.solve(QueueGrid);
            if (QueueSolved) {
                printGrid(QueueGrid);
            }

            System.out.println();
            
            
            String[][][] coor = getCrds("hardMap2Coords");
            printGrid(coor);
            
            System.out.println("\n=== Stack Result ===");
            String[][][] stackGrid = copyGrid(map);
            boolean stackSolved = SolveStack.solve(stackGrid);
            if (stackSolved) {
            	printGrid(stackGrid);
            }
            
            System.out.println("\n=== Optimal Result ===");
            String[][][] optGrid = Reader.copyGrid(map);
            boolean optSolved = SolveOptimal.solve(optGrid);
            if (optSolved) {
                Reader.printGrid(optGrid);
            }
            

        } catch (IncorrectMapFormatException e) {
            System.out.println("Format error: " + e.getMessage());
        } catch (IllegalMapCharacterException e) {
            System.out.println("Character error: " + e.getMessage());
        } catch (IncompleteMapException e) {
            System.out.println("Incomplete map: " + e.getMessage());
        }
    }
 * 
 * 
 */
    
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
                    mazeLevel = Integer.parseInt(parts[3]) - 1;
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
    
    public static int[] findCharInMaze(String[][][] grid, String target, int maze) {
        for (int r = 0; r < grid[maze].length; r++) {
            for (int c = 0; c < grid[maze][0].length; c++) {
                if (grid[maze][r][c].equals(target)) {
                    return new int[]{maze, r, c};
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

    public static void printGrid(String[][][] map) {
        for (int m = 0; m < map.length; m++) {
            System.out.println("--- Maze " + m + " ---");
            for (int i = 0; i < map[m].length; i++) {
                for (int j = 0; j < map[m][0].length; j++) {
                    System.out.print(map[m][i][j]);
                }
                System.out.println();
            }
        }
    }
   
    
    
}