/*
* Labyrinth.java is part of the Backtracking project in
* the recursion unit of Advanced CS at Friends School of Baltimore.
*
*
* Testing--
* Compile: javac Labyrinth.java
* Run: java Labyrinth [rows] [columns]
*
*
* (c) 2021 Joel Hammer
* Friends School of Baltimore
*
*
*/

/**
* Generates a random "grid-maze" of individual squares arranged into a
* rectangular grid such that a path from the top-left to the bottom-right
* corner always exists. Squares are made out of either stone or lava. Stone
* squares can be traversed while lava squares cannot. A square is "touching"
* another square if it is directly above or below that square or directly to
* its right or left (diagonally "touching" squares are not said to be really
* <i> touching </i> in this context). The Labyrinth is generated so that one
* can move along touching squares from the start square (top-left) to the end
* square (bottom-right).
* <br>
* <br>
* Coordinates in the Labyrinth are given in (row, column) form and not (x,y).
* That is, in general, the parameters of the methods below expect a row number
* followed by a column number. Both the rows and columns are numbered from the
* top-left corner and begin with 0, so that the top-left corner is (row = 0, 
* col = 0) and, say, three squares down and four to the right would be (row = 3,
* col = 4).
* <br>
* <br>
* A solution to the Labyrinth is a series of steps or "moves." These moves
* begin from the start (top-left) square and each move can either be to the
* square immediately above, below, to the left, or to the right of the current
* square. A solution is correct if the moves trace out a path along touching
* squares from the start to the end squares.
*
* Moves can be indicated in two ways: <ol>
* <li> As an {@code int[]} array </li>
* <li> Using the class-defined constants </li>
* </ol>
* <br>
* In the first case a solution is coded using the integers from 0 to 4 with:
* <ul>
* <li> "Up" = 0 </li>
* <li> "Down" = 1 </li>
* <li> "Left" = 2 </li>
* <li> "Right" = 3 </li>
* </ul>
* <br>
* <br>
* In the second case, the solution is actually represented using an {@code int[][]}
* array. The class has defined constants UP, DOWN, LEFT, and RIGHT which are
* all {@code int[]} arrays with two elements representing a particular motion
* through the grid. The numeric value of these constants, while <i> perhaps </i>
* useful, is of no real importance outside the class.
*/
public class Labyrinth {
    /**
    * The number of rows in the Labyrinth grid.
    */
    public final int rows;
    
    /**
    * The number of columns in the Labyrinth grid.
    */
    public final int cols;
    private UF tracker;
    private int destination;
    
    /**
    * Utility constant representing moving up a column, staying in the same row.
    */
    public static final int[] UP = {-1,0};
    
    /**
    * Utility constant representing moving down a column, staying in the same row.
    */
    public static final int[] DOWN = {1,0};
    
    /**
    * Utility constant representing moving left in a row, staying in the same column.
    */
    public static final int[] LEFT = {0,-1};
    
    /**
    * Utility constant representing moving right in a row, staying in the same column.
    */
    public static final int[]  RIGHT = {0,1};
    
    private boolean[][] grid;
    
    /**
    * Constructs a random labyrinth with specified width and height.
    * @param rows the desired number of rows in the Labyrinth
    * @param cols the desired number of columns in the Labyrinth
    */
    public Labyrinth(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new boolean[rows][cols];
        this.tracker = new UF(rows * cols);
        this.destination = rows * cols - 1;
        
        build();
    }
    
    //Constructs the labyrinth
    private void build() {
        //Allow access to the starting and ending squares.
        grid[0][0] = true;
        grid[rows-1][cols-1] = true;
        
        while(!tracker.find(0 , destination)) {
           int n = (int) (Math.random() * this.rows);
           int m = (int) (Math.random() * this.cols);
           grid[n][m] = true;
           link(n,m);
        }  
    }
    
    //Converts grid coordinates to "absolute" coordinates used by the tracker.
    private int toAbs(int row, int col) {
        return row * cols + col;
    }
    
    private int toAbs(int[] loc) throws IllegalArgumentException {
        validateLoc(loc);
        return toAbs(loc[0], loc[1]);
    }
    
    //Ensure locations given as array of length 2 is formatted correctly.
    private void validateLoc(int[] loc) throws IllegalArgumentException {
        if (loc.length != 2) {
            throw new IllegalArgumentException("Location parameters must have exactly two arguments");
        }
    }
    
    /**
    * Check if the given row and column is on the grid.
    * @param row a potential row number
    * @param col a potential col number
    * @return {@code true} if the given row and column is on the grid. 
    */
    public boolean isValid(int row, int col) {
        return row >= 0 && row < this.rows && col >= 0 && col < this.cols;
    }
    
    /*
    * Check if a potential grid square is actually on the grid with coordinates
    * given as an array of length 2;
    */
    private boolean isValid(int[] loc) throws IllegalArgumentException {
        validateLoc(loc);
        return isValid(loc[0], loc[1]);
    }
    
    //Link a given grid square to adjacent stone (not lava) sites.
    private void link(int row, int col) {
        for (int[] direction : new int[][]{UP, DOWN, LEFT, RIGHT}) {
            int neighborRow = row + direction[0];
            int neighborCol = col + direction[1];
            if (isValid(neighborRow, neighborCol) && isStone(neighborRow, neighborCol)) {
                tracker.union(toAbs(row, col), toAbs(neighborRow, neighborCol));
            }
        }
    }
    
    /**
    * Checks if a given grid square is stone (or lava).
    * @param row the row of the grid square to check.
    * @param col the column of the grid square to check.
    * @return {@code true} if the given square is stone, {@code false} if lava.
    */
    public boolean isStone(int row, int col) {
        return grid[row][col];
    }
    
    private boolean isStone(int[] loc) throws IllegalArgumentException {
        validateLoc(loc);
        return isStone(loc[0], loc[1]);
    }
    
    /**
    * Checks if a given set of instructions accurately solves the labyrinth.
    * @param solution an array of integers representing the directions to move
    * from the start square to the end square. With 0 = up, 1 = down, 2 = left, 
    * and 3 = right.
    * @return {@code true if the given solution is correct}, otherwise {@code false}.
    */
    public boolean solves(int[] solution) {
        int[] currentSquare = {0,0};
        int[][] directions = {UP, DOWN, LEFT, RIGHT};
        for (int i = 0; i < solution.length; i++) {
            currentSquare = nextSquare(currentSquare, directions[solution[i]]);
            if (!isStone(currentSquare) || !isValid(currentSquare)) {
                return false;
            }
        }
        return toAbs(currentSquare) == destination;
    }
    
    /**
    * Checks if a given set of instructions accurately solves the labyrinth.
    * @param solution an {@code int[][]} array using the class-defined constants
    * {@code UP}, {@code DOWN}, {@code LEFT}, and {@code RIGHT} detailing the
    * steps to the final square from the start square.
    * @return {@code true} if the given solution is correct, otherwise {@code false}.
    */
    public boolean solves(int[][] solution) {
        int[] currentSquare = {0,0};
        for (int i = 0; i < solution.length; i++) {
            currentSquare = nextSquare(currentSquare, solution[i]);
            if(!isStone(currentSquare) || !isValid(currentSquare)) {
                return false;
            }
        }
        
        return toAbs(currentSquare) == destination;
    }
    
    //Determine the next square from a given location and a direction to move.
    private int[] nextSquare(int[] loc, int[] direction) throws IllegalArgumentException {
        if (direction.length != 2) throw new IllegalArgumentException("direction and loc params must have exactly two elements.");
        return new int[] {loc[0] + direction[0], loc[1] + direction[1]};
    }
    
    /**
    * Prints the Labyrinth to StdOut. Mostly usefule for testing.
    */
    public void printGrid() {
        System.out.println();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j]) {
                    System.out.print(" S ");
                } else {
                    System.out.print(" - ");
                }
            }
            System.out.println();
        }
    }
    
    /**
    * Tests the Labyrinth class by generating a random Labyrinth with
    * dimensions determined from command line
    * @param args command line arguments. First the number of rows, then columns.
    */
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage is java Labyrinth [rows] [columns]");
            return;
        }
        
        int a = Integer.parseInt(args[0]);
        int b = Integer.parseInt(args[1]);
        
        Labyrinth l = new Labyrinth(a,b);
        l.printGrid();
    }
    
    //Private Union-Find class to assist with random maze construction.
    private class UF {
        private int[] id;
        private int[] size;
        
        UF(int n){
            id = new int[n];
            size = new int[n];
            
            for(int i = 0; i < n; i++) {
                id[i] = i; 
                size[i] = 1;
            }
        }
        
        int rootOf(int i){
            while(id[i] != i){
                id[i] = id[id[i]];
                i = id[i];
            }
            
            return i;
        }
        
        void union(int p, int q){
            int pRoot = rootOf(p);
            int qRoot = rootOf(q);
            
            if(pRoot == qRoot) return;
            
            if(size[pRoot] < size[qRoot]){
                id[pRoot] = qRoot;
                size[qRoot] += size[pRoot];
            }
            else{
                id[qRoot] = pRoot;
                size[pRoot] += qRoot;
            }
        }
        
        boolean find(int p, int q){
            return rootOf(p) == rootOf(q);
        }
    }

}