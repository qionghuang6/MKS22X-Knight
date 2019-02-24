public class KnightBoard{
  private int[][] board;
  private int[][] heatMap;
  private static final int[][] directions = {{1,2},{2,1},{2,-1},{1,-2},{-1,-2},{-2,-1},{-2,1},{-1,2}};
  public KnightBoard(int startingRows,int startingCols){
    if(startingCols < 0 || startingCols < 0){
      throw new IllegalArgumentException();
    }
    board = new int[startingRows][startingCols];
    heatMap = new int[startingRows][startingCols];
    heatMapMaker();
  }
  private void heatMapMaker(){
    for (int r = 0; r < board.length ;r++ ) {
      for (int c = 0;c < board[r].length ;c++ ) {
        int possibilities = 0;
        for (int[] direction : directions) {
          if(r + direction[0] >= 0 && r + direction[0] < board.length
            && c + direction[1] >= 0 && c + direction[1] < board[0].length){
              possibilities++;
          }
        }
        heatMap[r][c] = possibilities;
      }
    }
  }
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
    return s;
  }
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
    return countSolutionsR(0,0,0);
  }

  private boolean solveH(int row, int col, int level){
    board[row][col] = level + 1;
    if(level + 1 >= board.length * board[0].length){
      return true;
    }
    for (int[] direction : directions) {
      if(row + direction[0] >= 0 && row + direction[0] < board.length
        && col + direction[1] >= 0 && col + direction[1] < board[0].length &&
        board[row + direction[0]][col + direction[1]] == 0 &&
        solveH(row + direction[0], col + direction[1], level + 1)){
            return true;
        }
    }
    board[row][col] = 0;
    return false;
  }
  private int countSolutionsR(int row, int col, int level){
    int sols = 0;
    board[row][col] = level + 1;
    if(level + 1 == board.length * board[0].length){
      sols++;
    }
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
