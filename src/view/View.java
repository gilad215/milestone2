package view;

import model.data.Command;
import model.data.Level;
import model.data.Point;

import java.util.Observable;

public interface View{
    void displayMessage(Command c);
    void display(Level l);
    void start();
}
