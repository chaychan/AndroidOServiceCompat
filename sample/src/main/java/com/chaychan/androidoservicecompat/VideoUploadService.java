package com.chaychan.androidoservicecompat;

import android.content.Intent;
import android.os.IBinder;

import com.chaychan.aosc.ServiceCompat;

/**
 * @author chaychan
 * @description: VideoUploadService
 * @date 2019/8/21  15:12
 */
public class VideoUploadService extends ServiceCompat {

    @Override
    protected String getChannelName() {
        return "视频上传通知";
    }

    @Override
    protected int getChannelId() {
        return Constants.CHANNEL_ID_VIDEO_UPLOAD_SERVICE;
    }

    @Override
    public String getNotificationContent() {
        return "视频上传中...";
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
