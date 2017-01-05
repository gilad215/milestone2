package commands;

import levels.Level;

public interface Loader {
     void load();
     Level getLvl();
     void setLvl(Level l);
}
