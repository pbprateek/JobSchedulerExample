package com.prateek.flickr.flickrphotos;

import android.content.Context;
import android.preference.PreferenceManager;

public class QueryPrefrences {

    private static final String PREF_LAST_RESULT_ID="lastResultId";
    private static  final String PREF_JOB_SCHED="jobScheduler";

    public static String getLastResultId(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_LAST_RESULT_ID,null);
    }

    public static void setPrefLastResultId(Context context,String id){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(PREF_LAST_RESULT_ID,id).apply();
    }


    public static boolean getJobSchedulerStatus(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(PREF_JOB_SCHED,false);
    }

    public static void setJobSchedulerStatus(Context context,Boolean bool){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(PREF_JOB_SCHED,bool).apply();
    }
}
