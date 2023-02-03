package com.alexisdrai;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Listener extends Thread {
    private final BlockingQueue<Integer> out;

    public Listener(BlockingQueue<Integer> out) {
        this.out = out;
    }

    @Override
    public  void run() {
        while (!(out.isEmpty())) {
            System.out.println("resultat " + out.peek());
//            try {
//                wait(500);
//            } catch (InterruptedException e) {
//                Logger.getLogger(Listener.class.getName()).log(Level.SEVERE, null, e);
//            }
        }
    }
}
