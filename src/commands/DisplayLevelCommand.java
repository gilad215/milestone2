package commands;

import levels.Level;

public class DisplayLevelCommand implements Command {
    private Level lvl;
    private Displayer d;

    DisplayLevelCommand(Level l)
    {
        this.lvl=l;
    }
    @Override
    public void execute() {
        {
            d=new MySokobanDisplay(getLvl());
            d.display();
        }
    }

    public Level getLvl() {
        return lvl;
    }

    public void setLvl(Level lvl) {
        this.lvl = lvl;
    }
}
