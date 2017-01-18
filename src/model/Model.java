package model;

import model.data.Level;

import java.util.Observable;

public abstract class Model extends Observable {
    abstract public void load(String input);
    abstract public void save(String input);
    abstract public void move(String input);
    abstract public void exit();
    abstract public Level getLevel();
    abstract public void setLevel(Level l);
}
