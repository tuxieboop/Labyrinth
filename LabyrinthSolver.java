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
		//check if this is the end
		if(row == l.rows - 1 && col == l.cols - 1){
			return true;
		}
		
		if(solveDirection(row, col, 1))
			return true;
		if(solveDirection(row, col, 3))
			return true;
		if(solveDirection(row, col, 0))
			return true;
		if(solveDirection(row, col, 2))
			return true;
		
		return false;
	}
	
	private void setTracker(){
		for(int i = 0; i < tracker.length; i++){
			for(int ii = 0; ii < tracker[0].length; ii++){
				tracker[i][ii] = false;
			}
		}
	}
	
	private boolean solveDirection(int row, int col, int dir){
		tracker[row][col] = true;
		int[] direction = {0, 0};
		
		if(dir == 0){
			direction[0] = -1;
		}
		else if(dir == 1){
			direction[0] = 1;
		}
		else if(dir == 2){
			direction[1] = -1;
		}
		else{
			direction[1] = 1;
		}
		
		if(isValid(row + direction[0], col + direction[1])){
			solution.add(dir);
			if(findSafeMove(row + direction[0], col + direction[1])){
				return true;
			}
			solution.remove(solution.size() - 1);
		}
		return false;
	}
	
	private boolean isValid(int row, int col){
		int[] place = {row, col};
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