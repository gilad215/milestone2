package model.data;

import java.util.ArrayList;
import java.util.LinkedList;

public class LoadLevelCommand implements Command {
    private Level lvl;
    private String pathToFile;
    private Loader loader;

    public LoadLevelCommand(){}
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

    @Override
    public void setParams(LinkedList<String> linkedList) {
        pathToFile=linkedList.getFirst();
    }

    public Level getLvl() {
        return lvl;
    }

    public void setLvl(Level l) {
        this.lvl = l;
    }

}
