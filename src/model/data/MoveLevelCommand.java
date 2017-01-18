package model.data;

import model.Model;
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
        Goals=new ArrayList<Point>();

    }
    public MoveLevelCommand(Model m)
    {
        lvl=m.getLevel();
        Goals=new ArrayList<Point>();
        //Goals=lvl.getGoals();
    }

    public void execute() {
        Goals=lvl.getGoals();
        if(Goals==null) System.out.println("GOAL IS NULL");
        policy=new MySokobanPolicy(lvl,Goals);
        policy.Move(direction);
        //if(policy.isFinished()) System.out.println("Level Finished! :)");


    }

    @Override
    public void setParams(LinkedList<String> linkedList) {
        direction=linkedList.getFirst();
        System.out.println("DIRECTION OF MOVMENT IS "+direction);
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
        Goals=l.getGoals();
    }

    public boolean isFinished(){return policy.isFinished();}
}
