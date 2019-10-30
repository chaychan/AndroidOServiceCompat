package com.chaychan.androidoservicecompat;

import android.content.Intent;
import android.os.IBinder;

import com.chaychan.aosc.ServiceCompat;

/**
 * @author chaychan
 * @description: RecordAudioService
 * @date 2019/8/21  15:12
 */
public class RecordAudioService extends ServiceCompat {

    @Override
    protected String getChannelName() {
        return "正在使用录音";
    }

    @Override
    protected int getChannelId() {
        return Constants.CHANNEL_ID_RECORD_AUDIO_SERVICE;
    }

    @Override
    public String getNotificationContent() {
        return "录音中...";
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        //must call super.onCreate()
        super.onCreate();

        //do something
    }
}
