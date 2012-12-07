
package org.ohmage.systemlog;

import android.app.Application;
import android.content.Context;

/**
 * Creates a probewriter to send data to ohmage
 * 
 * @author cketcham
 */
public class SystemLog extends Application {

    public static SystemLogProbeWriter probeWriter;

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

    public static void i(String tag, String message) {
        probeWriter.log("info", tag, message);
    }

    public static void d(String tag, String message) {
        probeWriter.log("debug", tag, message);
    }

    public static void e(String tag, String message, Exception e) {
        probeWriter.log("error", tag, message + e.getMessage());
    }

    public static void e(String tag, String message) {
        probeWriter.log("error", tag, message);
    }

    public static void v(String tag, String message) {
        probeWriter.log("verbose", tag, message);
    }

    public static void w(String tag, String message) {
        probeWriter.log("warning", tag, message);
    }
}
