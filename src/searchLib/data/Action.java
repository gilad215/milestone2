package searchLib.data;

public class Action {
    private String act;
    public Action(String a){this.act=a; }
    public String getAct(){return act;}
    public void setAct(String a){this.act=a;}

    @Override
    public boolean equals(Object obj)
    {
        Action a=(Action)obj;
        return a.act.equals(act);
    }
    @Override
    public int hashCode()
    {
        return act.hashCode();
    }

}
