package com.thaind.jcp.enhance_threadsafed_object.queue;

import java.util.concurrent.BlockingQueue;

public class TaskRunnable {
    BlockingQueue<Task> queue;

    // avoid throwing interrupt exception
    public void run() {
        try {
            processTask(queue.take());
        } catch (InterruptedException e) {
            // restore interrupted status
            Thread.currentThread().interrupt();
        }
    }

    private void processTask(Task task) {

    }
}

interface Task {

}
