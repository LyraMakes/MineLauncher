package com.caitmake.minelauncher;

import javax.swing.*;

public class Main {
    private static final String title = "MineLauncher v0.1";

    public static void main(String[] args) {
        setLookAndFeel();
        SwingUtilities.invokeLater(() -> new GUI(title + " - Home"));
    }

    private static void setLookAndFeel() {
        final String SYSTEM_LF = UIManager.getSystemLookAndFeelClassName();
        try {
            UIManager.setLookAndFeel(SYSTEM_LF);
        } catch (Exception exc) {
            //Ignore exception
        }
    }
}
