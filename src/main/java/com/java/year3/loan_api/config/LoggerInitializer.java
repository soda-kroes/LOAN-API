package com.java.year3.loan_api.logging;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerInitializer {
    private static final Logger logger = Logger.getLogger(LoggerInitializer.class.getName());

    public static void initializeFileLogger() {
        try {
            // Configure the FileHandler to log to a file
            FileHandler fileHandler = new FileHandler("D:\\SODA-IT\\loan-api-log.txt");
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.INFO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}