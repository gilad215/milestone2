package boot;

import controller.MyController;
import model.MyModel;
import view.MyView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Run {
    public static void main(String[] args) {
        MyModel model=new MyModel();


        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter(System.out);
        MyView ui= new MyView(reader,writer,"Exit");

        MyController controller=new MyController(ui,model);
        model.addObserver(controller);
        ui.addObserver(controller);
        ui.start();

    }
}
