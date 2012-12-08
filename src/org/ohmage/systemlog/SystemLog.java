
package org.ohmage.systemlog;

import android.app.Application;
import android.content.Context;

/**
 * Creates a probewriter to send data to ohmage
 * 
 * @author cketcham
 */
public class SystemLog extends Application {

    /**
     * Different available log levels
     * 
     * @author cketcham
     */
    public enum Loglevel {
        NONE,
        ERROR,
        WARNING,
        INFO,
        DEBUG,
        VERBOSE
    }

    public static SystemLogProbeWriter probeWriter;
    private static Loglevel mLogLevel;

    public SystemLog(String logLevel) {
        mLogLevel = Loglevel.valueOf(logLevel.toUpperCase());
    }

    public void connect(Context context) {
        probeWriter = new SystemLogProbeWriter(context);
        probeWriter.connect();
    }

    public void close() {
        if (probeWriter != null) {
            probeWriter.close();
            probeWriter = null;
        }
    }

    /**
     * Checks to see if we want to log this level of messages
     * 
     * @param logLevel
     * @return
     */
    private static boolean shouldLogMessage(Loglevel logLevel) {
        try {
            return logLevel.compareTo(mLogLevel) <= 0;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static void log(Loglevel loglevel, String tag, String message) {
        if (shouldLogMessage(loglevel))
            probeWriter.log(loglevel.name().toLowerCase(), tag, message);
    }

    public static void i(String tag, String message) {
        log(Loglevel.INFO, tag, message);
    }

    public static void d(String tag, String message) {
        log(Loglevel.DEBUG, tag, message);
    }

    public static void e(String tag, String message, Exception e) {
        log(Loglevel.ERROR, tag, message + e.getMessage());
    }

    public static void e(String tag, String message, Throwable throwable) {
        log(Loglevel.ERROR, tag, message + android.util.Log.getStackTraceString(throwable));
    }

    public static void e(String tag, String message) {
        log(Loglevel.ERROR, tag, message);
    }

    public static void v(String tag, String message) {
        log(Loglevel.VERBOSE, tag, message);
    }

    public static void w(String tag, String message) {
        log(Loglevel.WARNING, tag, message);
    }
}
