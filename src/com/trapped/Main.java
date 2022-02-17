package com.trapped;

import com.trapped.utilities.FileManager;
import com.trapped.view.MainFrame;


public class Main {

    public ActionController aHandler = new ActionController(this);
    MainFrame mainFrame=new MainFrame(this);

    public static void main(String[] args) {
        FileManager.writeDefaults();
        new Main();
    }

}