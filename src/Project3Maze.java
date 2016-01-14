
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/**
 * Project 3
 * Maze Algorithmn 
 * CSC313
 * @author Justin Espejo
 *
 */
public class Project3Maze {

	public static boolean backtrack = false;
/**
 * This is the main of the project, It reads the file, if there is not file given
 * or the file name is wrong it will print out an error. If the file is able to be read
 * it will read the file and try to find a path through the maze starting from the position (0,0)
 * in the array.
 * @param args
 */
	public static void main(String[] args) {
		if(args == null || args.length == 0) System.out.println("You did not enter the name of the file.");
		else {
			try {
				readFile(args[0]);
			} catch (IOException e) {
				System.out.println("That File Does NOT Exist!!!");
			}
		}

	}
	
	
	
/**
 * This reads the fileinput and places the data in an array of integers to set up the maze. It then
 * calls the function to solve the maze. If no solution is found it will state that there is no 
 * solution. 
 * @param fileName is name of the file to be read 
 * @throws IOException
 */
	public static void readFile(String fileName) throws IOException {
	
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String line = br.readLine();
		while (line != null) {
			System.out.println("New Maze:");
			int maze[][] = new int[10][10];
			int r = 0;
			while (!line.equals("")) {
//				System.out.println(line);
				String[] input = line.split(" ", -1);
				for (int c = 0; c < input.length; c++) { 
					maze[r][c] = Integer.parseInt(input[c]);
				}
				r++;
				line = br.readLine();
				if(line == null) break;
			}
			printArray(maze);
//			System.out.println(">>> Start at [0,0]");
			int solution[][] = new int[maze.length][maze.length];
			for (int i = 0; i < solution.length; i++) for (int j = 0; j < solution[i].length; j++) solution[i][j] = maze[i][j];
			if (!solveMaze(maze,0,0,solution)) System.out.println("No Solution!");
			//Print the Solution 
			else {
				System.out.println("Solution Maze:");
				printArray(solution);
			}
			System.out.println();
			backtrack = false;
			line = br.readLine();
		}
		br.close();
	}
	
	/**
	 * This function will solve the maze. First it will check for the base case and try to move
	 * through the maze recursively, if no path is possible it will backtrack and try to find another 
	 * path to solve the maze.
	 * @param maze is the array of integer that represents the maze
	 * @param r is the row of the array
	 * @param c is the column of the array
	 * @param solution keeps track of where the path is 
	 * @return
	 */
	private static boolean solveMaze(int maze[][], int r, int c, int solution[][]) {
		//base cases:
		//out of board
		if(r<0 || r > maze.length-1) return false;
		if(c<0 || c > maze.length-1) return false;
		//reached the end
		if ( r == maze.length-1 && c ==maze.length-1){
			solution[r][c] = 2; return true;
		}
		//already crossed
		if(solution[r][c] == 2 ) return false; 
		//check if all
		if (maze[r][c] == 1) return false;
		//algorithm steps:
		solution[r][c] = 2;
		
		//Recursive steps:
		//North
		if (solveMaze(maze, r-1, c, solution)) return true; 
		//East
		if (solveMaze(maze, r, c+1, solution)) return true; 
		//South
		if (solveMaze(maze, r+1, c, solution)) return true; 
		//West
		if (solveMaze(maze, r, c-1, solution)) return true; 
		
		solution[r][c]=0; //not a solution
		if(!backtrack) {
			System.out.println(">>> Start backtracking from [" + r + ", " + c + "]");
			backtrack = true;
		}
		if(r == 0 && c == 0) System.out.print(">>> Back to [" + r + ", " + c + "]");
		else System.out.print(">>> Backtracking from [" + r + ", " + c + "]");
		if((c-1) >= 0 && solution[r][c-1] == 2) System.out.print(" to [" + r + ", " + (c-1) + "]");
		else if((r+1) < maze.length && solution[r+1][c] == 2) System.out.print(" to [" + (r+1) + ", " + c + "]");
		else if((c+1) < maze.length && solution[r][c+1] == 2) System.out.print(" to [" + r + ", " + (c+1) + "]");
		else if((r-1) >= 0 && solution[r-1][c] == 2) System.out.print(" to [" + (r-1) + ", " + c + "]");
		System.out.println();
		return false;
		
	}
	/**
	 * This function is the base case of the maze and also th limits.
	 * @param maze is the array of integer that represents the maze
	 * @param r is the rows in the array
	 * @param c is the column in the array
	 * @param solution is the current solution/path found
	 * @return
	 */
	public static boolean checkIfPath(int maze[][], int r, int c, int solution[][]){
		if (r <=0 && r<maze.length && c>=0 && c<maze.length && maze[r][c]==0)
			return true;
		return false;
	}
	/**
	 * This is function prints out the maze
	 * @param a is the maze to be printed
	 */
	static void printArray(int a[][]) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				System.out.print(a[i][j] + " ");
			}
			System.out.println();
		}
	}
}