package model;

import model.data.Level;

public interface Model {
    void load(String input);
    void save(String input);
    void move(String input);
    void exit();
    Level getLevel();
    void setLevel(Level l);
}
