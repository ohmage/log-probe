
package org.ohmage.logprobe;

import android.app.Service;
import android.content.Context;
import android.os.RemoteException;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;
import org.ohmage.logprobe.LogProbe.Status;
import org.ohmage.probemanager.ProbeBuilder;
import org.ohmage.probemanager.ProbeWriter;

/**
 * This probe writer sends systemlog data
 * 
 * @author cketcham
 */
public class LogProbeWriter extends ProbeWriter {

    private static final String OBSERVER_ID = "org.ohmage.LogProbe";
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

    public LogProbeWriter(Context context) {
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
            probe.withId().now();

            probe.write(this);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void activity(Context activity, Status status) {
        try {
            ProbeBuilder probe = new ProbeBuilder(OBSERVER_ID, OBSERVER_VERSION);
            probe.setStream(STREAM_ACTIVITY, STREAM_ACTIVITY_VERSION);

            JSONObject data = new JSONObject();
            data.put("activity", activity.getClass().getSimpleName());
            data.put("status", status.name());
            probe.setData(data.toString());
            probe.withId().now();

            probe.write(this);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void widget(View view, String name, String extra) {
        widget(view.getId(), (name != null) ? name : view.getContentDescription(), extra);
    }

    public void widget(int id, CharSequence name, String extra) {
        try {
            ProbeBuilder probe = new ProbeBuilder(OBSERVER_ID, OBSERVER_VERSION);
            probe.setStream(STREAM_WIDGET, STREAM_WIDGET_VERSION);

            JSONObject data = new JSONObject();
            data.put("id", id);
            data.put("name", (name != null) ? name : "None");
            data.put("extra", extra);
            probe.setData(data.toString());
            probe.withId().now();

            probe.write(this);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void service(Service service, Status status) {
        try {
            ProbeBuilder probe = new ProbeBuilder(OBSERVER_ID, OBSERVER_VERSION);
            probe.setStream(STREAM_SERVICE, STREAM_SERVICE_VERSION);

            JSONObject data = new JSONObject();
            data.put("service", service.getClass().getSimpleName());
            data.put("status", status.name());
            probe.setData(data.toString());
            probe.withId().now();

            probe.write(this);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void network(Context context, String resource, String networkState, long length) {
        try {
            ProbeBuilder probe = new ProbeBuilder(OBSERVER_ID, OBSERVER_VERSION);
            probe.setStream(STREAM_NETWORK, STREAM_NETWORK_VERSION);

            JSONObject data = new JSONObject();
            data.put("context", context.getClass().getSimpleName());
            data.put("resource", resource);
            data.put("network_state", networkState);
            data.put("length", length);
            probe.setData(data.toString());
            probe.withId().now();

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
