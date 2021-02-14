import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class GameHandler {
    private final Scanner scanner;
    private final BoardHandler boardHandler;
    private final StateMoveBoardHandler stateMoveBoardHandler;
    private boolean randomTurn = true;

    public GameHandler(BoardHandler boardHandler, StateMoveBoardHandler stateMoveBoardHandler, Scanner scanner) {
        this.boardHandler = boardHandler;
        this.stateMoveBoardHandler = stateMoveBoardHandler;
        this.scanner = scanner;
    }

    public int initialiseGame() {
        int token = 2;
        while (token != 1 && token != 0) {
            System.out.println("Choose token");
            System.out.println("Enter 1 for X and 0(zero) for O");
            token = scanner.nextInt();
            if (token != 1 && token != 0) {
                System.out.println("Invalid");
            }
        }
        return token;
    }

    public void playGame(int player, State statesTree) {
        if (player == 1) {
            while (!boardHandler.isBoardFull(null) && !boardHandler.isGameOver()) {
                statesTree = humansTurnForConsole(statesTree, 1);
                if (boardHandler.isBoardFull(null) || boardHandler.isGameOver()) {
                    printResult(1);
                    break;
                }
                statesTree = machinesTurnForConsole(statesTree, 0);
            }
        } else {
            while (!boardHandler.isBoardFull(null) && !boardHandler.isGameOver()) {
                statesTree = machinesTurnForConsole(statesTree, 1);
                if (boardHandler.isBoardFull(null) || boardHandler.isGameOver()) {
                    printResult(0);
                    break;
                }
                statesTree = humansTurnForConsole(statesTree, 0);
            }
        }
    }

    private State machinesTurnForConsole(State state, int machine) {
        System.out.println("Machine's Turn :");
        Move move;
        if (randomTurn) {
            int bound = 1 ;
            if (state.getChildStates().size() > 1) {
                bound = (new Random().nextInt(state.getChildStates().size() - 1));
            }
            Iterator<State> stateIterator = state.getChildStates().iterator();
            State state1 = stateMoveBoardHandler.getBestState(state, machine);
            for (int i = 0; i < bound; i++) {
                state1 = stateIterator.next();
            }
            move = stateMoveBoardHandler.generateMove(state, state1);
            boardHandler.addMove(move);
            state = state1;
            randomTurn = false;
        } else {
            move = stateMoveBoardHandler.getBestMove(state, machine);
            boardHandler.addMove(move);
            state = stateMoveBoardHandler.getBestState(state, machine);
        }
        System.out.println("Row = " + (move.getRow() + 1));
        System.out.println("Column = " + (move.getColumn() + 1));
        boardHandler.printBoard();
        return state;
    }

    public State machinesTurnForWindow(State state , int machine) {
        Move move;
        if (randomTurn) {
            int bound = 1 ;
            if (state.getChildStates().size() > 1) {
                bound = (new Random().nextInt(state.getChildStates().size() - 1));
            }
            Iterator<State> stateIterator = state.getChildStates().iterator();
            State state1 = stateMoveBoardHandler.getBestState(state, machine);
            for (int i = 0; i < bound; i++) {
                state1 = stateIterator.next();
            }
            move = stateMoveBoardHandler.generateMove(state, state1);
            boardHandler.addMove(move);
            state = state1;
            randomTurn = false;
        } else {
            move = stateMoveBoardHandler.getBestMove(state, machine);
            boardHandler.addMove(move);
            state = stateMoveBoardHandler.getBestState(state, machine);
        }
        return state;
    }

    private State humansTurnForConsole(State state, int human) {
        System.out.println("Your Turn :");
        System.out.print("Row = ");
        int row = scanner.nextInt() - 1;
        System.out.print("Column = ");
        int column = scanner.nextInt() - 1;
        while (!boardHandler.addMove(new Move(human, row, column))) {
            System.out.println("Invalid Move , Enter Again");
            System.out.print("Row = ");
            row = scanner.nextInt() - 1;
            System.out.print("Column = ");
            column = scanner.nextInt() - 1;
        }
        state = stateMoveBoardHandler.getStateFromBoard(state, boardHandler.getCurrentBoardCopy());
        boardHandler.printBoard();
        randomTurn = false;
        return state;
    }

    public State humansTurnForWindow(State state , int human, int row, int column) {
        boardHandler.addMove(new Move(human, row, column));
        randomTurn = false;
        return stateMoveBoardHandler.getStateFromBoard(state, boardHandler.getCurrentBoardCopy());
    }


    private void printResult(int player) {
        Board board = boardHandler.getCurrentBoardCopy();
        if (board.hasWon(player)) {
            System.out.println("You Won");
        } else if (board.hasLost(player)) {
            System.out.println("You Lost, Better Luck Next Time");
        } else if (board.hasDrawn()) {
            System.out.println("Draw");
        }
    }

}
