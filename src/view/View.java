package view;

import model.data.Command;
import model.data.Level;

public interface View{
    void displayMessage(Command c);
    void display(Level l);
    void setMoveMade(String s);
    void Finished();
    void start();
}
