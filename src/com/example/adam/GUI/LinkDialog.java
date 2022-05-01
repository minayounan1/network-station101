package com.example.adam.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LinkDialog extends JDialog implements ActionListener {

    public static String OK_BTN = "okButton";
    private final Canvas canvas;
    private JTextField bwTxt;
    private JTextField distanceTxt;

    public LinkDialog(Container owner) {
        canvas = (Canvas) owner;
        canvas.setCurrentDistance(LinkItem.DISTANCE);
        setLocationRelativeTo(owner);
        initComponents();

    }

    private void initComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridwidth = 1;
        c.gridheight = 1;
        c.fill = GridBagConstraints.HORIZONTAL;

        c.insets = new Insets(0, 15, 0, 5);
        c.gridx = 0;
        c.gridy = 0;
        add(new JLabel("Distance:"), c);

        c.weighty = .2;

        distanceTxt = new JTextField(String.valueOf(LinkItem.DISTANCE));
        c.insets = new Insets(0, 0, 0, 15);
        c.gridx = 1;
        c.gridy = 0;
        add(distanceTxt, c);

        JButton okBtn = new JButton("Ok");
        okBtn.setActionCommand(OK_BTN);
        okBtn.addActionListener(this);

        c.insets = new Insets(-10, 15, 10, 15);
        c.fill = GridBagConstraints.NONE;
        c.gridwidth = 2;
        c.weightx = .8;
        c.gridx = 0;
        c.gridy = 1;
        add(okBtn, c);

        setModalityType(ModalityType.APPLICATION_MODAL);
        setTitle("Link options");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(250, 140);
        setResizable(false);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        int distance = -1;
        try {
            distance = Integer.parseInt(distanceTxt.getText());
        } catch (NumberFormatException ex) {
            return;
        }
        if (distance > 0) {
            canvas.setCurrentDistance(distance);
            dispose();
        }


    }

}
