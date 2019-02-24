import java.util.Arrays;
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

    return solveOpt(startingRows,startingCols,0);
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
  private boolean solveOpt(int row, int col, int level){
    board[row][col] = level + 1;
    if(level + 1 >= board.length * board[0].length){
      return true;
    }
    int[][] moves = new int[8][3];
    for (int i =0;i < 8 ;i++ ) {
      moves[i][0] = directions[i][0];
      moves[i][1] = directions[i][1];
      if(row + directions[i][0] >= 0 && row + directions[i][0] < board.length &&
        col + directions[i][1] >= 0 && col + directions[i][1] < board[0].length){
          moves[i][2] = heatMap[row + directions[i][0]][col + directions[i][1]];
        }
    }
    Arrays.sort(moves, (a, b) -> Double.compare(a[2], b[2]));
    for (int[]  m: moves) {
      System.out.print(Arrays.toString(m));
      System.out.print(" ");
    }
    System.out.println();
    System.out.println(this);
    for (int[] move: moves) {
      if (move[2] != 0 && board[row + move[0]][col + move[1]] == 0) {
        heatMap[row + move[0]][col + move[1]]--;
        if(solveOpt(row + move[0], col + move[1], level + 1)){
            return true;
        }
        heatMap[row +move[0]][col + move[1]]++;
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
