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
        if(!params.isEmpty()) {
            if(params.size()>1) {
                String moveMade = params.get(0);
                view.setMoveMade(moveMade);
                view.Finished();
            }
            String moveMade = params.get(0);
            view.setMoveMade(moveMade);
        }
        Level level= model.getLvl();
        view.display(level);
    }

}
