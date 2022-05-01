package com.example.adam.GUI;

import com.example.adam.Network;
import com.example.adam.Simulator;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class MainWindow extends JFrame {

    private static Network network;
    private static Simulator simulator;
    private static JTextArea logText;
    private static AlarmDisplayer alarmDisplayer;

    public MainWindow() {
        super("Thermometer Network Simulator");
        network = new Network();
        alarmDisplayer = new AlarmDisplayer();
        simulator = new Simulator(network, 1000, 25, alarmDisplayer);
        initInfoPanels();
    }

    public static void main(String[] args) {

        System.setOut(new PrintStream(new FilteredStream(new ByteArrayOutputStream())));

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });

    }

    private void initInfoPanels() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        StationInfoPanel infoPanel = new StationInfoPanel();
        Canvas canvas = new Canvas(network, infoPanel, alarmDisplayer);

        EditToolBar editBar = new EditToolBar(canvas);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 10, 0, 0);
        c.weightx = .1;
        c.gridx = 0;
        c.gridy = 0;
        add(editBar, c);


        SimulationToolBar simuBar = new SimulationToolBar(simulator, canvas, alarmDisplayer);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 10);
        c.weightx = 1;
        c.gridx = 1;
        c.gridy = 0;
        add(simuBar, c);

        JScrollPane canvasScroll = new JScrollPane(canvas);
        canvasScroll.setMinimumSize(new Dimension(300, 50));

        infoPanel.setMinimumSize(new Dimension(200, 50));
        JScrollPane infoPanelScroll = new JScrollPane(infoPanel);
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, canvas, infoPanelScroll);


        splitPane.setDividerLocation(600);


        logText = new JTextArea();
        logText.setEditable(false);
        logText.setBackground(Color.WHITE);
        logText.setAutoscrolls(true);


        JScrollPane logTextPane = new JScrollPane(logText);
        JScrollPane alarmPane = new JScrollPane(alarmDisplayer);
        JSplitPane splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, logTextPane, alarmPane);

        splitPane2.setDividerLocation(600);

        JSplitPane mainSplitPane = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                splitPane,
                splitPane2);

        mainSplitPane.setDividerLocation(400);


        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(10, 10, 10, 10);
        c.weightx = 1;
        c.weighty = 1;
        c.gridx = 0;
        c.gridwidth = 2;
        c.gridy = 1;
        add(mainSplitPane, c);


    }

    private static class FilteredStream extends FilterOutputStream {

        public FilteredStream(OutputStream out) {
            super(out);
        }

        @Override
        public void write(byte[] b) throws IOException {
            logText.append(new String(b));
        }

        @Override
        public void write(int b) throws IOException {
            logText.append(String.valueOf((char) b));
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            String tmpString = new String(b, off, len);
            logText.append(tmpString);
        }
    }

}
