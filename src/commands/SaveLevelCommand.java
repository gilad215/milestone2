package commands;

import util.OBJUtil;
import util.LevelSaver;
import util.TXTUtil;
import util.XMLUtil;
import levels.Level;

import java.io.IOException;
import java.util.HashMap;

public class SaveLevelCommand implements Command {
    private Level lvl;
    private String p;
    private Saver saver;

    SaveLevelCommand(Level l, String path)
    {
        setP(path);
        setLvl(l);
    }

    @Override
    public void execute() {
        saver=new MySokobanSaver(getLvl(),getP());
        saver.save();
    }

    public Level getLvl() {
        return lvl;
    }

    public void setLvl(Level lvl) {
        this.lvl = lvl;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }
}
