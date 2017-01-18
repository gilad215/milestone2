package model;

import model.data.*;

import java.util.ArrayList;
import java.util.Observable;

public class MyModel extends Model {
    private Level level;

    @Override
    public void load(String input) {
        LoadLevelCommand load=new LoadLevelCommand(input);
        load.run();
        this.setLevel(load.getLvl());

    }

    @Override
    public void save(String input) {
        SaveLevelCommand save=new SaveLevelCommand(getLevel(),input);
        save.run();
    }


    @Override
    public void move(String input) {
        MoveLevelCommand move=new MoveLevelCommand(getLevel(),input);
        move.run();
        setLevel(move.getLvl());
    }

    @Override
    public void exit() {
        ExitCommand e=new ExitCommand();
        e.run();

    }

    @Override
    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public void notifyObservers() {
        super.notifyObservers();
    }
}
