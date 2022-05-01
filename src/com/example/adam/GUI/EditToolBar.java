package com.example.adam.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class EditToolBar extends JToolBar {

    public final static String ADD_NODE = "addStation";
    public final static String ADD_CONNECTION = "addLink";
    public final static String DELETE = "delete";

    public EditToolBar(Canvas canvasListener) {
        super("Network edition");
        addButtons(canvasListener);
        setFloatable(false);
    }

    private void addButtons(Canvas canvasListener) {
        JButton button = null;
        button = makeEditButton("node", ADD_NODE, "Add a network node",
                "Network Node", canvasListener);
        add(button);
        button = makeEditButton("wire", ADD_CONNECTION, "Add a connection", "Wire",
                canvasListener);
        add(button);
        addSeparator();
        button = makeEditButton("delete", DELETE, "Delete a component",
                "Delete", canvasListener);
        add(button);
    }

    private JButton makeEditButton(String imageName, String actionCommand,
                                   String toolTipText, String altText,
                                   Canvas canvasListener) {

        String imgLocation = "gifs/" + imageName + ".gif";
        URL imageURL = getClass().getResource(imgLocation);

        JButton button = new JButton();
        button.setActionCommand(actionCommand);
        button.setToolTipText(toolTipText);
        button.addActionListener(canvasListener);


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
}
