package model.data;



import java.io.Serializable;
import java.util.ArrayList;

public class   Level implements Serializable{

    private ArrayList<Point> goals;
    private ArrayList<ArrayList<Character>> board;


    public Level(ArrayList<ArrayList<Character>> board) {

        this.board = board;
        setGoals();
        System.out.println("Goals has been loaded! Number of Goals:"+getGoals().size());
    }

    public Level()
    {

        this.board=new ArrayList<ArrayList<Character>>();
    }


    public ArrayList<ArrayList<Character>> getBoard() {
        return board;
    }

    public void setBoard(ArrayList<ArrayList<Character>> board) {
        this.board = board;
    }

    public boolean isEmpty(){
        return board.isEmpty();
    }

    public void setGoals()
    {
        if(getBoard().isEmpty()) return;
        goals=new ArrayList<Point>();
        for (int i = 0; i < getBoard().size(); i++) {
            for (int j = 0; j < getBoard().get(i).size(); j++) {
                if (getBoard().get(i).get(j).equals('o')){
                    //System.out.println("Found goal! its on: ("+j+","+i+")");
                    goals.add(new Point(j, i));
                }
            }
        }
    }
    public ArrayList<Point> getGoals(){return goals;}

}
