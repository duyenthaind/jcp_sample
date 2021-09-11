package com.thaind.jcp.fraction7;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author duyenthai
 */
public class UEHLogger implements Thread.UncaughtExceptionHandler {


    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Logger logger = Logger.getAnonymousLogger();
        logger.log(Level.SEVERE, String.format("Thread terminated with exception: %s", t.getName()), e);
    }
}
