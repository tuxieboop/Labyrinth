public class LabyrinthSolver{
	
	public LabyrinthSolver(){
	}
	
	public void findSafeMove(int row, int col, Labyrinth l){
		if(row == l.rows - 1 && row == l.cols - 1){
			//show solution
			// break out of backtracking
		}
	}
	
	private boolean isValid(int row, int col, Labyrinth l){
		return(l.isValid(row, col) && l.isStone(row, col));
	}
	
}