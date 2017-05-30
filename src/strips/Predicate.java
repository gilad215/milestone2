package strips;

public class Predicate {
    String type, id, value;

    public Predicate(String type, String id, String value) {
        super();
        this.type = type;
        this.id = id;
        this.value = value;
    }

    public boolean satisfies(Predicate p) {
        return (type.equals(p.type) && (id.equals(p.id) || p.id.equals("?")) && value.equals(p.value));
    }

    public boolean contradicts(Predicate p)
    {
        return (type.equals(p.type) && id.equals(p.id) && !value.equals(p.value));
    }


}
