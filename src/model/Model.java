package model;

import model.data.Level;
import model.data.Point;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Observable;

public interface Model {
    void load(String input);
    void save(String input);
    void move(String direction);
    void setLvlID(int lvlid);
    void showLeaderboard() throws FileNotFoundException;
    public Level getLvl();


}
