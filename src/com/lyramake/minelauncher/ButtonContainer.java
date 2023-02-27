package com.lyramake.minelauncher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

class ButtonContainer extends JPanel {

    public String batchPath;

    public ButtonContainer(Dimension dimensions) {
        super();
        setSize(dimensions);

        batchPath = "";

        JButton runBatch = new JButton(new AbstractAction("RUN") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Process p = Runtime.getRuntime().exec("cmd /c start \"MineLauncher v1.0 - Server Console\" " + batchPath + "\\run.bat");
                } catch (IOException ioExc) {
                    System.err.println("Unable to Find 'run.bat' in directory :" + batchPath);
                }
            }
        });

        JButton openDir = new JButton(new AbstractAction("OPEN DIR") {
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

        runBatch.setFont(Configuration.runButtonFont);
        openDir.setFont(Configuration.opnButtonFont);

        runBatch.setForeground(Configuration.buttonTextColor);
        openDir.setForeground(Configuration.buttonTextColor);

        setLayout(null);
        add(runBatch);
        add(openDir);
    }
}
