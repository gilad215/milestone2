package model.data;

import model.Model;

import java.util.LinkedList;

public class DisplayLevelCommand implements Command {
    private Level lvl;
    private Displayer d;

    public DisplayLevelCommand(){}
    public DisplayLevelCommand(Model l)
    {
        this.lvl=l.getLevel();
    }

    @Override
    public void setParams(LinkedList<String> linkedList) {

    }

    public Level getLvl() {
        return lvl;
    }

    @Override
    public void execute() {
        d=new MySokobanDisplay(getLvl());
        d.display();
    }

    public void setLvl(Level l) {
        this.lvl = l;
    }
}
