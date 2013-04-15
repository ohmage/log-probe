
package org.ohmage.logprobe;

import android.Manifest.permission;
import android.content.Context;

import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

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
        ON, OFF
    }

    /**
     * Different available log levels
     * 
     * @author cketcham
     */
    public enum Loglevel {
        NONE, ERROR, WARNING, INFO, DEBUG, VERBOSE
    }

    public static LogProbeWriter probeWriter;
    public static boolean logAnalytics;
    public static Loglevel logLevel;
    public static boolean logDeviceId = false;
    private static Set<Context> writers = Collections.synchronizedSet(new HashSet<Context>());

    /**
     * Returns a LogProbeWriter and sets up the connection for writing probes to
     * ohmage. This must be called at least once before logs are written.
     * {@link #close(Context) } should be called to prevent leaking a context
     * object.
     * 
     * @param context
     * @return
     */
    public static synchronized LogProbeWriter get(Context context) {
        writers.add(context);
        if (probeWriter == null) {
            probeWriter = new LogProbeWriter(context.getApplicationContext());
            probeWriter.connect();
        }
        return probeWriter;
    }

    /**
     * close should be called after the context which is writing logs is
     * destroyed or logs are no longer going to be written.
     * 
     * @param context
     */
    public static synchronized void close(Context context) {
        writers.remove(context);
        if (writers.isEmpty() && probeWriter != null) {
            probeWriter.close();
            probeWriter = null;
        }
    }

    public static void setLevel(boolean logAnalytics, String logLevel) {
        setLevel(logAnalytics, Loglevel.valueOf(logLevel.toUpperCase(Locale.US)));
    }

    public static void setLevel(boolean logAnalytics, Loglevel logLevel) {
        LogProbe.logAnalytics = logAnalytics;
        LogProbe.logLevel = logLevel;
    }

    /**
     * If the deviceId is logged, your application should hold the
     * {@link permission#READ_PHONE_STATE} permission.
     * 
     * @param log
     */
    public static void setLogDeviceId(boolean log) {
        logDeviceId = log;
    }
}
