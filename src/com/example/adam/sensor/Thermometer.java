package com.example.adam.sensor;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Thermometer {

    protected boolean enabled;
    private boolean randomEnabled = false;
    private int temperature;
    private final int averageTemp = (int) (Math.random() * 10 + 20);

    //Observer pattern
    private final PropertyChangeSupport support;

    public Thermometer(int temperature) {
        support = new PropertyChangeSupport(this);
        this.temperature = temperature;
    }

    public Thermometer() {
        this(0);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    public void setRandomValue() {
        if (randomEnabled) {
            int value = (int) (averageTemp * (.9 + .2 * Math.exp(-.1 * Math.random() * 23)));
            support.firePropertyChange("temp", this.temperature, value);
            this.temperature = value;
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void reset() {
        enabled = true;
    }

    public void setValue(int temp) {
        support.firePropertyChange("temp", this.temperature, temp);
        this.temperature = temp;
    }

    public boolean isRandomEnabled() {
        return randomEnabled;
    }

    public void setRandomEnabled(boolean randomEnabled) {
        this.randomEnabled = randomEnabled;
    }

    public String readValue() {
        return "" + this.temperature;
    }


    public String getType() {
        return "T";
    }

    @Override
    public String toString() {
        return "Thermometer";
    }


}