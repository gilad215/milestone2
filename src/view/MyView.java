package view;

import commands.Displayer;
import commands.MySokobanDisplay;
import levels.Level;

import javax.swing.text.View;
import java.util.Observable;
import java.util.Scanner;

public class MyView extends Observable implements view.View{
    private Displayer displayer;
    String[] userinput;

    @Override
    public void displayData(Level lvl) {
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

    }
    @Override
    public String[] getInput() {
        return userinput;
    }

}
