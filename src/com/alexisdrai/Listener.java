package com.alexisdrai;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Listener extends Thread {
    private final BlockingQueue<Integer> out;
    private final AtomicBoolean running = new AtomicBoolean(false);

    public Listener(BlockingQueue<Integer> out) {
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
                System.out.println("resultat " + out.take());
            } catch (InterruptedException e) {
                Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
}
