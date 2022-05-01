package com.example.adam.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class AlarmDisplayer extends JPanel {

    private static final JLabel alarmPicture = new JLabel();
    private static boolean enabled;

    private static final BufferedImage[] images = new BufferedImage[2];

    public AlarmDisplayer() {
        try {
            images[0] = ImageIO.read(getClass().getResource("gifs/greenalarm.gif"));
            images[1] = ImageIO.read(getClass().getResource("gifs/redalarm.gif"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        setAlarmPicture(true);
        add(alarmPicture);
    }


    public void setAlarmPicture(boolean condition) {
        if (condition) {
            alarmPicture.setIcon(new ImageIcon(images[0].getScaledInstance(340, 273, Image.SCALE_FAST)));
        } else {
            alarmPicture.setIcon(new ImageIcon(images[1].getScaledInstance(340, 273, Image.SCALE_FAST)));
        }
        //True = green, False = red
    }


}
