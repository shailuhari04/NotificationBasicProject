package com.sdrss.notificationbasicproject;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    /**
     * 3 Steps To Create Notification
     * 1-> Notification Channel
     * 2-> Notification Builder
     * 3-> Notification Manager
     */

    //create the channel
    public static final String CHANNEL_ID = "SDRSS";
    public static final String CHANNEL_TITLE = "SDRSS Welcome!!!";
    public static final String CHANNEL_DESCRIPTION = "Hello User....";


    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //for OREO Onwards Channel Define
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_TITLE, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESCRIPTION);

            NotificationManager manager = getSystemService(NotificationManager.class);

            manager.createNotificationChannel(channel);
        }

        textView = findViewById(R.id.textView);

        //FireBase Token
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {

                if (task.isSuccessful()){
                    textView.setText(Objects.requireNonNull(task.getResult()).getToken());
                }else {

                    textView.setText(Objects.requireNonNull(task.getException()).getMessage());
                }
            }
        });


    }

    /**
     * @param view
     * @OnClick ->  Basic Notification Create
     */
    public void createBasicNotification(View view) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_article_color)
                .setContentTitle(CHANNEL_TITLE)
                .setContentText(CHANNEL_DESCRIPTION)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);

        manager.notify(1, builder.build());
    }
}
