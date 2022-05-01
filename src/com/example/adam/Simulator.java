package com.example.adam;

import com.example.adam.GUI.AlarmDisplayer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;

public final class Simulator {

    private final Network net;
    private GregorianCalendar date;
    private final Timer timer;
    private int temperature;
    private final AlarmDisplayer displayer;


    public Simulator(Network network, int delay, int temp, AlarmDisplayer displayer) {
        net = network;
        temperature = temp;
        this.displayer = displayer;

        timer = new Timer(1, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                next();
            }
        });
        setTemperature(temp);
        setDelay(delay);
    }

    public Simulator(Network network, AlarmDisplayer displayer) {
        this(network, 1000, 25, displayer);
    }

    private void next() {
        System.out.println("\n----------- Next step --------------");

        final BaseStation base = net.getBaseStation();
        base.sendRequests();

        net.getBaseStation().setTemperature(temperature);
    }

    public void run() {
        if (!timer.isRunning()) {
            System.out.println("\nStarting the simulation...");
            initialize();
            System.gc(); //garbage collector
            net.setModified(false);
            //net.getBaseStation().updateDataBase();
            timer.start();
            displayer.setAlarmPicture(true);
            net.getBaseStation().setTemperature(temperature);
        }
    }

    public void stop() {
        if (timer.isRunning()) {
            timer.stop();
            System.out.println("Simulation Stopped");
        }
    }

    private void initialize() {

        for (Node node : net.getNodes()) {
            System.out.println("Updating the route table of " + node.getName());
            node.getRouter().updateRouteTable();
        }
    }

    public void setDelay(int delay) {
        timer.setDelay(delay);
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

}


