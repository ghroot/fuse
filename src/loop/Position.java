package loop;

public class Position {
  public float x;
  public float y;

  public Position(float x, float y) {
    this.x = x;
    this.y = y;
  }

  public Position() {
    this(0, 0);
  }

  public boolean equals(Position position) {
    return position.x == this.x && position.y == this.y;
  }

  public String toString() {
    return x + ", " + y;
  }
}
