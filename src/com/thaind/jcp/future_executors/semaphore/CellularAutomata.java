package com.thaind.jcp.future_executors.semaphore;

import java.util.concurrent.CyclicBarrier;

public class CellularAutomata {
    private final Board mainBoard;
    private final CyclicBarrier barrier;
    private final Worker[] workers;

    public CellularAutomata(Board board) {
        this.mainBoard = board;
        int count = Runtime.getRuntime().availableProcessors();
        this.barrier = new CyclicBarrier(count, new Runnable() {
            @Override
            public void run() {
                mainBoard.commitNewValue();
            }
        });
        this.workers = new Worker[count];
        for (int index = -1; ++index < count;) {
            workers[index] = new Worker(mainBoard.getSubBoard(count, index));
        }
    }

    private class Worker implements Runnable {
        private final Board board;

        public Worker(Board board) {
            this.board = board;
        }

        @Override
        public void run() {
            while (!board.hasConverged()) {
                for (int x = 0; x < board.getMaxX(); x++) {
                    for (int y = 0; y < board.getMaxY(); y++) {
                        board.setNewValue(x, y, computeValue(x, y));
                    }
                }
                try {
                    barrier.await();
                } catch (Exception ex) {
                    System.err.println("Error when processing barrier, trace: " + ex);
                    ex.printStackTrace();
                    return;
                }
            }

        }
    }

    private int computeValue(int x, int y) {
        // Compute the new value that goes in (x,y)
        return 0;
    }
}

interface Board {
    int getMaxX();

    int getMaxY();

    int getValue(int x, int y);

    int setNewValue(int x, int y, int value);

    void commitNewValue();

    boolean hasConverged();

    void waitForConvergence();

    Board getSubBoard(int numPartitions, int index);
}
