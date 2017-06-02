package model.data;

import searchLib.data.SearchAction;
import searchLib.data.Searchable;
import searchLib.data.State;

import java.util.HashMap;

public class SokobanSearchable<T> implements Searchable<T> {

    private T t;
    private Level level;

    public SokobanSearchable(Level l,Point goal){this.level=l;this.t= (T) new Point(goal);}

    @Override
    public State getInitialState() {
        State<Point> state=new State<>(level.getInitialState());
        return state;
    }

    @Override
    public State getGoalState() {
        State<Point> goal=new State<>((Point) t);
        return goal;
    }

    @Override
    public HashMap<SearchAction, State> getAllPossibleMoves(State s) {
        Point point=(Point)s.getState();
        HashMap<SearchAction,State> moves=new HashMap<>();

        switch(level.getBoard().get(point.getY()).get(point.getX()-1)) //Checks Left of State
        {
            case(' '):
            {
                State<Point> possibleState=new State<>(new Point(point.getX()-1,point.getY()));
                moves.put(new SearchAction("left"),possibleState);
                break;
            }
            case('@'): {
                if (!(level.getBoard().get(point.getY()).get(point.getX() - 2).equals('@') || level.getBoard().get(point.getY()).get(point.getX() - 2).equals('#'))) {
                    State<Point> possibleState = new State<>(new Point(point.getX() - 1, point.getY()));
                    moves.put(new SearchAction("left"), possibleState);
                }
                break;
            }
        }

        switch(level.getBoard().get(point.getY()).get(point.getX()+1)) //Checks Right of State
        {
            case(' '):
            {
                State<Point> possibleState=new State<>(new Point(point.getX()+1,point.getY()));
                moves.put(new SearchAction("right"),possibleState);
                break;
            }
            case('@'): {
                if (!(level.getBoard().get(point.getY()).get(point.getX() + 2).equals('@') || level.getBoard().get(point.getY()).get(point.getX() + 2).equals('#'))) {
                    State<Point> possibleState = new State<>(new Point(point.getX() + 1, point.getY()));
                    moves.put(new SearchAction("right"), possibleState);
                    break;
                }
            }
        }
        switch(level.getBoard().get(point.getY()-1).get(point.getX())) //Checks Up of State
        {
            case(' '):
            {
                State<Point> possibleState=new State<>(new Point(point.getX(),point.getY()-1));
                moves.put(new SearchAction("up"),possibleState);
                break;
            }
            case('@'): {
                if (!(level.getBoard().get(point.getY()-2).get(point.getX()).equals('@') || level.getBoard().get(point.getY()-2).get(point.getX()).equals('#'))) {
                    State<Point> possibleState = new State<>(new Point(point.getX(), point.getY()-1));
                    moves.put(new SearchAction("up"), possibleState);
                    break;
                }
            }
        }
        switch(level.getBoard().get(point.getY()+1).get(point.getX())) //Checks Below of State
        {
            case(' '):
            {
                State<Point> possibleState=new State<>(new Point(point.getX(),point.getY()+1));
                moves.put(new SearchAction("down"),possibleState);
                break;
            }
            case('@'): {
                if (!(level.getBoard().get(point.getY()+2).get(point.getX()).equals('@') || level.getBoard().get(point.getY()+2).get(point.getX()).equals('#'))) {
                    State<Point> possibleState = new State<>(new Point(point.getX(), point.getY()+1));
                    moves.put(new SearchAction("down"), possibleState);
                    break;
                }
            }
        }

        return moves;



    }





    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

}
