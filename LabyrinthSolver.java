import java.util.*;
public class LabyrinthSolver{
	private ArrayList<Integer> solution = new ArrayList<Integer>();
	private ArrayList<int[]> tracker = new ArrayList<int[]>();
	
	public LabyrinthSolver(){
	}
	
	public int[] solve(Labyrinth l){
		// find safe move
		findSafeMove(0, 0, l);
		int[] result = toArray(solution);
		
		// reset everything
		solution.clear();
		tracker.clear();
		
		// return the solution
		return result;
	}
	
	public boolean findSafeMove(int row, int col, Labyrinth l){
		if(row == l.rows - 1 && col == l.cols - 1){
			return true;
		}
		
		int[][] directions = {l.UP, l.DOWN, l.LEFT, l.RIGHT};
		
		// loop through all the possible directions
		for(int i = 0; i < directions.length; i++){
			int[] x = directions[i];
			
			// check if it works
			if(isValid(row + x[0], col + x[1], l)){
				solution.add(i);
				int[] place = {row, col};
				tracker.add(place);
				
				if(!findSafeMove(row + x[0], col + x[1], l)){
					solution.remove(solution.size() - 1);
					tracker.remove(tracker.size() - 1);
				}
			}
		}
		return false;
	}
	
	private boolean isValid(int row, int col, Labyrinth l){
		int[] place = {row, col};
		return(l.isValid(row, col) && l.isStone(row, col) && !tracker.contains(place));
	}
	
	private int[] toArray(ArrayList<Integer> al){
		int[] result = new int[al.size()];
		for(int i = 0; i < result.length; i++){
			result[i] = al.get(i);
		}
		return result;
	}
	
	public static void main(String[] args){
		Labyrinth l = new Labyrinth(4, 4);
		l.printGrid();
		LabyrinthSolver ls = new LabyrinthSolver();
		System.out.println(l.solves(ls.solve(l)));
	}
}
