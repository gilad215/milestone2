package model.data;

import model.data.Level;

public interface Loader {
     void load();
     Level getLvl();
     void setLvl(Level l);
}
