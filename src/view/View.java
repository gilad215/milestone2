package view;

import model.data.Command;
import model.data.Level;
import model.data.Point;

import java.util.Observable;

public interface View{
    void DisplayPosition(Point p);
    void displayError(Command c);
    void start();
}
