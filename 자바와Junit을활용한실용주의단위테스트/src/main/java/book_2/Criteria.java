package book_2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Criteria implements Iterable<Criterion> {

    private List<Criterion> criteria = new ArrayList<>();

    public void add(Criterion criterion) {
        criteria.add(criterion);
    }

    public int arithmeticMean() {
        return 0;
    }

    public int size() {
        return criteria.size();
    }

    @Override
    public Iterator<Criterion> iterator() {
        return criteria.iterator();
    }

    @Override
    public void forEach(final Consumer<? super Criterion> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<Criterion> spliterator() {
        return Iterable.super.spliterator();
    }
}
