package com.lyramake.minelauncher;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {
        setLookAndFeel();
        SwingUtilities.invokeLater(() -> new GUI(Configuration.WINDOW_TITLE + " - Home"));
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
