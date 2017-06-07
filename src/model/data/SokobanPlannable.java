package model.data;

import model.policy.MySokobanPolicy;
import searchLib.data.BFS;
import searchLib.data.SearchAction;
import searchLib.data.Solution;
import strips.*;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class SokobanPlannable implements Plannable {

    private Level level;
    private Clause kb;
    private Clause goals;
    private int boxCount=0;
    private int goalCount=0;
    private MySokobanPolicy mySokobanPolicy;
    private MySokobanDisplay display;
    private Solution finale=new Solution();
    private Point initialSoko;



    public SokobanPlannable(Level lvl)
    {
        this.level=lvl;
        mySokobanPolicy=new MySokobanPolicy(this.level);
        display=new MySokobanDisplay(this.level);
        //saver=new MySokobanSaver(this.level,"C:\\Users\\G-lad\\IdeaProjects\\milestone2\\Extras\\levelplannable.txt");
        kb=new Clause(null);

        if(!level.getBoard().isEmpty()) {
            System.out.println("loaded level!");
            for (int i = 0; i < level.getBoard().size(); i++) {
                for (int j = 0; j < level.getBoard().get(i).size(); j++)
                    switch (level.getBoard().get(i).get(j)) {
                        case '#':
                            kb.add(new SokoPredicate("wallAt", "", j + "," + i));
                            break;
                        case ' ':
                            kb.add(new SokoPredicate("clearAt", "", j + "," + i));
                            break;
                        case 'A': {
                            kb.add(new SokoPredicate("sokobanAt", "", j + "," + i));
                            initialSoko=new Point(j,i);
                            break;
                        }
                        case '@':
                            boxCount++;
                            kb.add(new SokoPredicate("boxAt", "b" + boxCount, j + "," + i));
                            break;
                        case 'o':
                            goalCount++;
                            kb.add(new SokoPredicate("goalAt", "t" + goalCount, j + "," + i));
                            break;
                    }
            }
        }
        goals=getGoal();



    }

    @Override
    public Clause getGoal() {
        Clause goal=new Clause(null);
        Clause kb=getKnowledgeBase();
        for (Predicate p :getKnowledgeBase().getPredicates()) {
            if(p.getType().startsWith("goal")){
                goal.add(new SokoPredicate("boxAt", "?", p.getValue()));
            }
        }
        return goal;

//        for (Point p:level.getGoals()) {
//            goals.add(new SokoPredicate("BoxAt","?",p.getX()+","+p.getY()));
//        }
//        return goals;
    }

    @Override
    public Clause getKnowledgeBase() {
        return kb;
    }

    @Override
    public List<Action> getSatisfyingActions(Predicate top) {
        Predicate a=top;
        ArrayList<Point> boxes=new ArrayList<>();
        for (int i = 0; i < level.getBoard().size(); i++) {
            for (int j = 0; j < level.getBoard().get(i).size(); j++) {

                if (level.getBoard().get(i).get(j).equals('@') && (!level.getGoals().contains(new Point(j, i))))
                    boxes.add(new Point(j, i));
            }
        }
        Solution sokoSolution=new Solution();

        for (Point box:boxes) {
            System.out.println("Searching for GOAL on X:"+a.getX()+" Y:"+a.getY());
            Solution boxPath=Path("boxAt",box,new Point(a.getX(),a.getY()));
            System.out.println("WE HAVE A SOLUTION IN HAND ~~~~~~~~~~~~~~~~~~~~~~");
           if(boxPath!=null) {
               Point finalbox=box;
               Point sokoPos=getSoko();
               for (Object searchAction:boxPath.getActions()) {
                   SearchAction action=(SearchAction)searchAction;
                   Point boxPush = null;
                   System.out.println("box action is :"+action.toString());
                   switch (action.toString()) {
                       case ("right"): {
                           boxPush = new Point(finalbox.getX() - 1, finalbox.getY());
                           finalbox.setX(finalbox.getX()+1);
                           break;
                       }
                       case ("left"): {
                           boxPush = new Point(finalbox.getX() + 1, finalbox.getY());
                           finalbox.setX(finalbox.getX()-1);
                           break;
                       }
                       case ("up"): {
                           boxPush = new Point(finalbox.getX(), finalbox.getY() + 1);
                           finalbox.setY(finalbox.getY()-1);
                           break;
                       }
                       case ("down"): {
                           boxPush = new Point(finalbox.getX(), finalbox.getY() - 1);
                           finalbox.setY(finalbox.getY()+1);
                           break;
                       }
                   }
                   Solution sokoPath = Path("sokobanAt", getSoko(), boxPush);
                   if (sokoPath.getActions()!=null) {
                       System.out.println("TO GET TO:"+boxPush.toString()+" WE DO:"+sokoPath.getActions().toString());
                       ArrayList<SearchAction> lvlupdate=new ArrayList<>();
                       lvlupdate.addAll(sokoPath.getActions());
                       lvlupdate.add(action);
                       updateLevel(lvlupdate);
                       sokoSolution.addToActions(sokoPath.getActions());
                       sokoSolution.addToActions(action);
                       System.out.println("SOLUTION SO FAR:"+sokoSolution.getActions().toString());
                       System.out.println("BOX IS NOW ON");
                       System.out.println("SOKOBAN IS NOW HERE:"+getSoko().toString()+"<<<------------------------------------------");
                       finale.addToActions(sokoSolution.getActions());
                       sokoSolution.getActions().clear();
                    }
                    else
                   {
                       System.out.println("NULL ACTIONS FOR MY SOKOBANIA");
                       if(getSoko().equals(boxPush))
                       {
                           finale.addToActions(action);
                           updateLevel(action);
                       }
                       else
                       {
                           System.out.println("didnt find path for soko to box");
                           break;
                       }
                   }
               }
            break;
           }
           else System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Didnt find solution for box:" + box.toString()+"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        }



        if(!finale.getActions().isEmpty()) {
            List<SearchAction> sokoActions=finale.getActions();
            System.out.println("~~~~~~~~~~~~OUR FINAL ACTIONS ~~~~~~~~~~~~");
            System.out.println(sokoActions.toString());
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            List<Action> actions=new ArrayList<>();

            Point sokobanPos=initialSoko;
            for (SearchAction sAction:sokoActions) { //soko path


                Action predicate=new Action();
                predicate.setAct(sAction.getAct());
                System.out.println("Building a Clause....ACTION:"+predicate.getAct());
                switch(sAction.getAct())
                {
                    case("right"):
                    {
                        predicate=newAction(sokobanPos,"right");
                        break;
                    }
                    case("left"):
                    {
                        predicate=newAction(sokobanPos,"left");
                        break;
                    }
                    case("up"):
                    {
                        predicate=newAction(sokobanPos,"up");
                        break;
                    }
                    case("down"):
                    {
                        predicate=newAction(sokobanPos,"down");
                        break;
                    }
                }
                actions.add(predicate);

            }
            Action goalcomplete=new Action();
            //goalcomplete.setPreconditions(new Clause(new SokoPredicate("clearAt","",top.getValue())));
            goalcomplete.setEffects(new Clause(new SokoPredicate("boxAt","?",top.getValue())));
            actions.add(goalcomplete);
            return actions;
        }

        return null;
    }

    private Solution Path(String type,Point initial, Point target)
    {
        SokobanSearchable<Point> Search = null;
        Solution path=new Solution();
        System.out.println("TYPE OF SEARCH IS:"+type);
        System.out.println("GOAL IS:"+target.toString());
        if (type.equals("boxAt")){
            System.out.println("Searching for BOX PATH"); Search = new SokobanSearchable<>(level, "box", initial, target);}
        if (type.equals("sokobanAt")) {
            System.out.println("SEARCHING FOR SOKO MY LOVELY HERO~~~~~~~~~~~~~~~~");
            System.out.println("INITIAL SOKO POS:" + initial.toString() + " TARGET BOX POS:" + target.toString());
            Search = new SokobanSearchable<>(level, "soko", initial, target);
        }

        BFS<Point> searcher = new BFS<>();
        if (Search != null) {
            path = searcher.search(Search);
        }
        //System.out.println("DEBUGGGGG"+path.toString());
        //if(!path.getActions().isEmpty()) return path;
//        if(type.equals("sokobanAt"))
//        {
//            mySokobanPolicy=new MySokobanPolicy(level);
//            display=new MySokobanDisplay(mySokobanPolicy.getLvl());
//            display.display();
//            for (Object act:path.getActions()) {
//                SearchAction action=(SearchAction)act;
//                System.out.println("MOVING "+action.getAct());
//                mySokobanPolicy.Move(action.getAct());
//                display.display();
//            }
//            this.level=mySokobanPolicy.getLvl();
//
//
//            }

        return path;
    }

    @Override
    public Action getSatisfyingAction(Predicate top) {
        Action act=new Action();
        act.setEffects(new Clause(top));
        return act;
    }

    public Action newAction(Point soko,String move)
    {
        Action action=new Action();
        switch(move)
        {
            case("right"):
            {
                //action.getPreconditions().add();
                action.setPreconditions(new Clause(new SokoPredicate("clearAt","",(soko.getX()+1)+","+soko.getY())));
                    action.setEffects(new Clause(new SokoPredicate("clearAt","",(soko.getX())+","+soko.getY()),new SokoPredicate("sokobanAt","",(soko.getX()+1)+","+soko.getY())));
                    soko.setX(soko.getX()+1);
                    action.setAct(move);
                    break;

            }
            case("left"):
            {
                action.setPreconditions(new Clause(new SokoPredicate("clearAt","",(soko.getX()-1)+","+soko.getY())));
                action.setEffects(new Clause(new SokoPredicate("clearAt","",(soko.getX())+","+soko.getY()),new SokoPredicate("sokobanAt","",(soko.getX()-1)+","+soko.getY())));
                    soko.setX(soko.getX()-1);
                action.setAct(move);
                break;

            }
            case("up"):
            {
                action.setPreconditions(new Clause(new SokoPredicate("clearAt","",(soko.getX())+","+(soko.getY()-1))));    //clear(target)
                    action.setEffects(new Clause(new SokoPredicate("clearAt","",(soko.getX())+","+soko.getY()),new SokoPredicate("sokobanAt","",soko.getX()+","+(soko.getY()-1))));
                    soko.setY(soko.getY()-1);
                action.setAct(move);
                break;

            }
            case("down"):
            {
                action.setPreconditions(new Clause(new SokoPredicate("clearAt","",(soko.getX())+","+(soko.getY()+1))));    //clear(target)
                    action.setEffects(new Clause(new SokoPredicate("clearAt","",(soko.getX())+","+soko.getY()),new SokoPredicate("sokobanAt","",soko.getX()+","+(soko.getY()-1))));
                    soko.setY(soko.getY()+1);
                action.setAct(move);
                break;

            }
        }
        return action;
    }

    public ArrayList<ArrayList<Character>> getBoard()
    {
        return this.level.getBoard();
    }


    public int getBoxCount() {
        return boxCount;
    }

    public void setBoxCount(int boxCount) {
        this.boxCount = boxCount;
    }

    public int getGoalCount() {
        return goalCount;
    }

    public void setGoalCount(int goalCount) {
        this.goalCount = goalCount;
    }

    private Point getSoko() {
        for (int i = 0; i < level.getBoard().size(); i++) {
            for (int j = 0; j < level.getBoard().get(i).size(); j++) {

                if (level.getBoard().get(i).get(j).equals('A'))
                    return new Point(j, i);
            }
        }
        return null;
    }

    public void updateLevel(List<SearchAction> actions)
    {
        System.out.println("UPDATING INTERNAL LEVEL:");
        for (SearchAction act:actions) {

            mySokobanPolicy.Move(act.getAct());
            mySokobanPolicy.setPlayer();
        }
        this.level=mySokobanPolicy.getLvl();
        display.display();
    }
    public void updateLevel(SearchAction action)
    {
        System.out.println("UPDATING INTERNAL LEVEL:");
            mySokobanPolicy.Move(action.getAct());
            mySokobanPolicy.setPlayer();
        this.level=mySokobanPolicy.getLvl();
        display.display();
    }
    public Level getLevel() {
        return level;
    }

}
