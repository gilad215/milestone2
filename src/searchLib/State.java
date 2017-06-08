package searchLib;

public class State<T> implements Comparable<State<T>> {
    private T state;
    private int cost;
    private State<T> cameFrom;
    private SearchAction action;



    public T getState() {
        return state;
    }
    public void setState(T state) {
        this.state = state;
    }
    public int getCost() {
        return cost;
    }
    public void setCost(int cost) {
        this.cost = cost;
    }
    public void UpCost(){this.cost++;}
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
    public boolean equals(Object obj)
    {
        State<T> state=(State<T>)obj;
        return this.equals(state);
    }

    @Override
    public int hashCode() {
        return state.hashCode();
    }

    @Override
    public String toString() {
        return state.toString();
    }

    public SearchAction getAction() {
        return action;
    }

    public void setAction(SearchAction action) {
        this.action = action;
    }

    @Override
    public int compareTo(State<T> o) {
        return getCost()-o.getCost();
    }

}
