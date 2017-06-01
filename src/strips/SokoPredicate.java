package strips;

public class SokoPredicate extends Predicate{


    public SokoPredicate(String type, String id, String value) {
        super(type, id, value);

    }

    public int getX()
    {
        String[] arr=value.split(",");

        return Integer.parseInt(arr[0]);
    }
    public int getY()
    {
        String[] arr=value.split(",");

        return Integer.parseInt(arr[1]);
    }

    @Override
    public boolean contradicts(Predicate p)
    {
        return (super.contradicts(p) || (!id.equals(p.id)) && value.equals(p.value));
    }
}
