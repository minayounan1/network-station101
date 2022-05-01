package com.example.adam.GUI;

import com.example.adam.BaseStation;
import com.example.adam.Network;
import com.example.adam.Node;
import com.example.adam.Station;
import com.example.adam.exceptions.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Canvas extends JPanel implements ActionListener, MouseMotionListener, MouseListener {

    private final ArrayList<NodeItem> nodes;
    private final ArrayList<LinkItem> links;
    private final Network net;
    private final StationInfoPanel infoPanel;
    private String toolSelected;
    private NodeItem firstNode;
    private LinkItem currentLink;
    private boolean nodeDrag;
    private boolean editable;
    private final AlarmDisplayer displayer;

    private int currentDistance;

    public Canvas(Network net, StationInfoPanel infoPanel, AlarmDisplayer displayer) {
        nodes = new ArrayList<NodeItem>();
        links = new ArrayList<LinkItem>();
        this.net = net;
        this.infoPanel = infoPanel;
        this.displayer = displayer;

        try {
            BaseStation base = new BaseStation("Base", net, displayer);
            net.addNode(base);
            nodes.add(new BaseNodeItem(new Point(150, 150), base.getID()));

        } catch (DuplicateNodeException e) {
        }

        toolSelected = EditToolBar.ADD_NODE;
        firstNode = null;
        currentLink = null;
        nodeDrag = false;
        editable = true;

        setBackground(Color.WHITE);
        addMouseMotionListener(this);
        addMouseListener(this);
    }

    public void setEditable(boolean edit) {
        editable = edit;
        if (edit)
            setBackground(Color.WHITE);
        else
            setBackground(Color.decode("0xE8E8E8"));
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        toolSelected = e.getActionCommand();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        Point mousePos = new Point(e.getX(), e.getY());

        if (e.getButton() == MouseEvent.BUTTON3) {
            Station station;
            NodeItem node = getNode(mousePos);
            try {
                if (node.getID() > 0) {             // is not the base
                    station = (Station) net.getNodeFromID(node.getID());
                    infoPanel.setStation(station);
                }
            } catch (NodeNotFoundException ex) {

            } catch (NullPointerException ex) {
                infoPanel.setStation(null);
            }

            LinkItem link = getLink(mousePos);
            try {
                if (editable) {
                    Node nFrom = net.getNodeFromID(link.getNodeFrom().getID());
                    Node nTo = net.getNodeFromID(link.getNodeTo().getID());
                    // MEssage dialog with options
                    LinkDialog linkDialog = new LinkDialog(this);
                    net.modifyLink(nFrom, nTo, currentDistance);
                }
            } catch (NullPointerException ex) {
            } catch (NodeNotFoundException ex) {
            } catch (LinkNotFoundException ex) {
            }
        }

        if (!editable || e.getButton() != MouseEvent.BUTTON1)
            return;


        if (toolSelected.equals(EditToolBar.ADD_NODE)) {
            if (getNode(mousePos) == null) {
                try {
                    Station newStation = new Station(net);
                    net.addNode(newStation);
                    NodeItem newNodeItem = new NodeItem(mousePos, newStation.getID());
                    nodes.add(newNodeItem);
                } catch (DuplicateNodeException ex) {
                }
            } else
                nodeDrag = true;
        } else if (toolSelected.equals(EditToolBar.ADD_CONNECTION)) {
            if (firstNode == null) {
                firstNode = getNode(mousePos);
                if (firstNode != null) {
                    currentLink = new LinkItem(firstNode, new NodeItem(mousePos, -1));
                }
            } else {
                NodeItem secondNode = getNode(mousePos);
                try {
                    if (secondNode != firstNode) {
                        Node nodeFrom = net.getNodeFromID(firstNode.getID());
                        Node nodeTo = net.getNodeFromID(secondNode.getID());
                        net.addLink(nodeFrom, nodeTo, LinkItem.DISTANCE);
                        currentLink.setNodeTo(secondNode);
                        links.add(currentLink);

                        LinkDialog linkDialog = new LinkDialog(this);
                        net.modifyLink(nodeFrom, nodeTo, currentDistance);
                    }
                } catch (NullPointerException ex) {
                } catch (NodeNotFoundException ex) {
                } catch (NodeFullLinksException ex) {
                } catch (DuplicatedLinkException ex) {
                } catch (LinkNotFoundException ex) {
                } finally {
                    firstNode = null;
                    currentLink = null;
                }
            }
        } else if (toolSelected.equals(EditToolBar.DELETE)) {
            NodeItem nodeItem = getNode(mousePos);
            try {
                Node node = net.getNodeFromID(nodeItem.getID());
                net.removeNode(node);
                ArrayList<LinkItem> linksToDelete = new ArrayList<LinkItem>();

                for (LinkItem link : links) {
                    if (link.getNodeFrom() == nodeItem || link.getNodeTo() == nodeItem)
                        linksToDelete.add(link);
                }
                links.removeAll(linksToDelete);
                nodes.remove(nodeItem);

                if (node == infoPanel.getStation())
                    infoPanel.setStation(null);

            } catch (NullPointerException ex) {
            } catch (ClassCastException ex) {
                JOptionPane.showMessageDialog(getParent(),
                        "The base station cannot be removed", "Information",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (NodeNotFoundException ex) {
            }

            LinkItem link = getLink(mousePos);
            if (link != null) {
                try {
                    Node nodeFrom = net.getNodeFromID(link.getNodeFrom().getID());
                    Node nodeTo = net.getNodeFromID(link.getNodeTo().getID());
                    net.removeLink(nodeFrom, nodeTo);
                    links.remove(link);
                } catch (NodeNotFoundException ex) {
                }
            }
        }
        repaint();
        try {
            infoPanel.updateComponents();
        } catch (NullPointerException ex) {
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        nodeDrag = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
        firstNode = null;
        currentLink = null;
        repaint();

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }

    private NodeItem getNode(Point pointRef) {
        for (NodeItem n : nodes) {
            if (pointRef.contains(n.getPos(), (int) (n.getHeight() * 1.5)))
                return n;
        }
        return null;
    }

    private LinkItem getLink(Point pointRef) {
        for (LinkItem link : links) {
            if (pointRef.contains(link.getPos(), 30))
                return link;
        }
        return null;
    }

    public void setCurrentDistance(int distance) {
        currentDistance = distance;
    }


    @Override
    public void mouseMoved(MouseEvent e) {
        Point mousePos = new Point(e.getX(), e.getY());

        if (toolSelected.equals(EditToolBar.ADD_CONNECTION)) {
            if (currentLink != null) {
                currentLink.setNodeTo(new NodeItem(mousePos, -1));
                repaint();
            }
        } else if (nodeDrag) {
            NodeItem nodeItem = getNode(mousePos);
            if (nodeItem != null) {
                nodeItem.setPos(mousePos);
                repaint();
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (currentLink != null)
            currentLink.paint(g);
        for (LinkItem l : links)
            l.paint(g);
        for (NodeItem n : nodes)
            n.paint(g);

    }


}
