package model.data;

import java.util.LinkedList;

public interface Command{
    Level getLvl();
    void execute();
    void setParams(LinkedList<String> linkedList);
    void setLvl(Level l);
}
