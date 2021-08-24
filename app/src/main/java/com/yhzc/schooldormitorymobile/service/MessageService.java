package com.yhzc.schooldormitorymobile.service;

import static java.util.concurrent.Executors.newScheduledThreadPool;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.Vibrator;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.StringUtils;
import com.yhzc.schooldormitorymobile.R;
import com.yhzc.schooldormitorymobile.ui.meeting.list.MeetingListActivity;
import com.yhzc.schooldormitorymobile.utils.Cache;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author: Procyonlotor
 * @创建日期: 2021/7/14 14:20
 * @描述: 消息推送
 */
public class MessageService extends Service {

    private boolean pushthread = false;
    private static final int REFRESH_TIME = 60000;
    private static final int PERIOD = 10 * 1000;
    private static final int DELAY = 100;
    private static final int NOTIFICATION_SHOW_SHOW_AT_MOST = 100;
    private int mNotificationNum = 1;
    private static final String METHOD = "messagePush";
    private ScheduledExecutorService mScheduledExecutorService;
    private WebSocketClient mWebSocketClient;
    private NotificationManager mNotificationManager;
    private Notification mNotification;


    public MessageService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getStringExtra("flags") != null && !StringUtils.isEmpty(intent.getStringExtra("flags")) && "3".equals(intent.getStringExtra("flags"))) {
            initNotification();
            initWebSocket();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void getHttp() {
        startPolling();
    }

    private void startPolling() {
        mScheduledExecutorService = newScheduledThreadPool(1);
        mScheduledExecutorService.scheduleAtFixedRate(() -> {

        }, 0, 3, TimeUnit.SECONDS);

    }

    private void stopPolling() {
        if (null != mScheduledExecutorService) {
            mScheduledExecutorService.shutdownNow();
        }
    }


    private void initWebSocket() {
        URI serverURI = URI.create("ws://39.99.169.115:7771/websocket");
        mWebSocketClient = new WebSocketClient(serverURI) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
            }

            @Override
            public void onMessage(String message) {
                PushModel pushModel = GsonUtils.fromJson(message, PushModel.class);
                if (!StringUtils.isEmpty(pushModel.getMethod()) && StringUtils.equals(METHOD, pushModel.getMethod())) {
                    mNotificationNum++;
                    if (mNotificationNum > NOTIFICATION_SHOW_SHOW_AT_MOST) {
                        mNotificationNum = 1;
                    }
                    mNotificationManager.notify(mNotificationNum, mNotification);
                }
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                if (mWebSocketClient != null && mWebSocketClient.isClosed()) {
                    try {
                        mWebSocketClient.reconnectBlocking();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(Exception ex) {
                if (mWebSocketClient != null && mWebSocketClient.isClosed()) {
                    try {
                        mWebSocketClient.reconnectBlocking();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        try {
            mWebSocketClient.connectBlocking();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Map<String, Object> map = new HashMap<>();
        map.put("method", "OnConnection");
        map.put("token", Cache.getToken());
        map.put("mobileDevice", false);

        mWebSocketClient.send(GsonUtils.toJson(map));


    }

    private void initNotification() {
        String id = "message";//channel的id
        Intent intent = new Intent(ActivityUtils.getTopActivity(), MeetingListActivity.class);
        PendingIntent pi = PendingIntent.getActivity(ActivityUtils.getTopActivity(), 0, intent, 0);
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT > 26) {
            String description = "123";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(id, "123", importance);
            channel.setDescription(description);
            mNotificationManager.createNotificationChannel(channel);
        }
        mNotification = new NotificationCompat.Builder(this, id)
                .setAutoCancel(true)
                .setContentTitle("会议通知")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_logo)
                .setContentIntent(pi)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setVibrate(new long[]{0, 1000, 1000, 1000})
                .setLights(Color.GREEN, 1000, 1000)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .build();

    }

    @Override
    public void onDestroy() {
        pushthread = false;
        stopPolling();
        super.onDestroy();
    }

    public static void getConnet(Context mContext) {
        try {
            Intent intent = new Intent(mContext, MessageService.class);
            intent.putExtra("flags", "3");
            int currentapiVersion = Build.VERSION.SDK_INT;
            if (currentapiVersion > 20) {
                mContext.startService(intent);
            } else {
                PendingIntent pIntent = PendingIntent.getService(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), REFRESH_TIME, pIntent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stop(Context mContext) {
        Intent intent = new Intent(mContext, MessageService.class);
        PendingIntent pIntent = PendingIntent.getService(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pIntent);
    }

    private static void playNotificationRing(Context context) {
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone rt = RingtoneManager.getRingtone(context, uri);
        rt.play();
    }

    private static void playNotificationVibrate(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        long[] vibrationPattern = new long[]{0, 180, 80, 120};
        vibrator.vibrate(vibrationPattern, -1);
    }


}
