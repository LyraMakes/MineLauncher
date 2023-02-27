package com.lyramake.minelauncher;

import javax.swing.*;
import java.awt.*;
import java.io.File;


class ServerListItem extends JPanel {

    public ServerProperties props;

    public ServerListItem(Dimension dimensions, File serverPropFile) {
        super();
        setSize(dimensions);

        int componentWidth = 280;

        props = ServerProperties.loadProperties(serverPropFile);

        JLabel serverName = new JLabel(props.name);
        JLabel serverType = new JLabel(props.type);

        serverName.setBounds(10, 10, componentWidth, 20);
        serverType.setBounds(10, 50, componentWidth, 15);

        Font nameLabel = new Font("Monospaced", Font.BOLD, 20);
        serverName.setFont(nameLabel);
        Font typeLabel = new Font("Monospaced", Font.ITALIC, 15);
        serverType.setFont(typeLabel);

        Color textColor = new Color(210, 210, 210);
        serverName.setForeground(textColor);
        serverType.setForeground(textColor);


        setLayout(null);
        add(serverName);
        add(serverType);
    }

    @Override
    public String toString() {
        return props.name + ": { " + props.type + " : " + props.version + " }";
    }
}
