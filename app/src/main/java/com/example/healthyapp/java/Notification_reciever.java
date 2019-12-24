package com.example.healthyapp.java;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.example.healthyapp.R;
import com.example.healthyapp.pojo.FirstAid;
import com.example.healthyapp.ui.FirstAid.FirstAidDetailesActivity;
import com.example.healthyapp.ui.FirstAid.FirstAidsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.paperdb.Paper;

public class Notification_reciever extends BroadcastReceiver {

    FirebaseFirestore firebaseFirestore;
    String lang;
    @Override
    public void onReceive(final Context context, final Intent intent) {

        Paper.init(context);
        lang=Paper.book().read("language","en");
        firebaseFirestore=FirebaseFirestore.getInstance();

        final NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        //الصفحة الي هيروحلها لما يدوس على اللنوتيفيكاشن
        Intent repeating_intent=new Intent(context, FirstAidDetailesActivity.class);
        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent=PendingIntent.getActivity(context,100,repeating_intent,PendingIntent.FLAG_UPDATE_CURRENT);

        firebaseFirestore.collection("FirstAids").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    // بحول الداتا الي جيه من الفير بيز لي ليست من firstAids
                    List<FirstAid> firstAids=new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        firstAids.add(document.toObject(FirstAid.class));
                    }
                    if (firstAids.size()>0){

                        Random rand = new Random();
                        FirstAid firstAid=firstAids.get(rand.nextInt(firstAids.size()));

                        Intent repeating_intent=new Intent(context, FirstAidDetailesActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        repeating_intent.putExtra("name",firstAid.getEn_name());
                        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        PendingIntent pendingIntent=PendingIntent.getActivity(context,100,repeating_intent,PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);
                        String name=firstAid.getEn_name();
                        String body=firstAid.getEn_body();
                        if (lang.equals("ar")){
                            name=firstAid.getAr_name();
                            body=firstAid.getAr_body();
                        }
                        NotificationCompat.Builder builder=new NotificationCompat.Builder(context)
                                .setContentIntent(pendingIntent)
                                .setSmallIcon(R.drawable.logo)
                                .setContentTitle(name)
                                .setContentText(body)
                                .setAutoCancel(true);

                        notificationManager.notify(100,builder.build());
                    }else {
                        Intent repeating_intent=new Intent(context, FirstAidsActivity.class);
                        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        PendingIntent pendingIntent=PendingIntent.getActivity(context,100,repeating_intent,PendingIntent.FLAG_UPDATE_CURRENT);
                        NotificationCompat.Builder builder=new NotificationCompat.Builder(context)
                                .setContentIntent(pendingIntent)
                                .setSmallIcon(R.drawable.logo)
                                .setContentTitle("HealthApp")
                                .setContentText("Don't Forget Check FirstAids")
                                .setAutoCancel(true);

                        notificationManager.notify(100,builder.build());
                    }
                }else{
                    Intent repeating_intent=new Intent(context, FirstAidsActivity.class);
                    repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    PendingIntent pendingIntent=PendingIntent.getActivity(context,100,repeating_intent,PendingIntent.FLAG_UPDATE_CURRENT);
                    NotificationCompat.Builder builder=new NotificationCompat.Builder(context)
                            .setContentIntent(pendingIntent)
                            .setSmallIcon(R.drawable.logo)
                            .setContentTitle("HealthApp")
                            .setContentText("Don't Forget Check FirstAids")
                            .setAutoCancel(true);

                    notificationManager.notify(100,builder.build());
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Intent repeating_intent=new Intent(context, FirstAidsActivity.class);
                repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent=PendingIntent.getActivity(context,100,repeating_intent,PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Builder builder=new NotificationCompat.Builder(context)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("HealthApp")
                        .setContentText("Don't Forget Check FirstAids")
                        .setAutoCancel(true);

                notificationManager.notify(100,builder.build());
            }
        });

       /* NotificationCompat.Builder builder=new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(android.R.drawable.arrow_up_float)
                .setContentTitle("Notification title")
                .setContentText("Notification text")
                .setAutoCancel(true);

        notificationManager.notify(100,builder.build());*/

    }
}
