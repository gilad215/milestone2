package model.data;

import java.util.LinkedList;

public interface Command extends Runnable{
    Level getLvl();
    void run();
    void setParams(LinkedList<String> linkedList);
    void setLvl(Level l);
}
