package com.prateek.flickr.flickrphotos;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.prateek.flickr.flickrphotos.model.FlickrResponse;
import com.prateek.flickr.flickrphotos.network.FlickrClient;
import com.prateek.flickr.flickrphotos.network.FlickrService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PollService extends JobService {

    FlickrService retrofit = FlickrClient.getClient().create(FlickrService.class);
    Call<FlickrResponse> call = retrofit.getRecentPhotos();

    @Override
    public boolean onStartJob(final JobParameters params) {
        Log.d("HERE123", "Onstart");


        call.enqueue(new Callback<FlickrResponse>() {


            @Override
            public void onResponse(Call<FlickrResponse> call, Response<FlickrResponse> response) {

                FlickrResponse flickrResponse = response.body();
                String id = QueryPrefrences.getLastResultId(getApplicationContext());

                if (id != null) {

                    if (!id.equals(flickrResponse.getPhotos().getPhoto().get(0).getId())) {
                        Log.d("HERE123", "New Post Avalible");

                        Notification notification = new NotificationCompat.Builder(getApplicationContext(), "flickr").setTicker("Notification")
                                .setSmallIcon(R.drawable.ic_launcher_background)
                                .setContentTitle("New Photos Avalible").setAutoCancel(true).build();

                        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            // only for gingerbread and newer versions
                            NotificationChannel channel = new NotificationChannel("flickr", "FlickrChannel", NotificationManager.IMPORTANCE_HIGH);
                            NotificationManager notificationManager = getSystemService(NotificationManager.class);
                            notificationManager.createNotificationChannel(channel);
                            notificationManager.notify(0, notification);
                        } else {
                            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());
                            managerCompat.notify(0, notification);
                        }
                    } else {
                        Log.d("HERE123", "Nothing new");
                    }
                    jobFinished(params, false);
                }

            }

            @Override
            public void onFailure(Call<FlickrResponse> call, Throwable t) {
                Log.d("HERE123", t.getMessage());

                jobFinished(params, true);
            }
        });
        return true;//return true for long running background task bcz it runs on main thread
    }


    //called when job cancelled before completion by system
    @Override
    public boolean onStopJob(JobParameters params) {
        call.cancel();
        Log.d("HERE123", "Job cancelled" + params.toString());

        return true;//return true if job is important and must be done and needs to be rescheduled later
    }
}
