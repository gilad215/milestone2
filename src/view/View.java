package view;

import model.data.Level;

import java.util.Observable;

public abstract class View extends Observable {
    abstract public void displayData(Level lvl);
    abstract public String[] getInput();
    abstract public void userInput();
}
