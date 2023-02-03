package com.alexisdrai;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Stage extends Thread {
    private final BlockingQueue<Integer> in;
    private final BlockingQueue<Integer> out;
    private int number;

    public Stage(BlockingQueue<Integer> in, BlockingQueue<Integer> out, int number) {
        this.in = in;
        this.out = out;
        this.number = number;
    }

    @Override
    public void run() {
        while (true) {
//            while (in.isEmpty()) {
//                try {
//                    wait();
//                } catch (InterruptedException e) {
//                    Logger.getLogger(Stage.class.getName()).log(Level.SEVERE, null, e);
//                }
//            }
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
//            out.notifyAll();
        }
    }
}
