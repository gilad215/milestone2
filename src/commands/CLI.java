package commands;
//by gladis
import levels.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CLI {
    private Level level=null;
    private ArrayList<Point> Goals;

    private LoadLevelCommand load;
    private DisplayLevelCommand display;
    private SaveLevelCommand save;
    private MoveLevelCommand move;
    private ExitCommand exit;

    public void initCommand() throws FileNotFoundException {
        System.out.println("Enter Command:\n>Load file name\n>Display\n>Move {up, down, left, right}\n>Save file name\n>Exit");
        Scanner scanner=new Scanner(System.in);
        String input=scanner.nextLine();
        String[]inputs=input.split("\\s+"); //splits the line to 2 strings
        while(inputs.length>2) {
            System.out.println("Invalid Input.");
            input = scanner.nextLine();
            inputs=input.split("\\s+");
        } //asking for an input if its more than 3 words.

        while(true)
        {
            switch (inputs[0].toUpperCase()) //making the first word upper case
            {
                case("LOAD"):
                {
                    if(inputs.length==1)
                    {
                        System.out.println("Invalid File Path.");
                        break;
                    }
                    load =new LoadLevelCommand(inputs[1]);
                    load.execute();
                    setLevel(load.getLvl());
                    getGoals();
                    break;
                }
                case("DISPLAY"):
                {
                    if(getLevel()==null) {
                        System.out.println("No level loaded!");
                        return;
                    }
                    display =new DisplayLevelCommand(getLevel());
                    display.execute();
                    }
                    break;

                case("SAVE"):
                {
                    if(inputs.length==1)
                    {
                        System.out.println("Invalid File Path.");
                        break;
                    }
                    save=new SaveLevelCommand(getLevel(),inputs[1]);
                    save.execute();

                break;
                }
                case("MOVE"): {
                    move = new MoveLevelCommand(getLevel(), inputs[1], Goals);
                    move.execute();
                    setLevel(move.getLvl());
                    if(move.isFinished()) display.execute();

                    break;
                }
                case("EXIT"):
                    exit = new ExitCommand();
                    exit.execute();
            }
             input=scanner.nextLine();
             inputs=input.split("\\s+");
             for (String s:inputs) {s=s.toUpperCase();}

        }




    }

    public Level getLevel() {
        return level;
    }
    public void getGoals()
    {
        if(getLevel()==null) return;
        Goals=new ArrayList<Point>();
        for (int i = 0; i < getLevel().getBoard().size(); i++) {
            for (int j = 0; j < getLevel().getBoard().get(i).size(); j++) {
                if (getLevel().getBoard().get(i).get(j).equals('o')){
                    //System.out.println("Found goal! its on: ("+j+","+i+")");
                    Goals.add(new Point(j, i));
                }
            }
        }
    }
    public void setLevel(Level level) {
        this.level = level;
    }
}