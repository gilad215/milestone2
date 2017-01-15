package view;

import levels.Level;

public interface View {
    void displayData(Level lvl);
    String[] getInput();
    public void userInput();
}
