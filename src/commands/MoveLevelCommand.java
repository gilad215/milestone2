package commands;

import levels.Level;
import levels.Point;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class MoveLevelCommand implements Command {


    private Level lvl;
    private ArrayList<Point> Goals;
    private Policy policy;
    private String direction;

    public MoveLevelCommand(Level l, String dir, ArrayList<Point> goals)
    {
        lvl=new Level();
        Goals=new ArrayList<Point>();
        this.Goals=goals;
        this.lvl=l;
        this.direction=dir;
    }

    public void execute() {
        policy=new MySokobanPolicy(lvl,Goals);
        policy.Move(direction);
        if(policy.isFinished()) System.out.println("Level Finished! :)");


    }

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    public Level getLvl() {
        return lvl;
    }

    public void setLvl(Level lvl) {
        this.lvl = lvl;
    }

    public boolean isFinished(){return policy.isFinished();}
}
