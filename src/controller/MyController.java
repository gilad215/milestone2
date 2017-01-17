package controller;

import model.Model;
import model.data.*;
import view.View;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MyController implements Controller {
    private view.View ui;
    private Model model;
    private HashMap<String,Command> commands;
    private BlockingQueue<Command> queue;
    String args;

    public MyController(View v, Model m) {
        ui = v;
        model = m;
        queue=new LinkedBlockingQueue<Command>();
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
            System.out.println("COMMAND IS:"+commandKey+" PARAMSNOW:"+params.getFirst());
            Command c = commands.get(commandKey.toUpperCase());
            //System.out.println("PARAMS ARE:"+params);
            c.setParams(params);
            //c.setLvl(model.getLevel());
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
                Displayer d;
                LinkedList<String> linkedList= new LinkedList<>();
                LinkedList<String> rightlinkedlist= new LinkedList<>();
                LinkedList<String> leftlinkedList= new LinkedList<>();
                linkedList.add("load");
                linkedList.add("./extras/level2.txt");
                rightlinkedlist.add("move");
                rightlinkedlist.add("right");
                leftlinkedList.add("move");
                leftlinkedList.add("left");
                update(ui,linkedList);
                update(ui,rightlinkedlist);
                update(ui,leftlinkedList);
                for (Command c:queue) {
                    try {
                        Command command=queue.take();
                        if(command.getClass()==new LoadLevelCommand().getClass())
                        command.run();
                        else
                        {
                            command.setLvl(model.getLevel());
                            command.run();
                        }
                        model.setLevel(command.getLvl());
                        d=new MySokobanDisplay(model.getLevel());
                        System.out.println("DISPLAYING:");
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
