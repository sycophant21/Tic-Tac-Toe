import java.util.Objects;
import java.util.Set;

public class State {
    private final Board currentState;
    private final Set<State> childStates;
    private double heuristicValue;


    public State(Board currentState, Set<State> childStates, double heuristicValue) {
        this.currentState = currentState;
        this.childStates = childStates;
        this.heuristicValue = heuristicValue;
    }

    public void setHeuristicValue(double heuristicValue) {
        this.heuristicValue = heuristicValue;
    }

    public double getHeuristicValue() {
        return heuristicValue;
    }

    public Board getCurrentState() {
        return currentState;
    }

    public Set<State> getChildStates() {
        return childStates;
    }

    public void addChildState(State state) {
        childStates.add(state);
    }

    @Override
    public String toString() {
        return "State{" +
                "currentState = \n" + currentState +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return currentState.equals(state.currentState);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentState);
    }
}
