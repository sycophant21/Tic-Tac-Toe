import java.util.Objects;

public class Move {
    private final int token;
    private final int row;
    private final int column;

    public Move(int token, int row, int column) {
        this.token = token;
        this.row = row;
        this.column = column;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public int getToken() {
        return token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return token == move.token &&
                row == move.row &&
                column == move.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, row, column);
    }
}
