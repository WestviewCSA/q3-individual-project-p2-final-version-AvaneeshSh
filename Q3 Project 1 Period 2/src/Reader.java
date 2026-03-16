import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class Reader {

    public static void main(String[] args) {

    	try {
            String[][][] map = getText("EasyMap2");
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

/*
 * 

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
                String character = parts[0];
                int row = Integer.parseInt(parts[1]);
                int col = Integer.parseInt(parts[2]);

                if (row >= Integer.parseInt(rows) || col >= Integer.parseInt(columns)) {
                    System.out.println("Coordinates don't match the given space.");
                    return new String[0][0];
                }

                cordBased[row][col] = character;
            }

            for (int i = 0; i < cordBased.length; i++) {
                for (int j = 0; j < cordBased[0].length; j++) {
                    if (cordBased[i][j] == null) {
                        cordBased[i][j] = ".";
                    }
                }
            }

            return cordBased;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return null;
    }
    
 */
    
    public static int[] findChar(String[][] grid, String target) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j].equals(target)) {
                	int[] vals = new int[2];
                	vals[0] = i;
        			vals[1] = j;
        			return vals;
                }
            }
        }
        return null;
    }
}