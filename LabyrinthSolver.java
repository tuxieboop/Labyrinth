import java.util.*;

/**
* Solves a Labyrinth and returns an array of directions if the initial position is [0, 0], the upper left corner, and the goal is the lower right corner.
*/
public class LabyrinthSolver{
	private ArrayList<Integer> solution = new ArrayList<Integer>(); // Keeps track of directions: 0 up, 1 down, 2 left, 3 right
	private boolean[][] tracker; // Keeps track of where the solver has been
	private Labyrinth l; // The labyrinth to solve
	
	/**
	* Constructor for a LabyrinthSolver, sets up the tracker.
	* @param l the labyrinth for this LabyrinthSolver to solve
	*/
	public LabyrinthSolver(Labyrinth l){
		this.l = l;
		tracker = new boolean[l.rows][l.cols];
		setTracker();
	}
	
	/**
	* Solves a labyrinth from upper left corner to bottom right corner and returns an int[] of directions.
	* @return an array of directions
	*/
	public int[] solve(){
		// find safe move
		findSafeMove(0, 0);
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
	private boolean findSafeMove(int row, int col){
		// check if this is the end
		if(row == l.rows - 1 && col == l.cols - 1){
			return true;
		}
		
		// check down
		if(checkDirection(row, col, 1))
			return true;
		// check right
		if(checkDirection(row, col, 3))
			return true;
		// check up
		if(checkDirection(row, col, 0))
			return true;
		// check left
		if(checkDirection(row, col, 2))
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
	private boolean checkDirection(int row, int col, int dir){
		// set the position as visited
		tracker[row][col] = true;
		
		// set the direction based on dir
		int[] direction = {0, 0};
		
		// up
		if(dir == 0){
			direction[0] = -1;
		}
		// down
		else if(dir == 1){
			direction[0] = 1;
		}
		// left
		else if(dir == 2){
			direction[1] = -1;
		}
		// right
		else{
			direction[1] = 1;
		}
		
		// check if the new position is valid
		if(isValid(row + direction[0], col + direction[1])){
			solution.add(dir);
			// check if the new position leads to a solution
			if(findSafeMove(row + direction[0], col + direction[1])){
				return true;
			}
			solution.remove(solution.size() - 1);
		}
		return false;
	}
	
	/**
	* resets the tracker to all false
	*/
	private void setTracker(){
		for(int i = 0; i < tracker.length; i++){
			for(int ii = 0; ii < tracker[0].length; ii++){
				tracker[i][ii] = false;
			}
		}
	}
	
	/**
	* Checks if a position is valid: whether it is within the boundaries of the labyrinth and hasn't been visited yet.
	* @param row the position's row
	* @param col the position's column
	* @return boolean representing whether the position is valid
	*/
	private boolean isValid(int row, int col){
		return(l.isValid(row, col) && l.isStone(row, col) && !tracker[row][col]);
	}
	
	/**
	* Converts an Integer ArrayList to an int[].
	* @param al the ArrayList to convert
	* @return the converted int[]
	*/
	private int[] toArray(ArrayList<Integer> al){
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
		LabyrinthSolver ls = new LabyrinthSolver(l);
		System.out.println(l.solves(ls.solve()));
	}
}
