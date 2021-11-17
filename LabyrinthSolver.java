import java.util.*;
public class LabyrinthSolver{
	
	public LabyrinthSolver(){
	}
	
	public int[] findSafeMove(int row, int col, Labyrinth l){
		ArrayList<Integer> solution = new ArrayList<Integer>();
		
		if(row == l.rows - 1 && row == l.cols - 1){
			//show solution
			// break out of backtracking
			return solution.toArray();
		}
		for(int[] X : {l.UP, l.DOWN, l.LEFT, l.RIGHT}){
			if(isValid(row + X[0], col + X[1], l)){
				//makeMove(x)
				findSafeMove(row + X[0], col + X[1], l);
				//undo(x)
			}
		}
	}
	
	private boolean isValid(int row, int col, Labyrinth l){
		return(l.isValid(row, col) && l.isStone(row, col));
	}
	
}