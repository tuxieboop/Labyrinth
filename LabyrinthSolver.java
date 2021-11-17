import java.util.*;
public class LabyrinthSolver{
	
	public LabyrinthSolver(){
	}
	
	public int[] findSafeMove(int row, int col, Labyrinth l){
		ArrayList<Integer> solution = new ArrayList<Integer>();
		
		if(row == l.rows - 1 && row == l.cols - 1){
			//break out of backtracking
			return solution.toArray();
		}
		
		int[][] directions = {l.UP, l.DOWN, l.LEFT, l.RIGHT};
		for(int i = 0; i < directions.length; i++){
			int[] x = directions[i];
			
			if(isValid(row + x[0], col + x[1], l)){
				solution.add(i);
				findSafeMove(row + x[0], col + x[1], l);
				solution.remove(solution.size() - 1);
			}
		}
	}
	
	private boolean isValid(int row, int col, Labyrinth l){
		return(l.isValid(row, col) && l.isStone(row, col));
	}
	
}