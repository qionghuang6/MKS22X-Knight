public class KnightBoard{
  private int[][] board;
  private static final int[][] directions = {{1,2},{2,1},{2,-1},{-2,1},{-1,-2},{-2,-1},{-2,1},{-1,2}};
  public KnightBoard(int startingRows,int startingCols){
    if(startingCols < 0 || startingCols < 0){
      throw new IllegalArgumentException();
    }
    board = new int[startingRows][startingCols];
  }
  public String toString(){
    String s = "";
    for (int r = 0; r < board.length ; r++ ) {
      for (int c = 0; c < board[0].length ;c++ ) {
        if(board[r][c] < 0){
          s += "\n";
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
    return s;
  }
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
  public boolean solveH(int row, int col, int level){
    if(level == board.length * board[0].length){
      return true;
    }
    for (int[] direction : directions) {
      if(row + direction[0] >= 0 && row + direction[0] < board.length
        && col + direction[1] >= 0 && col + direction[1] < board[0].length){
          board[row + direction[0]][col + direction[1]] = level +1;
          if(solveH(row + direction[0], col + direction[1], level + 1)){
            return true;
          }
          //board[row + direction[0]][col + direction[1]] = 0;
        }
    }
    return false;
  }
}
