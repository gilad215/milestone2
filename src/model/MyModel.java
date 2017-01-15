package model;

import commands.*;
import levels.Level;
import levels.Point;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MyModel extends Observable implements Model {
    private Level level;
    ArrayList<Point> Goals=null;

    @Override
    public void load(String input) {
        LoadLevelCommand load=new LoadLevelCommand(input);
        load.execute();
        this.setLevel(load.getLvl());
        Goals=load.getGoals();

    }

    @Override
    public void save(String input) {
        SaveLevelCommand save=new SaveLevelCommand(getLevel(),input);
        save.execute();
    }


    @Override
    public void move(String input) {
        MoveLevelCommand move=new MoveLevelCommand(this.getLevel(),input,Goals);
        move.execute();
        setLevel(move.getLvl());
    }

    @Override
    public void exit() {
        ExitCommand e=new ExitCommand();
        e.execute();

    }

    @Override
    public Level getlvl() {
        return null;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}
