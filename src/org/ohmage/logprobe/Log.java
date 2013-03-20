
package org.ohmage.logprobe;

import org.ohmage.logprobe.LogProbe.Loglevel;

import java.util.Locale;

/**
 * Writes log messages
 * 
 * @author cketcham
 */
public class Log {

    /**
     * Checks to see if we want to log this level of messages
     * 
     * @param logLevel
     * @return
     */
    private static boolean shouldLogMessage(Loglevel logLevel) {
        if (LogProbe.logLevel == null)
            throw new RuntimeException("logLevel must be set before writing log");

        try {
            return logLevel.compareTo(LogProbe.logLevel) <= 0;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static void log(Loglevel loglevel, String tag, String message) {
        if (shouldLogMessage(loglevel)) {
            android.util.Log.d(tag, message);
            if (LogProbe.probeWriter == null)
                throw new RuntimeException("LogProbe must be connected");
            LogProbe.probeWriter.log(loglevel.name().toLowerCase(Locale.US), tag, message);
        }
    }

    public static void i(String tag, String message) {
        log(Loglevel.INFO, tag, message);
    }

    public static void d(String tag, String message) {
        log(Loglevel.DEBUG, tag, message);
    }

    public static void e(String tag, String message, Throwable throwable) {
        log(Loglevel.ERROR, tag, message + "\n" + android.util.Log.getStackTraceString(throwable));
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
