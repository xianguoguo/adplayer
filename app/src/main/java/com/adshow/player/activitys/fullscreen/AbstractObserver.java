package com.adshow.player.activitys.fullscreen;

import android.content.Context;
import android.os.Bundle;

public abstract class AbstractObserver {

    abstract void onCreate(Bundle savedInstanceState);

    abstract void onStart();

    abstract void onResume();

    abstract void onPause();

    abstract void onStop();

    abstract void onDestroy();

}
