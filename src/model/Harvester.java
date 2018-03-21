package model;

import process.Actor;
import process.DispatcherFinishException;
import simulation.CornModel;
import ui.MainForm;

/* Зерноуборочная машина */
public class Harvester extends Actor {

    private MainForm gui;

    private String name;

    public Harvester(MainForm gui, String name, CornModel cornModel) {
        // TODO Auto-generated constructor stub
        super();
        this.gui = gui;
        this.name = name;
    }

    @Override
    protected void rule() throws DispatcherFinishException {
        // TODO Auto-generated method stub

    }

}
