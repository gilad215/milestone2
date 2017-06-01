package strips;

public class Predicate {
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

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
