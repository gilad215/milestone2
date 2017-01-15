package commands;
import levels.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class LoadLevelCommand implements Command {
    private Level lvl;
    private String pathToFile;
    private Loader loader;
    private ArrayList<Point> Goals;


    public LoadLevelCommand(String path)
    {
        this.pathToFile =path;
    }

    @Override
    public void execute() {
        loader=new MySokobanLoader(pathToFile);
        loader.load();
        setLvl(loader.getLvl());
    }

    public Level getLvl() {
        return lvl;
    }

    public void setLvl(Level lvl) {
        this.lvl = lvl;
    }
    public void setGoals()
    {
        if(getLvl()==null) return;
        Goals=new ArrayList<Point>();
        for (int i = 0; i < getLvl().getBoard().size(); i++) {
            for (int j = 0; j < getLvl().getBoard().get(i).size(); j++) {
                if (getLvl().getBoard().get(i).get(j).equals('o')){
                    //System.out.println("Found goal! its on: ("+j+","+i+")");
                    Goals.add(new Point(j, i));
                }
            }
        }
    }
    public ArrayList<Point> getGoals() {return Goals;}
}
