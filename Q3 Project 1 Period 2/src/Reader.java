import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class Reader {
	

	public static void main(String[] args) {
		
		Queue text = getText("Easy_Map_2");
		
		while(!text.isEmpty()) {
			System.out.println(text.poll());
		}
		
		System.out.println("");
		
		String[][] n = getCords("Easy_Map_Coordinates");
		
		for(int i = 0; i < n.length; i++) {
			for(int j = 0; j < n[0].length; j++) {
				System.out.print(n[i][j]);
			}
			System.out.println();
		}
		

	}
	
	public static Queue<String> getText(String passedFile) {
		
		
		Queue<String> textBased = new ArrayDeque<>();
		File fileObj = new File(passedFile);
		try {
			
			
			String rows = "";		
			String columns = "";
			String maps = "";
			Scanner scan = new Scanner(fileObj);
			rows = scan.next();
			columns = scan.next();
			maps = scan.next();
			
			while(scan.hasNext()) {
				textBased.add(scan.next());
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return textBased;

	}
	
	public static String[][] getCords (String passedFile) {
		
		String rows = "";		
		String columns = "";
		String maps = "";
		

		
		File fileObj = new File(passedFile);
		try {
			
			
			Scanner scan = new Scanner(fileObj);
			rows = scan.next();
			columns = scan.next();
			maps = scan.next();
			
			String[][] cordBased = new String[Integer.parseInt(rows)][Integer.parseInt(columns)];
			
			while(scan.hasNext()) {
				
				String character = scan.next();
				cordBased[Integer.parseInt(scan.next())][Integer.parseInt(scan.next())] = character;
				scan.next();
			}
			
			for(int i = 0; i < cordBased.length; i++) {
				for(int j = 0; j < cordBased[0].length; j++) {
					if(cordBased[i][j] == null) {
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


