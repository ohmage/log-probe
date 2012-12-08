
package org.ohmage.systemlog;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.Context;
import android.view.View;

import org.apache.http.client.methods.HttpPost;

import java.net.URI;

/**
 * Creates a probewriter to send data to ohmage
 * 
 * @author cketcham
 */
public class SystemLog extends Application {

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
    private static boolean mLogAnalytics;
    private static Loglevel mLogLevel;

    public SystemLog(boolean logAnalytics, String logLevel) {
        mLogAnalytics = logAnalytics;
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

    /**
     * Log information about if an activity is being shown or hidden. This call
     * should be made from {@link Activity#onPause()} and
     * {@link Activity#onResume()}
     * 
     * @param activity
     * @param status
     */
    public static void activity(Context activity, Status status) {
        if (mLogAnalytics)
            probeWriter.activity(activity, status);
    }

    /**
     * Log information about a view being interacted with
     * 
     * @param view
     * @param name Human readable name for widget
     * @param extra extra info for widget
     */
    public static void widget(View view, String name, String extra) {
        if (mLogAnalytics)
            probeWriter.widget(view, name, extra);
    }

    /**
     * Log information about a view being interacted with
     * 
     * @param view
     */
    public static void widget(View view) {
        if (mLogAnalytics)
            widget(view, null, null);
    }

    /**
     * Log information about a view being interacted with
     * 
     * @param view
     * @param name
     */
    public static void widget(View view, String name) {
        if (mLogAnalytics)
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
        if (mLogAnalytics)
            probeWriter.widget(-1, name, null);
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
        if (mLogAnalytics)
            probeWriter.service(service, status);
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
        if (mLogAnalytics)
            probeWriter.network(context, resource, networkState, length);
    }

    /**
     * Log information about network traffic uploads
     * 
     * @param context
     * @param httpPost
     */
    public static void network(Context context, HttpPost httpPost) {
        if (mLogAnalytics)
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
        if (mLogAnalytics)
            network(context, URI.create(url).getPath(), "download", length);
    }
}
