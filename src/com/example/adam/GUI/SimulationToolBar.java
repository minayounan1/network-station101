package com.example.adam.GUI;

import com.example.adam.Simulator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

public class SimulationToolBar extends JToolBar implements ActionListener {
    public final static String PLAY = "playSimulation";
    public final static String STOP = "stopSimulation";
    public final static String SPEED = "speedSimulation";
    public final static String TEMP = "tempSimulation";
    public final static String SETTEMP = "tempLimitSimulation";

    private final Simulator simulator;
    private JTextField tempField;
    private JTextField simSpeed;
    private final Canvas canvas;
    private final AlarmDisplayer displayer;

    public SimulationToolBar(Simulator sim, Canvas canvas, AlarmDisplayer displayer) {
        super("Simulation control");
        simulator = sim;
        initComponents();
        setFloatable(false);
        this.canvas = canvas;
        this.displayer = displayer;
    }

    private void initComponents() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JButton button = null;
        button = makeEditButton("play", PLAY, "Start the simulation", "Play");
        add(button);

        button = makeEditButton("stop", STOP, "Stop the simulation", "Stop");
        add(button);

        add(Box.createHorizontalStrut(15));

        JLabel speedLabel = new JLabel("Delay (ms): ");
        add(speedLabel);
        simSpeed = new JTextField("1000");
        simSpeed.setActionCommand(SPEED);
        simSpeed.setHorizontalAlignment(JTextField.RIGHT);
        simSpeed.addActionListener(this);
        simSpeed.setMaximumSize(new Dimension(80, 25));
        add(simSpeed);

        add(Box.createHorizontalStrut(30));

        JLabel temperatureLabel = new JLabel("Temperature Limit (C): ");
        add(temperatureLabel);
        tempField = new JTextField("25");
        tempField.setActionCommand(TEMP);
        tempField.setHorizontalAlignment(JTextField.RIGHT);
        tempField.addActionListener(this);
        tempField.setMaximumSize(new Dimension(100, 25));
        add(tempField);

        add(Box.createHorizontalStrut(30));

        JButton btn = new JButton("Set Temperature");
        btn.setActionCommand(SETTEMP);
        btn.setToolTipText("Set the temperature limit");
        btn.addActionListener(this);
        add(btn);

        add(Box.createHorizontalGlue());

    }


    private JButton makeEditButton(String imageName, String actionCommand,
                                   String toolTipText, String altText) {
        String imgLocation = "gifs/" + imageName + ".gif";
        URL imageURL = getClass().getResource(imgLocation);

        JButton button = new JButton();
        button.setActionCommand(actionCommand);
        button.setToolTipText(toolTipText);
        button.addActionListener(this);

        if (imageURL != null) {
            try {
                button.setIcon(new ImageIcon(ImageIO.read(imageURL).getScaledInstance(30, 30, Image.SCALE_FAST)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            button.setText(altText);
            System.err.println("Resource not found: " + imgLocation);
        }

        return button;
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(PLAY)) {
            try {
                simulator.setDelay(Integer.parseInt(simSpeed.getText()));
                simSpeed.setEnabled(false);
                displayer.setEnabled(true);
                simulator.run();
                tempField.setEnabled(true);
                canvas.setEditable(false);
            } catch (NullPointerException ex) {
                //then network isn't valid
                System.out.println("Aborting...");
                JOptionPane.showMessageDialog(this.getParent(),
                        "The network is not valid.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getActionCommand().equals(STOP)) {
            simulator.stop();
            tempField.setEnabled(true);
            simSpeed.setEnabled(true);
            canvas.setEditable(true);
        } else if (e.getActionCommand().equals(TEMP)) {
            try {
                simulator.setTemperature(Integer.parseInt(tempField.getText()));
            } catch (NumberFormatException ex) {
                tempField.setText("25");
            }
        } else if (e.getActionCommand().equals(SETTEMP)) {
            try {
                simulator.setTemperature(Integer.parseInt(tempField.getText()));

            } catch (NumberFormatException ex) {
                tempField.setText("25");
            }
        }
    }

}
