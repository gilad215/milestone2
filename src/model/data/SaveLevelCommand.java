package model.data;

import java.util.LinkedList;

public class SaveLevelCommand implements Command {
    private Level lvl;
    private String p;
    private Saver saver;

    public SaveLevelCommand(){}
    public SaveLevelCommand(Level l, String path)
    {
        setP(path);
        setLvl(l);
    }

    @Override
    public void execute() {
        saver=new MySokobanSaver(getLvl(),getP());
        saver.save();
    }

    @Override
    public void setParams(LinkedList<String> linkedList) {

    }

    public Level getLvl() {
        return lvl;
    }

    public void setLvl(Level l) {
        this.lvl = l;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }
}
