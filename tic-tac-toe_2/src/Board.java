import org.jetbrains.annotations.NotNull;

import java.util.Stack;

public class Board {
    private final int[][] crossBoard;
    private Stack<Move> moveStack;

    public Board(int[][] crossBoard) {
        this.crossBoard = crossBoard;
        initialiseBoard();
    }

    private void initialiseBoard() {
        for (int i = 0 ; i < crossBoard.length ; i++) {
            for (int j = 0; j < crossBoard[0].length; j++) {
                crossBoard[i][j] = getValue(crossBoard.length,i,j);
            }
        }
        moveStack = new Stack<>();
    }

    private int getValue(int length, int row, int column) {
        int value = 2;
        for (int i = 0 ; i <= length ; i++) {
            value += Math.pow(row+column,i);
        }
        return value;
    }

    public void printBoard() {
        System.out.println(toString());
    }


    public boolean isCellEmpty(int row, int column) {
        return crossBoard[row][column] > 1;
    }

    public void addToken(@NotNull Move move) {
        crossBoard[move.getRow()][move.getColumn()] = move.getToken();
        moveStack.push(move);
    }

    public void removeMove(Move move) {
        if (!isCellEmpty(move.getRow(), move.getColumn())) {
            crossBoard[move.getRow()][move.getColumn()] = getValue(crossBoard.length, move.getRow(), move.getColumn());
            moveStack.remove(move);
        } else {
            System.out.println("Block Already Empty");
        }
    }

    public int getRowSize() {
        return crossBoard.length;
    }

    public int getColumnSize() {
        return crossBoard[0].length;
    }

    public Move getLastMove() {
        Move move = moveStack.pop();
        moveStack.push(move);
        return move;
    }

    public Stack<Move> getMoveStack() {
        return moveStack;
    }

    @Override
    public String toString() {
        String boardState = "";
        for (int[] array : crossBoard) {
            for (int i : array) {
                if (i == 1) {
                    boardState = boardState.concat("X\t");
                } else if (i == 0) {
                    boardState = boardState.concat("O\t");
                } else {
                    boardState = boardState.concat("_\t");
                }
            }
            boardState = boardState.concat("\n");
        }

        return boardState;
    }

    public boolean isOverFor(int player) {
        return (hasLost(player) || hasWon(player) || hasDrawn());
    }

    public boolean hasWon(int whoWon) {
        if (whoWon == 1) {
            return isRowFilledWithX(isRowFilled()) || isColumnFilledWithX(isColumnFilled()) || isDiagonalFilledWithX(isDiagonalFilled());
        } else {
            return isRowFilledWithO(isRowFilled()) || isColumnFilledWithO(isColumnFilled()) || isDiagonalFilledWithO(isDiagonalFilled());
        }
    }

    public boolean hasLost(int whoWon) {
        if (whoWon == 0) {
            return isRowFilledWithX(isRowFilled()) || isColumnFilledWithX(isColumnFilled()) || isDiagonalFilledWithX(isDiagonalFilled());
        } else {
            return isRowFilledWithO(isRowFilled()) || isColumnFilledWithO(isColumnFilled()) || isDiagonalFilledWithO(isDiagonalFilled());
        }
    }

    public boolean hasDrawn() {
        return !isMoveLeft() && ((!hasWon(1) || !hasWon(0)) && (!hasLost(1) || !hasLost(0)));
    }

    private int isRowFilled() {
        int rowFilled;
        boolean isRowFilled;
        for (int i = 0; i < crossBoard.length; i++) {
            isRowFilled = true;
            rowFilled = i;
            for (int j = 0; j < crossBoard[0].length - 1; j++) {
                if (crossBoard[i][j] != crossBoard[i][j + 1]) {
                    isRowFilled = false;
                    break;
                }
            }
            if (isRowFilled) {
                return rowFilled;
            }

        }
        return -1;
    }

    private int isColumnFilled() {
        int columnFilled;
        boolean isColumnFilled;
        for (int i = 0; i < crossBoard[0].length; i++) {
            isColumnFilled = true;
            columnFilled = i;
            for (int j = 0; j < crossBoard.length - 1; j++) {
                if (crossBoard[j][i] != crossBoard[j+1][i]) {
                    isColumnFilled = false;
                    break;
                }
            }
            if (isColumnFilled) {
                return columnFilled;
            }
        }
        return -1;
    }

    private int isDiagonalFilled() {
        int diagonalEqual = 1;
        for (int i = 0; i < crossBoard.length - 1; i++) {
            if (crossBoard[i][i] != crossBoard[i + 1][i + 1]) {
                diagonalEqual = 2;
                break;
            }
        }
        if (diagonalEqual != 1) {
            for (int i = 0; i < crossBoard.length - 1; i++) {
                if (crossBoard[i][crossBoard.length - 1 - i] != crossBoard[i + 1][crossBoard.length - i - 2]) {
                    diagonalEqual = -1;
                    break;
                }
            }
        }
        return diagonalEqual;
    }

    private boolean isRowFilledWithX(int row) {
        if (row == -1) {
            return false;
        } else {
            return crossBoard[row][0] == 1;
        }
    }

    private boolean isRowFilledWithO(int row) {
        if (row == -1) {
            return false;
        } else {
            return crossBoard[row][0] == 0;
        }
    }

    private boolean isColumnFilledWithX(int column) {
        if (column == -1) {
            return false;
        } else {
            return crossBoard[0][column] == 1;
        }
    }

    private boolean isColumnFilledWithO(int column) {
        if (column == -1) {
            return false;
        } else {
            return crossBoard[0][column] == 0;
        }
    }

    private boolean isDiagonalFilledWithX(int diagonal) {
        if (diagonal == -1) {
            return false;
        } else {
            if (diagonal == 1) {
                return crossBoard[0][0] == 1;
            }
            else {
                return crossBoard[0][crossBoard.length - 1] == 1;
            }
        }
    }

    private boolean isDiagonalFilledWithO(int diagonal) {
        if (diagonal == -1) {
            return false;
        } else {
            if (diagonal == 1) {
                return crossBoard[0][0] == 0;
            }
            else {
                return crossBoard[0][crossBoard.length - 1] == 0;
            }
        }
    }

    public boolean isMoveLeft() {
        for (int[] ints : crossBoard) {
            for (int j = 0; j < crossBoard[0].length; j++) {
                if (ints[j] > 1) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getLevelOfInequality(Board board1) {
        int levelOfInEquality = 0;
        for (int i = 0; i < crossBoard.length; i++) {
            for (int j = 0; j < crossBoard[0].length; j++) {
                if (board1.crossBoard[i][j] != crossBoard[i][j]) {
                    levelOfInEquality++;
                }
            }
        }
        return levelOfInEquality;
    }

    public int getDifferentPlaceOfValue(Board board1) {
        for (int i = 0; i < crossBoard.length; i++) {
            for (int j = 0; j < crossBoard[0].length; j++) {
                if (crossBoard[i][j] != board1.crossBoard[i][j]) {
                    if (board1.crossBoard[i][j] > 1) {
                        return -1;
                    }
                    return (10 * i) + j;
                }
            }
        }
        return -1;
    }

    public int getDifferentValue(Board board1) {
        int rowAndColumn = getDifferentPlaceOfValue(board1);
        int row = rowAndColumn / 10;
        int column = rowAndColumn % 10;
        return board1.crossBoard[row][column];
    }
}
