package model;

import model.data.Level;
import model.data.Point;

import java.util.ArrayList;
import java.util.Observable;

public interface Model {
    void load(String input);
    void save(String input);
    void move(String direction);
    public Level getLvl();

}
