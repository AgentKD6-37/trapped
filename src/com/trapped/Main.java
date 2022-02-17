package com.trapped;

import com.trapped.utilities.FileManager;
import com.trapped.view.MainFrame;

import javax.swing.*;
import java.util.TimerTask;
import java.util.Timer;


public class Main {

    public ActionController aHandler = new ActionController(this);
    MainFrame mainFrame=new MainFrame(this);

    public static void main(String[] args) {
        FileManager.writeDefaults();
        new Main();

    }

}