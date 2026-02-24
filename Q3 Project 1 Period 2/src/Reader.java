import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class Reader {

    public static void main(String[] args) {

        Queue<String> text = getText("EasyMap2");

        while (!text.isEmpty()) {
            System.out.println(text.poll());
        }

        System.out.println("");

        String[][] n = getCords("easyMap2 Coordinates");

        for (int i = 0; i < n.length; i++) {
            for (int j = 0; j < n[0].length; j++) {
                System.out.print(n[i][j]);
            }
            System.out.println();
        }
    }

    public static Queue<String> getText(String passedFile) {

        Queue<String> textBased = new ArrayDeque<>();
        File fileObj = new File(passedFile);

        try {
            Scanner scan = new Scanner(fileObj);
            String rows = scan.next();
            String columns = scan.next();
            String maps = scan.next();
            scan.nextLine(); 

            while (scan.hasNextLine()) {
                String temp = scan.nextLine();
                if (!temp.matches("[.$W@|]+")) {
                    System.out.println("Invalid character found");
                    return new ArrayDeque<>();
                }
                textBased.add(temp);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return textBased;
    }

    public static String[][] getCords(String passedFile) {

        File fileObj = new File(passedFile);

        try {
            Scanner scan = new Scanner(fileObj);
            String rows = scan.next();
            String columns = scan.next();
            String maps = scan.next();
            scan.nextLine();

            String[][] cordBased = new String[Integer.parseInt(rows)][Integer.parseInt(columns)];

            while (scan.hasNextLine()) {
                String line = scan.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(" ");
                String character = parts[0];
                int row = Integer.parseInt(parts[1]);
                int col = Integer.parseInt(parts[2]);

                cordBased[row][col] = character;
            }

            // Fill nulls with "."
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
        }

        return null;
    }
}