import java.util.Arrays;
public class KnightBoard{
  private int[][] board;
  //heatmap used for optmization
  private int[][] heatMap;
  //eight possibilities for knight to go
  private static final int[][] directions = {{1,2},{2,1},{2,-1},{1,-2},{-1,-2},{-2,-1},{-2,1},{-1,2}};
  public KnightBoard(int startingRows,int startingCols){
    if(startingCols < 0 || startingCols < 0){
      throw new IllegalArgumentException();
    }
    //sets up both boards
    board = new int[startingRows][startingCols];
    heatMap = new int[startingRows][startingCols];
    heatMapMaker();
  }
  //used to generate heatmap based on possibille moves from that board
  private void heatMapMaker(){
    for (int r = 0; r < board.length ;r++ ) {
      for (int c = 0;c < board[r].length ;c++ ) {
        int possibilities = 0;
        for (int[] direction : directions) {
          if(r + direction[0] >= 0 && r + direction[0] < board.length
            && c + direction[1] >= 0 && c + direction[1] < board[0].length){
              possibilities++;
              //adds to heatmap if move isnt out of bounds
          }
        }
        heatMap[r][c] = possibilities;
      }
    }
  }
  //loops through 2d board and prints values, shows path
  public String toString(){
    String s = "";
    for (int r = 0; r < board.length ; r++ ) {
      for (int c = 0; c < board[0].length ;c++ ) {
        if(board[r][c] < 10){
          s += " ";
        }
        s += board[r][c];
        if(c != board[0].length - 1){
          s += " ";
        }
      }
      if(r != board.length - 1){
        s += "\n";
      }
    }
    return s;  //testcase must be a valid index of your input/output array
  }
  //debug toString for heatmap
  public String heatToString(){
    String s = "";
    for (int[] row: heatMap) {
      for (int no: row) {
        s += no;
        s+= " ";
      }
      s += "\n";
    }
    return s;
  }
  //calls recurive helper solveH
  public boolean solve(int startingRows,int startingCols){
    if(startingCols < 0 || startingCols < 0){
      throw new IllegalArgumentException();
    }
    for (int[] row :board ) {
      for (int num: row){
        if(num != 0 ){
          throw new IllegalStateException();
        }
      }
    }

    return solveH(startingRows,startingCols,0);
  }
  //checks for valid arguments before calling recursive helper
  public int countSolutions(int startRow, int startCol){
    if(startRow < 0 || startCol < 0){
      throw new IllegalArgumentException();
    }
    for (int[] row :board ) {
      for (int num: row){
        if(num != 0 ){
          throw new IllegalStateException();
        }
      }
    }
    return countSolutionsR(startRow,startCol,0);
  }

  //recursive helper for solve
  private boolean solveH(int row, int col, int level){
    board[row][col] = level + 1;
    //returns true once it hits the end
    if(level + 1 >= board.length * board[0].length){
      return true;
    }
    //tries all 8 directions
    for (int[] direction : directions) {
      if(row + direction[0] >= 0 && row + direction[0] < board.length
        && col + direction[1] >= 0 && col + direction[1] < board[0].length &&
        board[row + direction[0]][col + direction[1]] == 0 &&
        solveH(row + direction[0], col + direction[1], level + 1)){
            return true;
        }
    }
    //cleans up changes if it couldnt solve
    board[row][col] = 0;
    return false;
  }
  //optmized solver than uses heatmap
  private boolean solveOpt(int row, int col, int level){
    //writes on board each move
    board[row][col] = level + 1;
    //returns true if it hits end of path
    if(level + 1 >= board.length * board[0].length){
      return true;
    }
    //array used to store possibile moves for sorting
    int[][] moves = new int[8][3];
    //loop sets up array to have coordinates and possible moves based on heatmap
    for (int i =0;i < 8 ;i++ ) {
      moves[i][0] = directions[i][0];
      moves[i][1] = directions[i][1];
      if(row + directions[i][0] >= 0 && row + directions[i][0] < board.length &&
        col + directions[i][1] >= 0 && col + directions[i][1] < board[0].length){
          moves[i][2] = heatMap[row + directions[i][0]][col + directions[i][1]];
        }
    }
    //sorts array by the number of possibilies
    //uses an annonymous comprator with arrays.sort to sort the array
    Arrays.sort(moves, (a, b) -> Double.compare(a[2], b[2]));
    /*
    for (int[]  m: moves) {
      System.out.print(Arrays.toString(m));
      System.out.print(" ");
    }
    System.out.println();
    System.out.println(this);
    */
    //goes in order of the sorted possibile moves
    for (int[] move: moves) {
      if (move[2] != 0 && board[row + move[0]][col + move[1]] == 0) {
        int original = heatMap[row + move[0]][col + move[1]]; //remembers original paths
        //tries moves in best order
        for (int[] m: moves) {
          try {
            heatMap[row + move[0]][col + move[1]]--;
          } catch(ArrayIndexOutOfBoundsException e) {

          }
        }
        heatMap[row + move[0]][col + move[1]] = 0;

        if(solveOpt(row + move[0], col + move[1], level + 1)){
            return true;
        }
        //restores heatmap to original paths if move doesnt work and needs to backtrack
        for (int[] m: moves) {
          try {
            heatMap[row + move[0]][col + move[1]]++;
          } catch(ArrayIndexOutOfBoundsException e) {

          }
        }
        heatMap[row +move[0]][col + move[1]] = original;
      }
    }
    board[row][col] = 0;
    return false;
  }
  private int countSolutionsR(int row, int col, int level){
    //counter for solutions
    int sols = 0;
    board[row][col] = level + 1;
    if(level + 1 == board.length * board[0].length){
      sols++;
    }
    //adds to solutions for every solution that works
    for (int[] direction : directions) {
      if(row + direction[0] >= 0 && row + direction[0] < board.length
        && col + direction[1] >= 0 && col + direction[1] < board[0].length &&
        board[row + direction[0]][col + direction[1]] == 0){
            sols += countSolutionsR(row + direction[0], col + direction[1], level + 1);
        }
    }
    board[row][col] = 0;
    return sols;
  }
}
