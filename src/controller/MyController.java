package controller;

import model.Model;
import model.data.*;
import view.View;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

public class MyController implements Controller {
    private view.View ui;
    private Model model;
    private HashMap<String,Command> commands;
    private BlockingQueue<Command> queue;
    String args;

    public MyController(View v, Model m) {
        ui = v;
        model = m;
        commands=new HashMap<String,Command>();
        commands.put("LOAD",new LoadLevelCommand());
        commands.put("SAVE",new SaveLevelCommand());
        commands.put("MOVE",new MoveLevelCommand());
        commands.put("EXIT",new ExitCommand());
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o == model) {
            ui.displayData(model.getLevel());
        }
        if (o == ui) {
            LinkedList<String> params = (LinkedList<String>) arg;
            String commandKey = params.removeFirst();
            Command c = commands.get(commandKey);
            c.setParams(params);
            c.setLvl(model.getLevel());
            insertCommand(c);
        }
    }
    @Override
    public void insertCommand(Command c) {
        queue.add(c);
    }

    @Override
    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true)
                {
                    try {
                        queue.add(new LoadLevelCommand());
                        Command c=queue.take();
                        c.run();
                        model.setLevel(c.getLvl());
                        Displayer d = new MySokobanDisplay(model.getLevel());
                        d.display();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }

    @Override
    public void stop() {

    }


    /*public void update(Observable o, Object arg) {
        if (o == ui) {
            String[] input = (String[])arg;
            switch (input[0].toUpperCase()) //making the first word upper case
            {
                case ("LOAD"): {
                    if (input.length == 1) {
                        System.out.println("Invalid File Path.");
                        break;
                    }
                    queue.add(new LoadLevelCommand(input[1]));
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
    }*/
}
