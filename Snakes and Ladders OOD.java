public interface Board {
  public void reset();
}
// Should be singleton
public class SnakeBoard implements Board {
  List<Snake> snakes;
  List<Ladder> ladders;
  List<SnakeGamePlayer> players;
  Cell[][] cells;
  SixFaceDice dice = new SixFaceDice();
  SnakeBoard() {
    // initialize snakes, ladder, cells & players
  }
  SnakeBoard(List<SnakeGamePlayer> snakes, List<Ladders> ladders, 
      List<Player> players, int row, int column) {
    // assign member vars
    this.cell = new Cell[row][column]
  }
  boolean winner() {
    // return null until no one won the game
  }
  public void reset() {}
  public void bite() {
    //  if new position of player is same as anyone of the snake head pos, update player position
  }
  public void moveUpTheLadder() {}
}

class Ladder {
  private int start;
  private end;
}

class Snake {
  private int[] head;//x,y co-ordinate of player
  private  int[] tail;
}

public interface Player {
  public int score();
  public int move();
}

public SnakeGamePlayer implements Player {
  private int pid;
  // implement score and move method
  public int[]  currPositionOnBoard() {
    // return x,y co-ordinate of player in board
  }
}

public interface Dice {
  public int roll();
}
public class SixFaceDice implements Dice {
  private static SixFaceDice s;
  public int roll() {
    // generate random number between 1 to 6
  }
  static private SixFaceDice() {
  }
  public void getSixFaceDice() {
    if (s == null) { return new SixFaceDice()}
    else {
     return this.s;
    }
  }
}
public class Cell {
  List<Integer> ids; // players ids who are in the cell
  int num;
  int row;
  int column;
}
