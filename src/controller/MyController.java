package controller;

import model.Model;
import model.data.*;
import view.View;

import java.util.*;
import java.util.concurrent.*;

public class MyController implements Observer {
    private View ui;
    private Model model;
    private Controller controller;
    private HashMap<String,Command> commands;

    public MyController(View v, Model m) {
        ui = v;
        model = m;

        initCommands();
        controller=new Controller();
        controller.start();
    }

    protected void initCommands() {
        commands = new HashMap<String, Command>();
        commands.put("MOVE", new MoveLevelCommand(model));
        commands.put("DISPLAY", new DisplayLevelCommand(model));
        commands.put("LOAD", new LoadLevelCommand());
        commands.put("SAVE",new SaveLevelCommand());


    }

    @Override
    public void update(Observable o, Object arg) {
        LinkedList<String> params = (LinkedList<String>) arg;
        String commandKey = params.removeFirst();
        Command c = commands.get(commandKey.toUpperCase());
        if (c == null) {
            System.out.println("Invalid Command");
            return;
        }
        c.setParams(params);
        controller.insertCommand(c);
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
    }
                            queue.add(new LoadLevelCommand());
                        LinkedList<String> linkedList= new LinkedList<>();
                        LinkedList<String> mlinkedList= new LinkedList<>();
                        LinkedList<String> leftlinkedList= new LinkedList<>();


                        linkedList.add("./extras/level2.txt");
                        mlinkedList.add("right");
                        leftlinkedList.add("left");
                        System.out.println(queue.size());
                        Command c=queue.take();
                        c.setParams(linkedList);
                        c.run();
                        model.setLevel(c.getLvl());
                        if(!model.getLevel().getBoard().isEmpty()) System.out.println("board isnt empty");
                        Displayer d = new MySokobanDisplay(model.getLevel());
                        d.display();
                        System.out.println("Load finished, Queue elements: "+queue.size());
                        queue.add(new MoveLevelCommand());
                        System.out.println("Added Move Command, Queue elements: "+queue.size());
                        c=queue.take();
                        c.setParams(mlinkedList);
                        c.setLvl(model.getLevel());
                        c.run();
                        model.setLevel(c.getLvl());
                        d.display();
                        queue.add(new MoveLevelCommand());
                        c=queue.take();
                        c.setParams(leftlinkedList);
                        c.setLvl(model.getLevel());
                        c.run();
                        model.setLevel(c.getLvl());
                        d.display();

    */
}
