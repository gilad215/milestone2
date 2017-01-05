package commands;
import levels.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class LoadLevelCommand implements Command {
    private Level lvl;
    private String pathToFile;
    private Loader loader;


    LoadLevelCommand(String path)
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
}
