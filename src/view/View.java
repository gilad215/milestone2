package view;

import model.data.Level;

public interface View {
    void displayData(Level lvl);
    String[] getInput();
    public void userInput();
}
