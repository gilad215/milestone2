package searchLib.data;

public interface Searcher<T> {
    public Solution search(Searchable<T> s);
    public int getNumberOfNodesEvaluated();
}
