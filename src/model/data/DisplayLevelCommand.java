package model.data;

import model.Model;
import view.View;

import java.util.LinkedList;

public class DisplayLevelCommand extends Command {
    private Model model;
    private View view;

    public DisplayLevelCommand(Model model, View view) {
        this.model = model;
        this.view = view;
    }


    @Override
    public void execute() {
        Level level= model.getLvl();
        view.display(level);
    }

}
