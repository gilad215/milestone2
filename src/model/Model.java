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
    void showLeaderboard() throws FileNotFoundException;
    void addUser(String fn,String ln,int steps,int time);
    public Level getLvl();


}
