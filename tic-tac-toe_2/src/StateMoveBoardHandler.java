import java.util.Set;

public class StateMoveBoardHandler {
    private final BoardHandler boardHandler;

    public StateMoveBoardHandler(BoardHandler boardHandler) {
        this.boardHandler = boardHandler;
    }

    public Move getBestMove(State state, int machine) {
        return generateMove(state, getBestState(state, machine));
    }

    public Move generateMove(State currentState, State futureState) {
        Board currentBoard = boardHandler.getBoardCopy(currentState.getCurrentState());
        Board futureBoard = boardHandler.getBoardCopy(futureState.getCurrentState());
        if (((currentBoard.getRowSize() != futureBoard.getRowSize()) || (currentBoard.getColumnSize() != futureBoard.getColumnSize()))) {
            System.out.println("UnEqual Boards");
            return null;
        } else {
            if (currentBoard.getLevelOfInequality(futureBoard) != 1) {
                System.out.println("Error");
            } else {
                int rowAndColumn = currentBoard.getDifferentPlaceOfValue(futureBoard);
                if (rowAndColumn == -1) {
                    System.out.println("Equal Boards");
                } else {
                    int row = rowAndColumn / 10;
                    int column = rowAndColumn % 10;

                    return new Move(currentBoard.getDifferentValue(futureBoard), row, column);
                }
            }
        }
        return null;
    }

    public State getBestState(State state, int machine) {
        if (machine == 0) {
            return getLeastNegativeState(state, machine, 1);
        }
        else {
            return getLeastPositiveState(state, machine);
        }


    }

    private State getLeastNegativeState(State state, int machine, int human) {
        double heuristicValue = Double.NEGATIVE_INFINITY;
        Set<State> childStates = state.getChildStates();
        State bestState = childStates.iterator().next();
        for (State s : childStates) {
            if (s.getCurrentState().hasWon(machine) || s.getCurrentState().hasDrawn()) {
                bestState = s;
                break;
            }
            if (heuristicValue <= s.getHeuristicValue()) {
                State losingStateForMachine = hasLosingState(s, machine);
                State losingStateForHuman = hasLosingState(s, human);
                if (losingStateForMachine != null) {
                    continue;
                } if (losingStateForHuman != null) {
                    bestState = s;
                    break;
                }else {
                    heuristicValue = s.getHeuristicValue();
                    bestState = s;
                }
            }
        }
        return bestState;
    }

    private State getLeastPositiveState(State state, int machine) {
        double heuristicValue = Double.POSITIVE_INFINITY;
        Set<State> childStates = state.getChildStates();
        State bestState = childStates.iterator().next();
        for (State s : childStates) {
            if (s.getCurrentState().hasWon(machine) || s.getCurrentState().hasDrawn()) {
                bestState = s;
                break;
            }
            if (heuristicValue >= s.getHeuristicValue()) {
                State losingStateForMachine = hasLosingState(s, machine);
                State losingStateForHuman = hasLosingState(s, 0);
                if (losingStateForMachine != null) {
                    continue;
                } if (losingStateForHuman != null) {
                    bestState = s;
                    break;
                }else {
                    heuristicValue = s.getHeuristicValue();
                    bestState = s;
                }
            }
        }
        return bestState;
    }




    public State getStateFromBoard(State state, Board board) {
        Set<State> stateSet = state.getChildStates();
        for (State s : stateSet) {
            if (s.getCurrentState().getLevelOfInequality(board) == 0) {
                return s;
            }
        }
        return null;
    }


    private State hasLosingState(State state, int whoToLose) {
        Set<State> stateSet = state.getChildStates();
        for (State s : stateSet) {
            if (s.getCurrentState().hasLost(whoToLose)) {
                return s;
            }
        }
        return null;
    }

}