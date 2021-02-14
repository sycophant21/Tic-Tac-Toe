import java.util.Stack;

public class BoardHandler {
    private Board currentBoard;

    public BoardHandler(int boardSize) {
        initialise(boardSize);
    }
    private void initialise(int boardSize) {
        currentBoard = new Board(new int[boardSize][boardSize]);
    }

    public void printBoard() {
        currentBoard.printBoard();
    }

    public boolean addMove(Move move) {
        if (isMovePossible(move.getRow(), move.getColumn())) {
            currentBoard.addToken(move);
            return true;
        }
        else {
            return false;
        }
    }
    private boolean isMovePossible(int row, int column) {
        return currentBoard.isCellEmpty(row, column) && ((row < 3 && row > -1) && (column < 3 && column > -1));
    }

    public Board getCurrentBoardCopy() {
        return createCopy(currentBoard);
    }
    public Board getBoardCopy(Board board) {
        return createCopy(board);
    }

    private Board createCopy(Board board) {
        Board newBoard = new Board(new int[board.getRowSize()][board.getColumnSize()]);
        Stack<Move> moveQueue = new Stack<>();
        Stack<Move> moveStack = board.getMoveStack();
        while (!moveStack.empty()) {
            Move move1 = moveStack.pop();
            newBoard.addToken(move1);
            moveQueue.add(move1);
        }
        while (!moveQueue.isEmpty()) {
            moveStack.push(moveQueue.pop());
        }
        return newBoard;
    }

    public boolean isBoardFull(Board board) {
        if (board == null) {
            return !currentBoard.isMoveLeft();
        }
        else {
            return !board.isMoveLeft();
        }
    }

    public boolean isGameOver() {
        return currentBoard.isOverFor(0) || currentBoard.isOverFor(1);
    }


}
