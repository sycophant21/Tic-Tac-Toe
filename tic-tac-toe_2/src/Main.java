import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
      /*  System.out.println("Enter board size");
        System.out.println("(Board size = N, as in N*N)");
        BoardHandler boardHandler = new BoardHandler(scanner.nextInt());*/
        BoardHandler boardHandler = new BoardHandler(3);
        State statesTree = new State(boardHandler.getCurrentBoardCopy(), new HashSet<>(), -100);
        StateMoveBoardHandler stateMoveBoardHandler = new StateMoveBoardHandler(boardHandler);
        MoveHandler moveHandler = new MoveHandler(boardHandler);
        GameHandler gameHandler = new GameHandler(boardHandler, stateMoveBoardHandler, scanner);
        int player = gameHandler.initialiseGame();
        moveHandler.generateMoveTree(statesTree, true, 0, player);
        gameHandler.playGame(player, statesTree);


    }



}
