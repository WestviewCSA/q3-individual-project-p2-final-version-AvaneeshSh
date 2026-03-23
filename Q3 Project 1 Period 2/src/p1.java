public class p1 {

    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("No arguments provided. Use --Help for usage information.");
            System.exit(-1);
        }
        
        boolean useStack = false;
        boolean useQueue = false;
        boolean useOpt = false;
        boolean useTime = false;
        boolean inCoordinate = false;
        boolean outCoordinate = false;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--Stack")) {
                useStack = true;
            } else if (args[i].equals("--Queue")) {
                useQueue = true;
            } else if (args[i].equals("--Opt")) {
                useOpt = true;
            } else if (args[i].equals("--Time")) {
                useTime = true;
            } else if (args[i].equals("--Incoordinate")) {
                inCoordinate = true;
            } else if (args[i].equals("--OutCoordinate")) {
                outCoordinate = true;
            } else if (args[i].equals("--Help")) {
                System.out.println("This program navigates Wolverine through a maze to find the Diamond Wolverine Buck.");
                System.out.println("--Stack         Use stack-based approach");
                System.out.println("--Queue         Use queue-based approach");
                System.out.println("--Opt           Use optimal path approach");
                System.out.println("--Time          Print the runtime of the search");
                System.out.println("--Incoordinate  Input file is in coordinate format");
                System.out.println("--OutCoordinate Output in coordinate format");
                System.out.println("--Help          Print this help message");
                System.exit(0);
            }
        }

        String filename = args[args.length - 1];

        try {

        	if (!useStack && !useQueue && !useOpt) {
        	    throw new IllegalCommandLineInputsException(
        	        "Must specify at least one of --Stack, --Queue, or --Opt.");
        	} else if (useStack && useQueue) {
        	    throw new IllegalCommandLineInputsException(
        	        "Cannot specify both --Stack and --Queue.");
        	} else if (useStack && useOpt) {
        	    throw new IllegalCommandLineInputsException(
        	        "Cannot specify both --Stack and --Opt.");
        	} else if (useQueue && useOpt) {
        	    throw new IllegalCommandLineInputsException(
        	        "Cannot specify both --Queue and --Opt.");
        	}

            String[][][] grid;
            if (inCoordinate) {
                grid = Reader.getCords(filename);
            } else {
                grid = Reader.getText(filename);
            }

            if (grid == null) {
                System.out.println("Error: could not read file " + filename);
                System.exit(-1);
            }

            String[][][] gridCopy = Reader.copyGrid(grid);

            long startTime = System.nanoTime();

            boolean solved = false;
            if (useStack) {
                solved = SolveStack.solve(gridCopy);
            } else if (useQueue) {
                solved = SolveQueue.solve(gridCopy);
            } else if (useOpt) {
                solved = SolveOptimal.solve(gridCopy);
            }

            long endTime = System.nanoTime();

            if (solved) {
                if (outCoordinate) {
                    printCoordinate(gridCopy);
                } else {
                    printGrid(gridCopy);
                }
            }

            if (useTime) {
                double seconds = (endTime - startTime) / 1_000_000_000.0;
                System.out.println("Total Runtime: " + seconds + " seconds");
            }

        } catch (IllegalCommandLineInputsException e) {
            System.out.println("Command line error: " + e.getMessage());
            System.exit(-1);
        } catch (IncorrectMapFormatException e) {
            System.out.println("Format error: " + e.getMessage());
        } catch (IllegalMapCharacterException e) {
            System.out.println("Character error: " + e.getMessage());
        } catch (IncompleteMapException e) {
            System.out.println("Incomplete map: " + e.getMessage());
        }
    }

    public static void printGrid(String[][][] map) {
        for (int m = 0; m < map.length; m++) {
            for (int i = 0; i < map[m].length; i++) {
                for (int j = 0; j < map[m][0].length; j++) {
                    System.out.print(map[m][i][j]);
                }
                System.out.println();
            }
        }
    }

    public static void printCoordinate(String[][][] map) {
        for (int m = 0; m < map.length; m++) {
            for (int r = 0; r < map[m].length; r++) {
                for (int c = 0; c < map[m][0].length; c++) {
                    if (map[m][r][c].equals("+")) {
                        System.out.println("+ " + r + " " + c + " " + m);
                    }
                }
            }
        }
    }
}