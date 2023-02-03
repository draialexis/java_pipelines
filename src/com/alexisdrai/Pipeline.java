package com.alexisdrai;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author sasa
 */
public class Pipeline {
    public static final int PIPE_CAPACITY = 10;
    public static final int STAGES_ABOVE_FIRST = 3;

    public static void main(String[] argv) {

        BlockingQueue<Integer> first, in, out = null;
        first = in = new LinkedBlockingQueue<>(PIPE_CAPACITY);
        for (int i = 0; i < STAGES_ABOVE_FIRST; i++) {
            out = new LinkedBlockingQueue<>(PIPE_CAPACITY);
            (new Stage(in, out, -1)).start();
            in = out;
        }

        (new Listener(out)).start();

        System.out.println(first.isEmpty());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Pipeline.class.getName()).log(Level.SEVERE, null, ex);
        }
        first.add(2); // 6

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Pipeline.class.getName()).log(Level.SEVERE, null, ex);
        }
        first.add(6); // 10

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Pipeline.class.getName()).log(Level.SEVERE, null, ex);
        }
        first.add(3); // 7
    }
}