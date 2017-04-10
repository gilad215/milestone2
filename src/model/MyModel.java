package model;

import db.TableUtil;
import db.User;
import javafx.application.Platform;
import model.data.*;
import model.policy.MySokobanPolicy;
import model.policy.Policy;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyModel extends Observable implements Model {
    private Level lvl;
    private Policy policy;
    private Loader loader;
    private Saver saver;
    private TableUtil tableUtil;


    public MyModel() {tableUtil=new TableUtil();}
    public Level getLvl(){return lvl;}

    @Override
    public void load(String input) {

        loader=new MySokobanLoader(input);
        loader.load();
        if(loader.getLvl()==null)
        {
            System.out.println("Level Invalid");
            return;
        }
        lvl=loader.getLvl();
        if(lvl==null) return;
        lvl.setGoals();

        String pattern="level\\d+";
        Pattern p=Pattern.compile(pattern);
        Matcher m=p.matcher(input);
        if(m.find())
        {
            if(tableUtil.getLevelIDs().containsKey(m.group(0).toUpperCase())) tableUtil.setLvlid(tableUtil.getLevelIDs().get(m.group(0).toUpperCase()));
        }

        this.setChanged();
        List<String> params = new LinkedList<String>();
        params.add("Display");
        this.notifyObservers(params);

    }

    @Override
    public void save(String input) {
        saver=new MySokobanSaver(lvl,input);
        if(saver.getLvl()==null)
        {
            System.out.println("Level Invalid");
            return;
        }
        saver.save();
        this.setChanged();
        List<String> params = new LinkedList<String>();
        params.add("Display");
        this.notifyObservers(params);
    }


    @Override
    public void move(String direction) {
        if(lvl==null) {
            System.out.println("No Level Loaded!");
            return;
        }
        policy=new MySokobanPolicy(lvl);
        policy.Move(direction);
        lvl=policy.getLvl();
        this.setChanged();
        LinkedList<String> params = new LinkedList<String>();
        params.add("Display");
        if(policy.moveMade()) params.add("movemade");
        if(policy.isFinished()) params.add("finished");
        this.notifyObservers(params);
    }


    @Override
    public void notifyObservers() {
        super.notifyObservers();
    }

    @Override
    public void showLeaderboard() throws FileNotFoundException {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    tableUtil.showLeaderboard();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void addUser(String fn, String ln, int steps, int time) {
        tableUtil.addUser(new User(tableUtil.getLvlid(),fn,ln,steps,time));
    }
}
