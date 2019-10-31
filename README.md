### AndroidOServiceCompat框架
这是一个针对安卓8.0对后台服务的限制，对Service做出了兼容的框架，使用AndroidOServiceCompat框架，可以让你的项目的Service更快更方便地兼容安卓8.0。

### 效果

![image](/img/1.gif)


### 如何使用

&emsp;&emsp;一、将项目中使用startService()启动服务的方式，更改为调用**ServiceCompat.startService(context,intent)**

&emsp;&emsp;二、将项目中继承Service的类改成继承**ServiceCompat**，如demo中的VideoUploadService:


```
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
```

&emsp;&emsp;其中需要注意的是**onCreate()** 方法中一定要要调用**super.onCreate()** 方法，因为针对安卓8.0的兼容就是在ServiceCompat类的onCreate()方法中做处理的：


```
public abstract class ServiceCompat extends Service {

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //适配安卓8.0
            String channelId = getChannelId() + "";
            String channelName = getChannelName();
            NotificationChannel channel = new NotificationChannel(channelId, channelName,
                    NotificationManager.IMPORTANCE_MIN);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);

            startForeground(getChannelId(), getNotification());
        }
    }
    
    ...
}
```

&emsp;&emsp;另外，还要实现**getChannelName()**、**getChannelId()**、**getNotificationContent()** 方法，这三个方法主要返回的是通知栏的一些属性，因为将服务改为前台服务展示需要传入一个Notification，这里已经在ServiceCompat封装好一个简单的Notification，只要返回channelName,channelId以及通知的显示内容即可。

&emsp;&emsp;当然，如果你想自定义通知的样式，比如修改通知的大图标largeIcon，通知的小图标smallIcon，这里默认都是使用ic_launcher，如果你想自己指定，只需要重写以下的方法：


```
    /**
     * Large icon for notification , subclasses can be overwritten and returned
     */
    public Bitmap getLargeIcon() {
        return BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
    }

    /**
     * Small icon for notification , subclasses can be overwritten and returned
     */
    public int getSmallIcon() {
        return R.mipmap.ic_launcher;
    }

```

&emsp;&emsp;如果你对通知栏的样式还有更多的要求，可以重写getNotification()方法，返回你自己创建的Notification对象


```
 /**
     * Displayed notifications, subclasses can be overwritten and returned
     */
    public Notification getNotification() {
        return createNormalNotification(getNotificationContent());
    }
```


框架中默认是返回基本的通知栏，通知栏的content使用的是app_name字段，大图标和小图标使用的ic_launcher

```
protected Notification createNormalNotification(String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, getChannelId() + "");
        if (TextUtils.isEmpty(content)) {
            return builder.build();
        }

        builder.setContentTitle(getString(R.string.app_name))
                .setContentText(content)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(getSmallIcon())
                .setLargeIcon(getLargeIcon())
                .build();

        return builder.build();
}
```



#### 导入方式

在项目根目录下的build.gradle中的allprojects{}中，添加jitpack仓库地址，如下：


```
allprojects {
    repositories {
        jcenter()
        maven { url 'https://jitpack.io' }//添加jitpack仓库地址
    }
}
```

打开app的module中的build.gradle，在dependencies{}中，添加依赖，如下：


```
dependencies {
		......
        compile 'com.github.chaychan:AndroidOServiceCompat:1.0.0'
}
```
