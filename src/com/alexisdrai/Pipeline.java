package com.alexisdrai;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

public class Pipeline {
    public static final int PIPE_CAPACITY = 10;
    public static final int STAGES = 4;

    public static void main(String[] argv) {

        BlockingQueue<Integer> first = null, out = null, in;

        Stage[] threads = new Stage[STAGES];
        for (int i = 0; i < STAGES; i++) {
            if (first == null) {
                first = in = new ArrayBlockingQueue<>(PIPE_CAPACITY);
            }
            else {
                in = out;
            }

            out = new ArrayBlockingQueue<>(PIPE_CAPACITY);
            threads[i] = new Stage(in, out);
            (threads[i]).start();
        }

        Listener listener = new Listener(out);
        (listener).start();

        BlockingQueue<Integer> finalFirst = first;
        IntStream.of(2, 5, 7, 12, 64, 53, 1, 7).forEach(n -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Pipeline.class.getName()).log(Level.SEVERE, null, ex);
            }
            finalFirst.add(n);
        });

        listener.cleanStop();
        for (Stage thread : threads) {
            thread.cleanStop();
        }
    }
}