package controller;

import model.Model;
import view.View;

import java.util.Observable;
import java.util.Observer;

public class MyController implements Controller {
    private view.View ui;
    private Model model;

    public MyController(View v, Model m) {
        ui = v;
        model = m;
    }

    public void update(Observable o, Object arg) {
        if (o == ui) {
            String[] input = ui.getInput();
            switch (input[0].toUpperCase()) //making the first word upper case
            {
                case ("LOAD"): {
                    if (input.length == 1) {
                        System.out.println("Invalid File Path.");
                        break;
                    }
                    model.load(input[1]);
                    break;
                }
                case ("SAVE"): {
                    if (input.length == 1) {
                        System.out.println("Invalid File Path.");
                        break;
                    }
                    model.save(input[1]);
                    break;
                }
                case ("MOVE"): {
                    model.move(input[1]);
                    break;
                }
                case ("EXIT"): {
                    model.exit();
                }

            }
        }
        if (o == model) {
            ui.displayData(model.getlvl());
        }
        ui.userInput();
    }
}
