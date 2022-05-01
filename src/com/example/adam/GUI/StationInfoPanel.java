package com.example.adam.GUI;

import com.example.adam.Station;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class StationInfoPanel extends JPanel implements ActionListener, PropertyChangeListener {

    private Station station;
    private DefaultListModel sensorModel;
    private JTextField tempField;
    private JLabel title;
    private JLabel state;
    private JLabel randNumState;
    private JLabel displayStationTemp;

    public StationInfoPanel() {
        station = null;
        setVisible(false);
        initComponents();
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getSource().equals(station.getSensor()))
            setDisplayStationTemp((int) evt.getNewValue());
    }

    public void updateComponents() {
        title.setText(station.getName() + " (ID: " +
                station.getID() + ")");

        if (station.isActive()) {
            state.setText("Enabled");
            state.setForeground(Color.GREEN);
        } else {
            state.setText("Disabled");
            state.setForeground(Color.RED);
        }

        if (station.getSensor().isRandomEnabled()) {
            randNumState.setText("Enabled");
            randNumState.setForeground(Color.GREEN);
        } else {
            randNumState.setText("Disabled");
            randNumState.setForeground(Color.RED);
        }

        setDisplayStationTemp(Integer.parseInt(station.getSensor().readValue()));
    }

    private void initializeObserver() {
        station.getSensor().addPropertyChangeListener(this);
    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weightx = .8;

        title = new JLabel();
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));

        title.setIconTextGap(20);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        c.insets = new Insets(20, 20, 0, 20);
        add(title, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.insets = new Insets(10, 20, 0, 0);
        add(new JLabel("State: "), c);

        c.gridx = 1;
        c.gridwidth = 1;
        c.insets = new Insets(8, -30, 0, 20);
        state = new JLabel();
        state.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        add(state, c);

        c.gridx = 2;
        c.insets = new Insets(10, -60, 0, 20);
        JButton stateBtn = new JButton("Change state");
        stateBtn.setActionCommand("changeState");
        stateBtn.setToolTipText("Click to enable or disable the station");
        stateBtn.addActionListener(this);
        add(stateBtn, c);


        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        c.insets = new Insets(10, 20, 0, 20);
        add(new JLabel("Random Temp:"), c);

        c.gridx = 1;
        c.gridwidth = 1;
        c.insets = new Insets(8, -30, 0, 20);
        randNumState = new JLabel();
        randNumState.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        add(randNumState, c);

        c.gridx = 2;
        c.insets = new Insets(10, -60, 0, 20);
        JButton randTempBtn = new JButton("Random Number");
        randTempBtn.setActionCommand("randomTemperatureGenerator");
        randTempBtn.setToolTipText("Random number generator");
        randTempBtn.addActionListener(this);
        add(randTempBtn, c);


        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 3;
        c.insets = new Insets(20, 20, 0, 10);

        JLabel tempLabel = new JLabel("Node Temperature (C): ");
        add(tempLabel, c);

        c.gridx = 1;
        c.insets = new Insets(20, 30, 0, 150);

        tempField = new JTextField("0");
        tempField.setHorizontalAlignment(JTextField.RIGHT);
        tempField.setMaximumSize(new Dimension(30, 50));

        add(tempField, c);


        sensorModel = new DefaultListModel();

        JButton addBtn = new JButton("Add Temp");
        addBtn.setActionCommand("addTemperature");
        addBtn.setToolTipText("Add Temperature");
        addBtn.addActionListener(this);

        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 5;
        c.weighty = 0;
        c.insets = new Insets(15, 25, 10, 20);
        add(addBtn, c);

        JButton resetBtn = new JButton("Reset");
        resetBtn.setActionCommand("resetSensor");
        resetBtn.setToolTipText("Reset a sensor");
        resetBtn.addActionListener(this);

        c.gridx = 1;
        add(resetBtn, c);


        JLabel fjfj = new JLabel("Right Now (C):");
        c.gridx = 0;
        c.gridy = 6;
        c.weighty = .1;
        add(fjfj, c);

        displayStationTemp = new JLabel("0");
        displayStationTemp.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 45));

        c.gridx = 1;
        add(displayStationTemp, c);

    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("addTemperature")) {
            if (!station.getSensor().isRandomEnabled()) {
                try {
                    int temp = Integer.parseInt(tempField.getText());
                    station.getSensor().setValue(temp);
                } catch (NumberFormatException numberFormatException) {
                    numberFormatException.printStackTrace();
                }
            }
        } else if (command.equals("resetSensor")) {
            tempField.setText("0");
            station.getSensor().reset();
            station.getSensor().setValue(0);
        } else if (command.equals("changeState")) {
            station.setActive(!station.isActive());
            updateComponents();
        } else if (command.equals("randomTemperatureGenerator")) {
            if (station.getSensor().isRandomEnabled()) {
                station.getSensor().setRandomEnabled(false);
                tempField.setEnabled(true);
                station.getSensor().setValue(Integer.parseInt(tempField.getText()));
            } else {
                tempField.setEnabled(false);
                station.getSensor().setRandomEnabled(true);
                station.getSensor().setRandomValue();
            }
            updateComponents();
        }
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
        if (station != null) {
            setVisible(true);
            updateComponents();
            initializeObserver();
        } else
            setVisible(false);

    }

    public void setDisplayStationTemp(int st) {
        displayStationTemp.setText("" + st);
    }

}
