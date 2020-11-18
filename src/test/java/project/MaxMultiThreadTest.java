package project;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class MaxMultiThreadTest {

    private List<Integer> collection;
    private final Random random = new Random();


    @BeforeEach
    public void init() {
        collection = new ArrayList<>();
    }

    @Test
    public void findMaxTest() throws ExecutionException, InterruptedException {
        int max = random.nextInt();
        collection.add(max);
        for (int i = 0; i < 9_999_999; ++i) {
            int nextInt = random.nextInt();
            collection.add(nextInt);
            if (nextInt > max) {
                max = nextInt;
            }
        }
        MaxMultiThread<Integer> maxMultiThread = new MaxMultiThread<>(12);
        Assertions.assertEquals(max, maxMultiThread.max(collection));
    }

    @Test
    public void smallCollectionTest() throws ExecutionException, InterruptedException {
        collection.add(random.nextInt());
        collection.add(random.nextInt());
        MaxMultiThread maxMultiThread = new MaxMultiThread(12);
        Assertions.assertEquals(Collections.max(collection),
                maxMultiThread.max(collection));
    }
}
