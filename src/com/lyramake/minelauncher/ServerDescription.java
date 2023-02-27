package com.lyramake.minelauncher;

import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Dimension;


class ServerDescription extends JPanel {
    private final JLabel serverName;
    private final JLabel serverType;
    private final JLabel serverDesc;

    public ServerDescription(Dimension dimensions) {
        super();
        setSize(dimensions);

        serverName = new JLabel("Undefined");
        serverType = new JLabel("Undefined : Undefined");
        serverDesc = new JLabel("Undefined");

        serverName.setBounds(10, 10, 700, 55);
        serverType.setBounds(10, 70, 700, 40);
        serverDesc.setBounds(10, 120, 700, 200);

        serverName.setFont(Configuration.nameLabel);
        serverType.setFont(Configuration.typeLabel);

        serverDesc.setFont(Configuration.descFont);

        serverName.setForeground(Configuration.textColor);
        serverType.setForeground(Configuration.textColor);

        serverDesc.setForeground(Configuration.textColor);

        setLayout(null);
        add(serverName);
        add(serverType);
        add(serverDesc);
    }

    public void setDescription(ServerProperties props) {
        this.serverName.setText(props.name);
        this.serverType.setText(props.type + ": " + props.version);
        this.serverDesc.setText(props.description);
    }
}
