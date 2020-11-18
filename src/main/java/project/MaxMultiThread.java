package project;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

public class MaxMultiThread<T extends Comparable<T>> {

    /**
     * threads number
     */
    private final int threadNumber;

    /**
     * thread pool
     */
    private final ExecutorService executorService;

    public MaxMultiThread(int threadNumber) {
        this.threadNumber = threadNumber;
        this.executorService = Executors.newFixedThreadPool(threadNumber);
    }

    /**
     * finds max element of the collection
     * @param collection collection
     * @return max element
     * @throws ExecutionException ...
     * @throws InterruptedException ...
     */
    public T max(List<T> collection) throws ExecutionException, InterruptedException {

        if (collection.size() <= threadNumber) {
            return Collections.max(collection);
        }

        List<Future<T>> futures = new ArrayList<>(threadNumber);
        List<List<T>> collections = split(collection, threadNumber);

        for (List<T> temp : collections) {
            Callable<T> max = new Max<>(temp);
            futures.add(executorService.submit(max));
        }
        List<T> maxElements = new ArrayList<>();
        for (Future<T> future : futures) {
            maxElements.add(future.get());
        }
        executorService.shutdown();
        return Collections.max(maxElements);
    }

    /**
     * returns splitted collections
     * @param collection collection
     * @param threadNumber thread number
     * @return collections after split
     */
    private List<List<T>> split(List<T> collection, int threadNumber) {
        int index = 0;
        int size = collection.size() / threadNumber;

        List<List<T>> collections = new ArrayList<>();
        while (index < collection.size()) {
            int nextIndex = Math.min(collection.size() - index, size);
            collections.add(collection.subList(index, index + nextIndex));
            index += nextIndex;
        }
        return collections;
    }
}
