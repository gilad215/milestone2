package model.data;

import searchLib.data.SearchAction;
import searchLib.data.Searchable;
import searchLib.data.State;

import java.util.HashMap;
import java.util.Objects;

public class SokobanSearchable<T> implements Searchable<T> {

    private T t;
    private Level level;


    private String type;
    private State<Point> initial;
    private State<Point> goal;


    HashMap<SearchAction,State> moves=new HashMap<>();


    public SokobanSearchable(Level l,String Type,Point initial,Point goal){this.level=l;this.goal=new State<>(goal);this.initial=new State<>(initial);setType(Type);}

    @Override
    public State getInitialState() {
        return initial;
    }

    @Override
    public State getGoalState() {
        return goal;
    }

    @Override
    public HashMap<SearchAction, State> getAllPossibleMoves(State s) {
        Point point=(Point)s.getState();
        System.out.println("looking for states around: "+point.toString());

        if(Objects.equals(getType(), "soko")) {
            switch (level.getBoard().get(point.getY()).get(point.getX() - 1)) //Checks Left of State
            {
                case (' '): {
                    State<Point> possibleState = new State<>(new Point(point.getX() - 1, point.getY()));
                    moves.put(new SearchAction("left"), possibleState);
                    break;
                }
                case ('@'): {
                    if (!(level.getBoard().get(point.getY()).get(point.getX() - 2).equals('@') || level.getBoard().get(point.getY()).get(point.getX() - 2).equals('#'))) {
                        State<Point> possibleState = new State<>(new Point(point.getX() - 1, point.getY()));
                        moves.put(new SearchAction("left"), possibleState);
                    }
                    break;
                }
                case ('o'): {
                    State<Point> possibleState = new State<>(new Point(point.getX() - 1, point.getY()));
                    moves.put(new SearchAction("left"), possibleState);
                    break;

                }
            }

            switch (level.getBoard().get(point.getY()).get(point.getX() + 1)) //Checks Right of State
            {
                case (' '): {
                    State<Point> possibleState = new State<>(new Point(point.getX() + 1, point.getY()));
                    moves.put(new SearchAction("right"), possibleState);
                    break;
                }
                case ('@'): {
                    if (!(level.getBoard().get(point.getY()).get(point.getX() + 2).equals('@') || level.getBoard().get(point.getY()).get(point.getX() + 2).equals('#'))) {
                        State<Point> possibleState = new State<>(new Point(point.getX() + 1, point.getY()));
                        moves.put(new SearchAction("right"), possibleState);
                        break;
                    }

                }
                case ('o'): {
                    State<Point> possibleState = new State<>(new Point(point.getX() + 1, point.getY()));
                    moves.put(new SearchAction("right"), possibleState);
                    break;

                }
            }
            switch (level.getBoard().get(point.getY() - 1).get(point.getX())) //Checks Up of State
            {
                case (' '): {
                    State<Point> possibleState = new State<>(new Point(point.getX(), point.getY() - 1));
                    moves.put(new SearchAction("up"), possibleState);
                    break;
                }
                case ('@'): {
                    if (!(level.getBoard().get(point.getY() - 2).get(point.getX()).equals('@') || level.getBoard().get(point.getY() - 2).get(point.getX()).equals('#'))) {
                        State<Point> possibleState = new State<>(new Point(point.getX(), point.getY() - 1));
                        moves.put(new SearchAction("up"), possibleState);
                        break;
                    }
                }
                case ('o'): {
                    State<Point> possibleState = new State<>(new Point(point.getX(), point.getY() - 1));
                    moves.put(new SearchAction("up"), possibleState);
                    break;

                }
            }
            switch (level.getBoard().get(point.getY() + 1).get(point.getX())) //Checks Below of State
            {
                case (' '): {
                    State<Point> possibleState = new State<>(new Point(point.getX(), point.getY() + 1));
                    moves.put(new SearchAction("down"), possibleState);
                    break;
                }
                case ('@'): {
                    if (!(level.getBoard().get(point.getY() + 2).get(point.getX()).equals('@') || level.getBoard().get(point.getY() + 2).get(point.getX()).equals('#'))) {
                        State<Point> possibleState = new State<>(new Point(point.getX(), point.getY() + 1));
                        moves.put(new SearchAction("down"), possibleState);
                        break;
                    }
                }
                case ('o'): {
                    State<Point> possibleState = new State<>(new Point(point.getX(), point.getY() + 1));
                    moves.put(new SearchAction("down"), possibleState);
                    break;

                }
            }
        }
        if(Objects.equals(getType(), "box")) {
            switch (level.getBoard().get(point.getY()).get(point.getX() - 1)) //Checks Left
            {
                case (' '): {
                    if (clearAt(point.getX() + 1, point.getY()) || goalAt(point.getX() + 1, point.getY()) || sokoAt(point.getX() + 1, point.getY())) {
                        State<Point> possibleState = new State<>(new Point(point.getX() - 1, point.getY()));
                        moves.put(new SearchAction("left"), possibleState);
                        break;
                    }
                    break;
                }
                case ('o'): {
                    if (clearAt(point.getX() + 1, point.getY()) || goalAt(point.getX() + 1, point.getY()) || sokoAt(point.getX() + 1, point.getY())) {
                        State<Point> possibleState = new State<>(new Point(point.getX() - 1, point.getY()));
                        moves.put(new SearchAction("left"), possibleState);
                        break;
                    }
                    break;
                }
            }
            switch (level.getBoard().get(point.getY()).get(point.getX() +1)) //Checks Right
            {
                case (' '): {
                    if (clearAt(point.getX() - 1, point.getY()) || goalAt(point.getX() - 1, point.getY()) || sokoAt(point.getX() - 1, point.getY())) {
                        State<Point> possibleState = new State<>(new Point(point.getX() + 1, point.getY()));
                        moves.put(new SearchAction("right"), possibleState);
                        break;
                    }
                    break;
                }
                case ('o'): {
                    if (clearAt(point.getX() - 1, point.getY()) || goalAt(point.getX() - 1, point.getY()) || sokoAt(point.getX() - 1, point.getY())) {
                        State<Point> possibleState = new State<>(new Point(point.getX() + 1, point.getY()));
                        moves.put(new SearchAction("right"), possibleState);
                        break;
                    }
                    break;
                }
            }
            switch (level.getBoard().get(point.getY()-1).get(point.getX())) //Checks Up
            {
                case (' '): {
                    if (clearAt(point.getX(), point.getY()+1) || goalAt(point.getX(), point.getY()+1) || sokoAt(point.getX(), point.getY()+1)) {
                        State<Point> possibleState = new State<>(new Point(point.getX(), point.getY()-1));
                        moves.put(new SearchAction("up"), possibleState);
                        break;
                    }
                    break;
                }
                case ('o'): {
                    if (clearAt(point.getX(), point.getY()+1) || goalAt(point.getX(), point.getY()+1) || sokoAt(point.getX(), point.getY()+1)) {
                        State<Point> possibleState = new State<>(new Point(point.getX(), point.getY()-1));
                        moves.put(new SearchAction("up"), possibleState);
                        break;
                    }
                    break;
                }
            }
            switch (level.getBoard().get(point.getY()+1).get(point.getX())) //Checks Down
            {
                case (' '): {
                    if (clearAt(point.getX(), point.getY()-1) || goalAt(point.getX(), point.getY()-1) || sokoAt(point.getX(), point.getY()-1)) {
                        State<Point> possibleState = new State<>(new Point(point.getX(), point.getY()+1));
                        moves.put(new SearchAction("down"), possibleState);
                        break;
                    }
                    break;
                }
                case ('o'): {
                    if (clearAt(point.getX(), point.getY()-1) || goalAt(point.getX(), point.getY()-1) || sokoAt(point.getX(), point.getY()-1)) {
                        State<Point> possibleState = new State<>(new Point(point.getX(), point.getY()+1));
                        moves.put(new SearchAction("down"), possibleState);
                        break;
                    }
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

    public void setGoal(State<Point> goal) {
        this.goal = goal;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private boolean clearAt(int x, int y)
    {
        return level.getBoard().get(y).get(x).equals(' ');
    }
    private boolean boxAt(int x, int y)
    {
        return level.getBoard().get(y).get(x).equals('@');
    }
    private boolean goalAt(int x, int y)
    {
        return level.getBoard().get(y).get(x).equals('o');
    }
    private boolean sokoAt(int x, int y)
    {
        return level.getBoard().get(y).get(x).equals('A');
    }
}

