package com.example.ft_hangouts;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Calendar;

public class App extends Application implements Application.ActivityLifecycleCallbacks{

    private int activityReferences = 0;
    Calendar lastTime;

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

        if (++activityReferences == 1) {
            // App enters foreground
            if(lastTime != null)
                Toast.makeText(activity, String.valueOf(lastTime.get(Calendar.HOUR) + ":" + lastTime.get(Calendar.MINUTE)) , Toast.LENGTH_SHORT).show();
        }
//        Log.v(this.getClass().getName(), "References : " + activityReferences);

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        if (--activityReferences == 0) {
            // App enters background
            lastTime = Calendar.getInstance();
        }
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }
}