package model.data;

import searchLib.data.SearchAction;
import searchLib.data.Searchable;
import searchLib.data.State;

import java.util.HashMap;

public class SokobanSearchable<T> implements Searchable<T> {

    private T point;
    private Level level;

    @Override
    public State getInitialState() {
        State<Point> state=new State<>(level.getInitialState());
        return state;
    }

    @Override
    public State getGoalState() {
        State<Point> goal=new State<>((Point)point);
        return goal;
    }

    @Override
    public HashMap<SearchAction, State> getAllPossibleMoves(State s) {
        return null;
    }





    public T getPoint() {
        return point;
    }

    public void setPoint(T point) {
        this.point = point;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

}
