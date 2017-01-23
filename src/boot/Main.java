package boot;

import javafx.application.Application;
import javafx.stage.Stage;
import view.gui.GuiRun;

public class Main extends Application {
    private GuiRun gui=new GuiRun();

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        gui.start(primaryStage);
    }
}
