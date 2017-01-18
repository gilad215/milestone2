package model.data;

import java.util.LinkedList;

public class ExitCommand implements Command{

    @Override
    public Level getLvl() {
        return null;
    }

    @Override
    public void execute() {
        System.exit(0);
    }

    @Override
    public void setParams(LinkedList<String> linkedList) {

    }

    @Override
    public void setLvl(Level l) {

    }
}
