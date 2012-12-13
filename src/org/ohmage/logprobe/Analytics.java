
package org.ohmage.logprobe;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.view.View;

import org.apache.http.client.methods.HttpPost;
import org.ohmage.logprobe.LogProbe.Status;

import java.net.URI;

/**
 * Writes analytics messages
 * 
 * @author cketcham
 */
public class Analytics {

    /**
     * Log information about if an activity is being shown or hidden. This call
     * should be made from {@link Activity#onPause()} and
     * {@link Activity#onResume()}
     * 
     * @param activity
     * @param status
     */
    public static void activity(Context activity, Status status) {
        if (LogProbe.logAnalytics)
            LogProbe.probeWriter.activity(activity, status);
    }

    /**
     * Log information about a view being interacted with
     * 
     * @param view
     * @param name Human readable name for widget
     * @param extra extra info for widget
     */
    public static void widget(View view, String name, String extra) {
        if (LogProbe.logAnalytics)
            LogProbe.probeWriter.widget(view, name, extra);
    }

    /**
     * Log information about a view being interacted with
     * 
     * @param view
     */
    public static void widget(View view) {
        if (LogProbe.logAnalytics)
            widget(view, null, null);
    }

    /**
     * Log information about a view being interacted with
     * 
     * @param view
     * @param name
     */
    public static void widget(View view, String name) {
        if (LogProbe.logAnalytics)
            widget(view, name, null);
    }

    /**
     * For the case that we want to log some widget action but don't have access
     * to the view
     * 
     * @param context
     * @param string
     */
    public static void widget(Context context, String name) {
        if (LogProbe.logAnalytics)
            LogProbe.probeWriter.widget(-1, name, null);
    }

    /**
     * Log information about if a service is being started or stopped. This call
     * should be made from {@link Service#onCreate()} and
     * {@link Service#onDestroy()}
     * 
     * @param service
     * @param status
     */
    public static void service(Service service, Status status) {
        if (LogProbe.logAnalytics)
            LogProbe.probeWriter.service(service, status);
    }

    /**
     * Log network traffic
     * 
     * @param context
     * @param resource
     * @param networkState
     * @param length
     */
    private static void network(Context context, String resource, String networkState, long length) {
        if (LogProbe.logAnalytics)
            LogProbe.probeWriter.network(context, resource, networkState, length);
    }

    /**
     * Log information about network traffic uploads
     * 
     * @param context
     * @param httpPost
     */
    public static void network(Context context, HttpPost httpPost) {
        if (LogProbe.logAnalytics)
            network(context, httpPost.getURI().getPath(), "upload", httpPost.getEntity()
                    .getContentLength());
    }

    /**
     * Log information about network traffic downloads
     * 
     * @param context
     * @param url
     * @param length
     */
    public static void network(Context context, String url, long length) {
        if (LogProbe.logAnalytics)
            network(context, URI.create(url).getPath(), "download", length);
    }
}
