
package org.ohmage.systemlog;

import android.content.Context;

/**
 * Creates a probewriter to send data to ohmage
 * 
 * @author cketcham
 */
public class SystemLog {

    /**
     * Used as the ON/OFF indicator for activity messages
     * 
     * @author cketcham
     */
    public enum Status {
        ON,
        OFF
    }

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
    public static boolean logAnalytics;
    public static Loglevel logLevel;

    public SystemLog(boolean logAnalytics, String logLevel) {
        SystemLog.logAnalytics = logAnalytics;
        SystemLog.logLevel = Loglevel.valueOf(logLevel.toUpperCase());
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
}
