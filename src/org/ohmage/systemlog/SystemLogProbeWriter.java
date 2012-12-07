
package org.ohmage.systemlog;

import android.content.Context;
import android.os.RemoteException;

import org.json.JSONException;
import org.json.JSONObject;
import org.ohmage.probemanager.ProbeBuilder;
import org.ohmage.probemanager.ProbeWriter;

/**
 * This probe writer sends systemlog data
 * 
 * @author cketcham
 */
public class SystemLogProbeWriter extends ProbeWriter {

    private static final String OBSERVER_ID = "org.ohmage.SystemLog";
    private static final int OBSERVER_VERSION = 1;

    private static final String STREAM_LOG = "log";
    private static final int STREAM_LOG_VERSION = 1;

    private static final String STREAM_ACTIVITY = "activity";
    private static final int STREAM_ACTIVITY_VERSION = 1;

    private static final String STREAM_WIDGET = "widget";
    private static final int STREAM_WIDGET_VERSION = 1;

    private static final String STREAM_SERVICE = "service";
    private static final int STREAM_SERVICE_VERSION = 1;

    private static final String STREAM_NETWORK = "network";
    private static final int STREAM_NETWORK_VERSION = 1;

    public SystemLogProbeWriter(Context context) {
        super(context);
    }

    /**
     * Log a message to SystemLog
     * 
     * @param level
     * @param tag
     * @param message
     */
    public void log(String level, String tag, String message) {
        try {
            ProbeBuilder probe = new ProbeBuilder(OBSERVER_ID, OBSERVER_VERSION);
            probe.setStream(STREAM_LOG, STREAM_LOG_VERSION);

            JSONObject data = new JSONObject();
            data.put("level", level);
            data.put("tag", tag);
            data.put("message", message);
            probe.setData(data.toString());
            probe.write(this);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
