package model.data;

import model.Model;

public class AddUserCommand extends Command {
    private Model model;

    public AddUserCommand(Model m) {this.model=m;}

    @Override
    public void execute() {
        model.addUser(params.get(0),params.get(1),Integer.valueOf(params.get(2)),Integer.valueOf(params.get(3)));

    }
}
