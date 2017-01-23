package view;

import model.Model;
import model.data.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Scanner;

public class MyView extends Observable implements View {
    private BufferedReader reader;
    private PrintWriter writer;
    private String exitString;

    public MyView(BufferedReader reader, PrintWriter writer, String exitString) {
        this.reader = reader;
        this.writer = writer;
        this.exitString = exitString;
    }


    public void display(Level l) {
        Displayer displayer;
        displayer = new MySokobanDisplay(l);
        displayer.display();
    }

    @Override
    public void setMoveMade(String s) {

    }

    @Override
    public void Finished() {
        System.out.println("Level Finished!");
    }

    @Override
    public void notifyObservers() {
        super.notifyObservers();
    }

    @Override
    public void displayMessage(Command c) {
        System.out.println("Error: " + c);
    }

    @Override
    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String commandLine = "";
                do {
                    writer.print("Enter command: \n");
                    writer.flush();
                    try {
                        commandLine = reader.readLine();
                        String[] arr = commandLine.split(" ");
                        LinkedList<String> params = new LinkedList<String>();
                        for (String param : arr) {
                            params.add(param);
                        }
                        setChanged();
                        notifyObservers(params);


                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } while (!commandLine.equals(exitString));
            }
        }).start();
    }
}




/*public void displayData(Level lvl) {
        displayer=new MySokobanDisplay(lvl);
        displayer.display();
    }
    public void userInput()
    {
        System.out.println("Enter Command:\n>Load file name\n>Display\n>Move {up, down, left, right}\n>Save file name\n>Exit");
        Scanner scanner=new Scanner(System.in);
        String input=scanner.nextLine();
        String[]inputs=input.split("\\s+"); //splits the line to 2 strings
        while(inputs.length>2) {
            System.out.println("Invalid Input.");
            input = scanner.nextLine();
            inputs=input.split("\\s+");
        } //asking for an input if its more than 3 words.
        userinput=inputs;

    }*/
