package project;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

public class Max<T extends Comparable<T>> implements Callable<T> {

    private final List<T> collection;

    public Max(List<T> collection) {
        this.collection = collection;
    }

    @Override
    public T call() {
        return collection == null ? null :
                Collections.max(collection);
    }
}
