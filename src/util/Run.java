package util;


import commands.CLI;

import java.io.*;

public class Run {
    public static void main(String[] args) throws IOException {
        CLI cli= new CLI();
        cli.initCommand();
    }
}
