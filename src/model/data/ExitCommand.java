package model.data;

import java.util.LinkedList;

public class ExitCommand implements Command{

    @Override
    public Level getLvl() {
        return null;
    }

    @Override
    public void run() {
        System.exit(0);
    }

    @Override
    public void setParams(LinkedList<String> linkedList) {

    }

    @Override
    public void setLvl(Level l) {

    }
}
