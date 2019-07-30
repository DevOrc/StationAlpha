package com.noahcharlton.stationalpha.desktop;

import com.badlogic.gdx.Gdx;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.io.PrintWriter;
import java.io.StringWriter;

public class Watchdog implements Thread.UncaughtExceptionHandler {

    private final Logger logger = LogManager.getLogger(Watchdog.class);

    public static void watch(Thread gameThread){
        Watchdog watchdog = new Watchdog();

        gameThread.setUncaughtExceptionHandler(watchdog);
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Gdx.app.exit();

        logger.info("Game threw exception! Exiting app...");
        e.printStackTrace();

        SwingUtilities.invokeLater(() -> showErrorMessage(e));
    }

    private void showErrorMessage(Throwable error) {
        logger.debug("Showing error dialog!");
        JOptionPane.showMessageDialog(null, getDetails(error),
                "Error", JOptionPane.ERROR_MESSAGE);
        logger.debug("Error dialog closed! Shutting down JVM");

        System.exit(-1);
    }

    private String getDetails(Throwable error) {
        StringWriter writer = new StringWriter();
        error.printStackTrace(new PrintWriter(writer));

        return writer.toString();
    }
}
