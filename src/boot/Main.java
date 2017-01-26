package boot;

import controller.server.CLIclient;
import controller.server.Server;
import javafx.application.Application;
import javafx.stage.Stage;
import view.gui.GuiRun;

public class
Main extends Application {
    private GuiRun gui=new GuiRun();

    public static void main(String[] args) {
        //CLIclient ch= new CLIclient();
        //int port=2159;
        //Server server= new Server(port,ch);
        //server.start();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        gui.start(primaryStage);
    }
}
