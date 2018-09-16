package com.prateek.flickr.flickrphotos;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.prateek.flickr.flickrphotos.model.FlickrResponse;
import com.prateek.flickr.flickrphotos.model.Photo;
import com.prateek.flickr.flickrphotos.network.FlickrClient;
import com.prateek.flickr.flickrphotos.network.FlickrService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL);
        recyclerView.addItemDecoration(decoration);
        initAdapter();


        FlickrService retrofit = FlickrClient.getClient().create(FlickrService.class);

        Call<FlickrResponse> call=retrofit.getRecentPhotos();
        call.enqueue(new Callback<FlickrResponse>() {
            @Override
            public void onResponse(Call<FlickrResponse> call, Response<FlickrResponse> response) {
                FlickrResponse response1 = response.body();
                List<Photo> photos = response1.getPhotos().getPhoto();
                if (photos != null) {
                    QueryPrefrences.setPrefLastResultId(getApplicationContext(), photos.get(0).id);
                    adapter.submitList(photos);
                }


            }

            @Override
            public void onFailure(Call<FlickrResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "NETWORK ERROR", Toast.LENGTH_SHORT).show();

            }
        });

        setupJobScheduler();

    }

    private void setupJobScheduler() {

        if(!QueryPrefrences.getJobSchedulerStatus(this)) {

            ComponentName componentName = new ComponentName(this, PollService.class);
            JobInfo jobInfo = new JobInfo.Builder(1, componentName)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .setPersisted(true).setPeriodic(15*60*1000).build();

            JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
            int result = scheduler.schedule(jobInfo);
            if (result == JobScheduler.RESULT_SUCCESS) {
                QueryPrefrences.setJobSchedulerStatus(this, true);
                Log.d("HERE123", "job Scheduled");
            }
        }


    }

    private void initAdapter() {

        adapter = new ImageAdapter();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter);
    }
}
