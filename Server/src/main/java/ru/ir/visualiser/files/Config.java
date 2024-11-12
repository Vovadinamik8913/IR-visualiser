package ru.ir.visualiser.files;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.io.File;
import java.io.IOException;


public class Config {
    @Getter
    private String[] optsPath;
    @Getter
    private String workPath;
    private static Config instance;

    private Config() {}

    public static Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    public static void deserializeFromJson() {
        String filepath = "irvis.json";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            instance = objectMapper.readValue(new File(filepath), Config.class);
        } catch (IOException e) {
            e.getMessage();
        }
    }

}