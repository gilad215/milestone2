package controller;

import model.data.Command;

import java.util.Observer;

public interface Controller extends Observer {
    public void insertCommand(Command c);
    void start();
    void stop();
}
