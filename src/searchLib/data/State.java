package searchLib.data;

public class State<T> {
    private T state;
    private double cost;
    private State<T> cameFrom;
    private Action action;

    public T getState() {
        return state;
    }
    public void setState(T state) {
        this.state = state;
    }
    public double getCost() {
        return cost;
    }
    public void setCost(double cost) {
        this.cost = cost;
    }
    public State<T> getCameFrom() {
        return cameFrom;
    }
    public void setCameFrom(State<T> cameFrom) {
        this.cameFrom = cameFrom;
    }

    public State(T state) {
        this.state = state;
    }

    public boolean equals(State<T> s) {
        return state.equals(s.state);
    }

    @Override
    public int hashCode() {
        return state.hashCode();
    }

    @Override
    public String toString() {
        return state.toString();
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }
}
