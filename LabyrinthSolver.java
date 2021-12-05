import java.util.*;

/**
* Solves a Labyrinth and returns an array of directions.
*/
public class LabyrinthSolver{
	private static ArrayList<Integer> solution = new ArrayList<Integer>(); // Keeps track of directions: 0 up, 1 down, 2 left, 3 right
	private static boolean[][] tracker; // Keeps track of where the solver has been
	private static Labyrinth l; // The labyrinth to solve
	
	/**
	* Constructor for a LabyrinthSolver
	*/
	public LabyrinthSolver(){
	}
	
	/**
	* Solves a labyrinth from upper left corner to bottom right corner and returns an int[] of directions.
	* @return an array of directions
	*/
	public static int[] solve(Labyrinth l){
		tracker = new boolean[l.rows][l.cols];
		setTracker();
		
		// backtrack through Labyrinth, adding directions to an ArrayList
		findSafeMove(0, 0, l);
		int[] result = toArray(solution);
		
		// reset everything
		solution.clear();
		setTracker();
		
		// return the solution
		return result;
	}
	
	/**
	* Solves the labyrinth by checking each direction and backtracking.
	* @param row the current position's row
	* @param col the current position's column
	* @return boolean representing whether a solution was found
	*/
	private static boolean findSafeMove(int row, int col, Labyrinth l){
		// check if we have reached our goal
		if(row == l.rows - 1 && col == l.cols - 1){
			return true;
		}
		
		// check down
		if(checkDirection(row, col, 1, l))
			return true;
		// check right
		if(checkDirection(row, col, 3, l))
			return true;
		// check up
		if(checkDirection(row, col, 0, l))
			return true;
		// check left
		if(checkDirection(row, col, 2, l))
			return true;
		
		return false;
	}
	
	/**
	* Checks if a direction is valid & leads to a solution for a certain position.
	* @param row the current position's row
	* @param col the current position's column
	* @param dir the direction to check
	* @return boolean representing whether the direction is valid & leads to a solution
	*/
	private static boolean checkDirection(int row, int col, int dir, Labyrinth l){
		// set the position as visited
		tracker[row][col] = true;
		
		// set the direction based on dir
		int[] direction = {0, 0};
		
		// up
		if(dir == 0)
			direction[0] = -1;
		// down
		else if(dir == 1)
			direction[0] = 1;
		// left
		else if(dir == 2)
			direction[1] = -1;
		// right
		else
			direction[1] = 1;
		
		// check if the new position is valid
		if(isValid(row + direction[0], col + direction[1], l)){
			solution.add(dir);
			// check if the new position leads to a solution
			if(findSafeMove(row + direction[0], col + direction[1], l)){
				return true;
			}
			solution.remove(solution.size() - 1);
		}
		return false;
	}
	
	/**
	* resets the tracker to all false
	*/
	private static void setTracker(){
		for(int i = 0; i < tracker.length; i++){
			for(int ii = 0; ii < tracker[0].length; ii++){
				tracker[i][ii] = false;
			}
		}
	}
	
	/**
	* Checks if a position is valid: whether it is within the boundaries of the Labyrinth and hasn't been visited yet.
	* @param row the position's row
	* @param col the position's column
	* @return boolean representing whether the position is valid
	*/
	private static boolean isValid(int row, int col, Labyrinth l){
		return(l.isValid(row, col) && l.isStone(row, col) && !tracker[row][col]);
	}
	
	/**
	* Converts an Integer ArrayList to an int[].
	* @param al the ArrayList to convert
	* @return the converted int[]
	*/
	private static int[] toArray(ArrayList<Integer> al){
		int[] result = new int[al.size()];
		for(int i = 0; i < result.length; i++){
			result[i] = al.get(i);
		}
		return result;
	}
	
	/**
	* Tester. Creates a 10 by 10 Labyrinth and a LabyrinthSolver that solves the labyrinth, then displays the labyrinth and prints a boolean representing whether the LabyrinthSolver's solution was successful.
	*/
	public static void main(String[] args){
		Labyrinth l = new Labyrinth(10, 10);
		l.printGrid();
		System.out.println(l.solves(LabyrinthSolver.solve(l)));
	}
}
