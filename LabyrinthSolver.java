import java.util.*;
public class LabyrinthSolver{
	private ArrayList<Integer> solution = new ArrayList<Integer>();
	private boolean[][] tracker;
	private Labyrinth l;
	
	public LabyrinthSolver(Labyrinth l){
		this.l = l;
		tracker = new boolean[l.rows][l.cols];
		setTracker();
	}
	
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
	
	public boolean findSafeMove(int row, int col){
		// check if this is the end
		if(row == l.rows - 1 && col == l.cols - 1){
			return true;
		}
		
		// check down
		if(solveDirection(row, col, 1))
			return true;
		// check right
		if(solveDirection(row, col, 3))
			return true;
		// check up
		if(solveDirection(row, col, 0))
			return true;
		// check left
		if(solveDirection(row, col, 2))
			return true;
		
		return false;
	}
	
	/**
	* resets the tracker
	*/
	private void setTracker(){
		for(int i = 0; i < tracker.length; i++){
			for(int ii = 0; ii < tracker[0].length; ii++){
				tracker[i][ii] = false;
			}
		}
	}
	
	private boolean solveDirection(int row, int col, int dir){
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
	
	private boolean isValid(int row, int col){
		return(l.isValid(row, col) && l.isStone(row, col) && !tracker[row][col]);
	}
	
	private int[] toArray(ArrayList<Integer> al){
		int[] result = new int[al.size()];
		for(int i = 0; i < result.length; i++){
			result[i] = al.get(i);
		}
		return result;
	}
	
	public static void main(String[] args){
		Labyrinth l = new Labyrinth(10, 10);
		l.printGrid();
		LabyrinthSolver ls = new LabyrinthSolver(l);
		System.out.println(l.solves(ls.solve()));
	}
}