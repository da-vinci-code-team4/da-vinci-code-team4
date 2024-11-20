package main;

import main.controller.*;
import main.game.*;
import main.view.TestMainFrame;

public class TestMain {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                TestMainModel model = new TestMainModel();
                TestMainFrame view = new TestMainFrame();
                TestController controller = new TestController(model, view);
                controller.initController();
            }
        });
    }
}