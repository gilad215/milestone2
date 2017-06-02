package searchLib.data;

public class SearchAction {
    private String act;
    public SearchAction(String a){this.act=a; }
    public String getAct(){return act;}
    public void setAct(String a){this.act=a;}

    @Override
    public boolean equals(Object obj)
    {
        SearchAction a=(SearchAction)obj;
        return a.act.equals(act);
    }
    @Override
    public int hashCode()
    {
        return act.hashCode();
    }

    public String toString()
    {
        return act;
    }

}
