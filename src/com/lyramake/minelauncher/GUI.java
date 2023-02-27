package com.lyramake.minelauncher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;


@SuppressWarnings({"FieldCanBeLocal", "ConstantConditions"})
public class GUI extends JFrame {

    private static JPanel serverSelect;


    ServerListItem[] serverList;
    ServerDescription serverDesc;
    ButtonContainer buttonPane;

    public GUI(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Configuration.DIMENSIONS);

        JPanel pane = new JPanel(null);

        File startDir = new File("servers");
        String[] directories = startDir.list((current, name) -> new File(current, name).isDirectory());

        Dimension sli = new Dimension(300,80);

        serverSelect = new JPanel(null);

        serverSelect.setBackground(Configuration.serverSelectBg);

        if (directories != null) {
            serverList = new ServerListItem[directories.length];
            for (int i = 0; i < directories.length; ++i) {
                serverList[i] = new ServerListItem(sli, new File("servers/" + directories[i] + "/ServerProperties.json"));
                serverList[i].setBounds(0, sli.height * i, sli.width, sli.height);

                serverList[i].setBackground( (i % 2 == 0) ? Configuration.evenColor : Configuration.oddsColor );

                serverSelect.add(serverList[i]);
            }

        } else {
            serverList = new ServerListItem[] {new ServerListItem(sli, null)};
            serverList[0].setBounds(0, 0, sli.width, sli.height);

            serverList[0].setBackground(Configuration.evenColor);

            serverSelect.add(serverList[0]);
        }

        for (int i = 0; i < serverList.length; ++i) {
            int finalI = i;
            serverList[finalI].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    serverDesc.setDescription(serverList[finalI].props);
                    buttonPane.batchPath = "servers\\" + directories[finalI];
                }
            });
        }

        serverSelect.setBounds(0, 0, 300,640);

        serverDesc = new ServerDescription(new Dimension(715,500));
        serverDesc.setBounds(300, 0, 715, 500);
        serverDesc.setBackground(new Color(220,220,220));

        buttonPane = new ButtonContainer(new Dimension(715, 105));
        buttonPane.setBounds(300,500,715,105);
        buttonPane.setBackground(new Color(60,60,60));


        pane.setLayout(null);
        pane.add(serverSelect);
        pane.add(serverDesc);
        pane.add(buttonPane);

        add(pane, BorderLayout.CENTER);
        setResizable(false);
        setVisible(true);
    }
}


