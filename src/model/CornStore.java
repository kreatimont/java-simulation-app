package model;

import process.Actor;
import process.DispatcherFinishException;
import simulation.CornModel;
import sun.applet.Main;
import ui.MainForm;

/*Элеватор, склад зерна -- разгружает машину*/
public class CornStore extends Actor {

    private MainForm gui;
    private String name;

    @Override
    protected void rule() throws DispatcherFinishException {

    }

    public CornStore(MainForm gui, String name, CornModel cornModel) {
        super();
        this.gui = gui;
        this.name = name;
    }


}
