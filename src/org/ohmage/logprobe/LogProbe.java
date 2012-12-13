
package org.ohmage.logprobe;

import android.content.Context;

/**
 * Creates a probewriter to send data to ohmage
 * 
 * @author cketcham
 */
public class LogProbe {

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

    public static LogProbeWriter probeWriter;
    public static boolean logAnalytics;
    public static Loglevel logLevel;

    public LogProbe(boolean logAnalytics, String logLevel) {
        LogProbe.logAnalytics = logAnalytics;
        LogProbe.logLevel = Loglevel.valueOf(logLevel.toUpperCase());
    }

    public void connect(Context context) {
        probeWriter = new LogProbeWriter(context);
        probeWriter.connect();
    }

    public void close() {
        if (probeWriter != null) {
            probeWriter.close();
            probeWriter = null;
        }
    }
}
