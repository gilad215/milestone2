package model.data;

import model.Model;

import java.util.ArrayList;
import java.util.LinkedList;

public class LoadLevelCommand extends Command {
    private Model model;

    public LoadLevelCommand(Model model) {
        this.model = model;
    }

    @Override
    public void execute() {
        model.load(params.get(0));
    }
}


