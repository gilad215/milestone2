package model.data;

import model.policy.MySokobanPolicy;
import model.policy.Policy;

import java.util.ArrayList;
import java.util.LinkedList;

public class MoveLevelCommand implements Command {


    private Level lvl;
    private ArrayList<Point> Goals;
    private Policy policy;
    private String direction;

    public MoveLevelCommand(){
        lvl=new Level();
        Goals=new ArrayList<Point>();}
    public MoveLevelCommand(Level l, String dir)
    {
        lvl=new Level();
        Goals=new ArrayList<Point>();
        this.lvl=l;
        this.Goals=l.getGoals();
        this.direction=dir;
    }

    public void run() {
        policy=new MySokobanPolicy(lvl,Goals);
        policy.Move(direction);
        if(policy.isFinished()) System.out.println("Level Finished! :)");


    }

    @Override
    public void setParams(LinkedList<String> linkedList) {
        direction=linkedList.getFirst();
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

    public void setLvl(Level l) {
        this.lvl = l;
    }

    public boolean isFinished(){return policy.isFinished();}
}
