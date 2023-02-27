package com.lyramake.minelauncher;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class ServerProperties {
    public String name;
    public String type;
    public String version;

    public String description;

    public ServerProperties () {
        this.name = "Undefined Name";
        this.type = "Undefined Type";
        this.version = "Undefined Version";

        this.description = "Undefined Description";
    }

    public static ServerProperties loadProperties(File file) {
        ObjectMapper mapper = new ObjectMapper();
        ServerProperties props;
        try {
            // Uncomment the next line to use a bodged fix for linux suppport
            if (file == null) throw new IOException();
            props = mapper.readValue(file, ServerProperties.class);
        } catch (IOException ioExc) {
            System.err.println("Error fetching properties file, using default properties instead");
            props = new ServerProperties();
        }
        return props;
    }

    public static void saveProperties(String filePath, ServerProperties props) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(filePath), props);
        } catch (IOException ioExc) {
            System.err.println("Error Saving to file - properties not saved");
        }
    }
}