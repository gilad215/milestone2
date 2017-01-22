package controller.server;

import model.data.Command;
import model.data.Level;
import view.View;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class Server implements View{
    private int port;
    private ClientHandler ch;
    private volatile boolean stop;

    public Server(int port,ClientHandler ch) {
        this.port = port;
        this.ch = ch;
        stop = false;
    }
    @Override
    public void displayMessage(Command c) {

    }

    @Override
    public void display(Level l) {

    }

    public void runServer() {
        try{
        ServerSocket server=new ServerSocket(port);
        server.setSoTimeout(1000);
            while(!stop){

                    Socket aClient=server.accept(); // blocking call
                    new Thread(new Runnable() { //WOW! we should control the number of threads!
                        public void run() {
                            try {
                                ch.handleClient(aClient.getInputStream(), aClient.getOutputStream());
                                aClient.getInputStream().close();
                                aClient.getOutputStream().close();
                                aClient.close();
                            } catch (IOException e) {/*...*/}
                        }
                    }).start();
                }
            server.close(); //WOW! we should wait for all threads before closing!
        }           catch(SocketTimeoutException e) {/*...*/} catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void start(){
        new Thread(new Runnable() {
            public void run() {
                try{runServer();}catch (Exception e){}
            }
        }).start();
    }
    public void stop(){
        stop=true;
    }
}
