package model;

import commands.LoadLevelCommand;
import levels.Level;

import java.util.ArrayList;

public interface Model {
    void load(String input);
    void save(String input);
    void move(String input);
    void exit();
    Level getlvl();
}
