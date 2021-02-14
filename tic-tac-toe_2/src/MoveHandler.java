import java.util.HashSet;
import java.util.Set;

public class MoveHandler {

    private final BoardHandler boardHandler;
    private int counter = 0;

    public MoveHandler(BoardHandler boardHandler) {
        this.boardHandler = boardHandler;
    }

    public void generateMoveTree(State previousState ,boolean turn, int level, int player) {
        Board board = boardHandler.getBoardCopy(previousState.getCurrentState());
        double heuristic = -100;
        if (board.isOverFor(player)) {
            if (board.hasWon(player)) {
                heuristic = Math.pow( 2 , -1 * level );
                //heuristic = (int) (-1 * Math.pow(2,level));
            }
            else if (board.hasLost(player)) {
                heuristic = Math.pow( 2 , level );

                //heuristic = (int) (1 * Math.pow(2, level));
            }
            else if (board.hasDrawn()) {
                heuristic = Math.pow( 2 , 0 );
            }
            previousState.setHeuristicValue(heuristic);
            /*counter++;
            System.out.println(board);
            System.out.println(counter);*/
            return;
        }
        if (turn) {
            for (int i = 0 ; i < board.getRowSize() ; i++) {
                for (int j = 0 ; j < board.getColumnSize() ; j++) {
                    if (board.isCellEmpty(i,j)) {
                        Move move = new Move(1,i,j);
                        board.addToken(move);
                        State state = new State(boardHandler.getBoardCopy(board),new HashSet<>(), -100);
                        previousState.addChildState(state);
                        //System.out.println(state);
                        board.removeMove(move);
                    }
                }
            }
            Set<State> stateSet = previousState.getChildStates();
            for (State state : stateSet) {
                generateMoveTree(state,false, level + 1, player);
            }
        }
        else {
            for (int i = 0 ; i < board.getRowSize() ; i++) {
                for (int j = 0 ; j < board.getColumnSize() ; j++) {
                    if (board.isCellEmpty(i,j)) {
                        Move move = new Move(0,i,j);
                        board.addToken(move);
                        State state = new State(boardHandler.getBoardCopy(board),new HashSet<>(), -100);
                        previousState.addChildState(state);
                        //System.out.println(state);
                        board.removeMove(move);
                    }
                }
            }
            Set<State> stateSet = previousState.getChildStates();
            for (State state : stateSet) {
                generateMoveTree(state,true, level + 1, player);
            }
        }
        previousState.setHeuristicValue(getHeuristicValueForState(previousState, turn));
    }

    private double getHeuristicValueForState(State state, boolean turn) {
/*        if (minMax) {
            if (turn) {
                return min(state);
            }
            else {
                return max(state);
            }
        }
        else {
            if (turn) {
                return max(state);
            }
            else {
                return min(state);
            }
        }*/
        if (turn) {
            return getMinimumHeuristicValue(state);
        }
        else {
            return getMaximumHeuristicValue(state);
        }

/*        double heuristicValue = 1;
        Set<State> stateSet = state.getChildStates();
        for (State s : stateSet) {
            heuristicValue *= s.getHeuristicValue();
        }
        return heuristicValue*//*//*state.getChildStates().size()*//*;*/
    }

    private double getMinimumHeuristicValue(State state) {
        double heuristicValue = Double.POSITIVE_INFINITY;
        Set<State> stateSet = state.getChildStates();
        for (State s : stateSet) {
            if (s.getHeuristicValue() < heuristicValue) {
                heuristicValue = s.getHeuristicValue();
            }
            /*heuristicValue += s.getHeuristicValue();*/
        }
        return heuristicValue;
    }
    private double getMaximumHeuristicValue(State state) {
        double heuristicValue = Double.NEGATIVE_INFINITY;
        Set<State> stateSet = state.getChildStates();
        for (State s : stateSet) {
            if (s.getHeuristicValue() > heuristicValue) {
                heuristicValue = s.getHeuristicValue();
            }
            /*heuristicValue += s.getHeuristicValue();*/
        }
        return heuristicValue;
    }

}
