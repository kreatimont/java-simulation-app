package model;

import process.Actor;
import process.DispatcherFinishException;
import simulation.CornModel;
import ui.MainForm;

/* Сборочная машина(собирает зерно, приготовленное комбайном) */
public class Car extends Actor {

    private MainForm gui;
    private String name;

    @Override
    protected void rule() throws DispatcherFinishException {
        // TODO Auto-generated constructor stub
    }

    public Car(MainForm gui, String name, CornModel model) {
        // TODO Auto-generated constructor stub
        this.gui = gui;
        this.name = name;
    }

}
