package model;

import model.data.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

public class MyModel extends Model {
    private Level level;

    @Override
    public void load(String input) {
        LoadLevelCommand load=new LoadLevelCommand(input);
        load.execute();
        this.setLevel(load.getLvl());
        this.setChanged();
        //this.notifyObservers();

    }

    @Override
    public void save(String input) {
        SaveLevelCommand save=new SaveLevelCommand(getLevel(),input);
        save.execute();
    }


    @Override
    public void move(String input) {
        MoveLevelCommand move=new MoveLevelCommand();
        move.execute();
        setLevel(move.getLvl());
        this.setChanged();
        List<String> params = new LinkedList<String>();
        params.add("DISPLAY");
        this.notifyObservers(params);

    }

    @Override
    public void exit() {
        ExitCommand e=new ExitCommand();
        e.execute();

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
