package de.klein_max.adventcalendar.tools;

public class ExceptionListener implements Thread.UncaughtExceptionHandler {
    public void uncaughtException(Thread t, Throwable e) {
        handle(e);
    }

    public void handle(Throwable throwable) {
        try {
            DevTweaks.showError(new Exception(throwable));
        } catch (Throwable t) {
            // don't let the exception get thrown out, will cause infinite looping!
        }
    }

    public static void registerExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionListener());
        System.setProperty("sun.awt.exception.handler", ExceptionListener.class.getName());
    }
}