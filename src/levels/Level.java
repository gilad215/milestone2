package levels;



import java.io.Serializable;
import java.util.ArrayList;

public class Level implements Serializable{


    private ArrayList<ArrayList<Character>> board;

    public Level(ArrayList<ArrayList<Character>> board) {

        this.board = board;
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

}
