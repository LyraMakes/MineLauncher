package com.caitmake.minelauncher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

@SuppressWarnings({"FieldCanBeLocal", "ConstantConditions"})
public class GUI extends JFrame {
    private static final Dimension DIMENSIONS = new Dimension(1015, 640);

    private static final Color serverSelectBg = new Color(40,40,40);
    private static final Color evenColor = new Color(80, 80, 80);
    private static final Color oddsColor = new Color (100, 100, 100);

    private static JPanel pane;
    private static JPanel serverSelect;


    ServerListItem[] serverList;
    ServerDescription serverDesc;
    ButtonContainer buttonPane;

    public GUI(String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(DIMENSIONS);

        pane = new JPanel(null);

        File startDir = new File("servers");
        String[] directories = startDir.list((current, name) -> new File(current, name).isDirectory());

        Dimension sli = new Dimension(300,80);

        serverSelect = new JPanel(null);

        serverSelect.setBackground(serverSelectBg);

        if (directories != null) {
            serverList = new ServerListItem[directories.length];
            for (int i = 0; i < directories.length; ++i) {
                serverList[i] = new ServerListItem(sli, new File("servers/" + directories[i] + "/ServerProperties.json"));
                serverList[i].setBounds(0, sli.height * i, sli.width, sli.height);

                serverList[i].setBackground( (i % 2 == 0) ? evenColor : oddsColor );

                serverSelect.add(serverList[i]);
            }

        } else {
            serverList = new ServerListItem[] {new ServerListItem(sli, null)};
            serverList[0].setBounds(0, 0, sli.width, sli.height);

            serverList[0].setBackground(evenColor);

            serverSelect.add(serverList[0]);
        }

        for (int i = 0; i < serverList.length; ++i) {
            int finalI = i;
            serverList[i].addMouseListener(new MouseAdapter() {
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


@SuppressWarnings("FieldCanBeLocal")
class ServerListItem extends JPanel {
    private final JLabel serverName;
    private final JLabel serverType;

    private final Font nameLabel = new Font("Monospaced", Font.BOLD, 20);
    private final Font typeLabel = new Font("Monospaced", Font.ITALIC, 15);

    private final Color textColor = new Color(210,210,210);

    public ServerProperties props;

    public ServerListItem(Dimension dimensions, File serverPropFile) {
        super();
        setSize(dimensions);

        int componentWidth = 280;

        props = ServerProperties.loadProperties(serverPropFile);

        serverName = new JLabel(props.name);
        serverType = new JLabel(props.type);

        serverName.setBounds(10, 10, componentWidth, 20);
        serverType.setBounds(10, 50, componentWidth, 15);

        serverName.setFont(nameLabel);
        serverType.setFont(typeLabel);

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

@SuppressWarnings("FieldCanBeLocal")
class ServerDescription extends JPanel {
    private final JLabel serverName;
    private final JLabel serverType;
    private final JLabel serverDesc;

    private final Font nameLabel = new Font("Monospaced", Font.BOLD, 50);
    private final Font typeLabel = new Font("Monospaced", Font.ITALIC, 30);
    private final Font descFont = new Font("Segoe UI", Font.PLAIN, 20);

    private final Color textColor = new Color(20,20,20);

    public ServerDescription(Dimension dimensions) {
        super();
        setSize(dimensions);

        serverName = new JLabel("Undefined");
        serverType = new JLabel("Undefined : Undefined");
        serverDesc = new JLabel("Undefined");


        serverName.setBounds(10, 10, 700, 55);
        serverType.setBounds(10, 70, 700, 40);
        serverDesc.setBounds(10, 120, 700, 200);

        serverName.setFont(nameLabel);
        serverType.setFont(typeLabel);

        serverDesc.setFont(descFont);


        serverName.setForeground(textColor);
        serverType.setForeground(textColor);

        serverDesc.setForeground(textColor);


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

@SuppressWarnings("FieldCanBeLocal")
class ButtonContainer extends JPanel {
    private final JButton runBatch;
    private final JButton openDir;

    public String batchPath;

    private final Font runButtonFont = new Font("Monospaced", Font.BOLD, 40);
    private final Font opnButtonFont = new Font("Monospaced", Font.PLAIN, 30);

    private final Color textColor = new Color(10,10,10);

    public ButtonContainer(Dimension dimensions) {
        super();
        setSize(dimensions);

        batchPath = "";

        runBatch = new JButton(new AbstractAction("RUN") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Process p = Runtime.getRuntime().exec("cmd /c start \"MineLauncher v1.0 - Server Console\" " + batchPath + "\\run.bat");
                } catch (IOException ioExc) {
                    System.err.println("Unable to Find 'run.bat' in directory :" + batchPath);
                }
            }
        });

        openDir = new JButton(new AbstractAction("OPEN DIR") {
            @Override
            public void actionPerformed(ActionEvent e) {
                Desktop desktop = Desktop.getDesktop();
                try {
                    File mainPath = new File(System.getProperty("user.home") + "\\MineLauncher\\");
                    desktop.open(new File(mainPath + "\\" + batchPath));
                } catch (IllegalArgumentException | IOException exc) {
                    System.err.println("Unable to locate directory");
                    exc.printStackTrace();
                }
            }
        });

        runBatch.setBounds(40, 20, 300, 65);
        openDir.setBounds(375, 20, 300, 65);

        runBatch.setFont(runButtonFont);
        openDir.setFont(opnButtonFont);

        runBatch.setForeground(textColor);
        openDir.setForeground(textColor);


        setLayout(null);
        add(runBatch);
        add(openDir);
    }
}