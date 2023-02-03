package com.alexisdrai;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Stage extends Thread {
    private final BlockingQueue<Integer> in;
    private final BlockingQueue<Integer> out;
    private int number;
    private final AtomicBoolean running = new AtomicBoolean(false);

    public Stage(BlockingQueue<Integer> in, BlockingQueue<Integer> out) {
        this.in = in;
        this.out = out;
    }

    public void cleanStop() {
        running.set(false);
    }

    @Override
    public void run() {
        running.set(true);
        while (running.get()) {
            try {

                number = in.take();

            } catch (InterruptedException e) {
                Logger.getLogger(Stage.class.getName()).log(Level.SEVERE, null, e);
            }

            number++;

            try {

                out.put(number);

            } catch (InterruptedException e) {
                Logger.getLogger(Stage.class.getName()).log(Level.SEVERE, null, e);
            }
            synchronized (out) {
                out.notifyAll();
            }
        }
    }
}
