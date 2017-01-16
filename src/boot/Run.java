package boot;

import controller.MyController;
import model.MyModel;
import view.MyView;

public class Run {
    public static void main(String[] args) {
        MyView ui=new MyView();
        MyModel model=new MyModel();
        MyController controller=new MyController(ui,model);
        ui.addObserver(controller);
        model.addObserver(controller);
        controller.start();
    }
}
