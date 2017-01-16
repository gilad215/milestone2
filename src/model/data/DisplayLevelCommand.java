package model.data;

import java.util.LinkedList;

public class DisplayLevelCommand implements Command {
    private Level lvl;
    private Displayer d;

    DisplayLevelCommand(Level l)
    {
        this.lvl=l;
    }
    @Override
    public void run() {
        {
            d=new MySokobanDisplay(getLvl());
            d.display();
        }
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
}
