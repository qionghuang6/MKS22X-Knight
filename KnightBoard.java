public class KnightBoard{
  int[][] board;
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
}
