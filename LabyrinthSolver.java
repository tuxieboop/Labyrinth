import java.util.*;
public class LabyrinthSolver{
	private ArrayList<Integer> solution = new ArrayList<Integer>();
	
	public LabyrinthSolver(){
	}
	
	public int[] solve(Labyrinth l){
		findSafeMove(0, 0, l);
		int[] result = toArray(solution);
		solution.clear();
		return result;
	}
	
	public void findSafeMove(int row, int col, Labyrinth l){
		
		if(row == l.rows - 1 && col == l.cols - 1){
			return;
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
	
	private int[] toArray(ArrayList<Integer> al){
		int[] result = new int[al.size()];
		for(int i = 0; i < result.length; i++){
			result[i] = al.get(i);
		}
		return result;
	}
	
	public static void main(String[] args){
		Labyrinth l = new Labyrinth(6, 6);
		LabyrinthSolver ls = new LabyrinthSolver();
		System.out.println(l.solves(ls.solve(l)));
	}
}
