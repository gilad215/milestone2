package controller;

import model.Model;
import model.data.Command;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.Observer;

public class Controller  {
    //private Model m;
    private BlockingQueue<Command> queue;
    private boolean isStopped = false;

    public Controller() {
        queue = new ArrayBlockingQueue<Command>(10);
    }

    public void insertCommand(Command command) {
        try {
            queue.put(command);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (!isStopped) {
                    try {
                        Command cmd = queue.poll(1, TimeUnit.SECONDS);
                        if (cmd != null)

                            cmd.execute();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    public void stop() {
        isStopped = true;
    }

}
