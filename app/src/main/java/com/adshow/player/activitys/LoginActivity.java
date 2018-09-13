package com.adshow.player.activitys;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.adshow.player.R;

public class LoginActivity extends AppCompatActivity {

    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Button btn = (Button) v;
                int focus = hasFocus ? R.anim.enlarge : R.anim.decrease;
                Animation mAnimation = AnimationUtils.loadAnimation(getApplication(), focus);
                mAnimation.setBackgroundColor(Color.TRANSPARENT);
                mAnimation.setFillAfter(hasFocus);
                btn.startAnimation(mAnimation);
                mAnimation.start();
                btn.setBackgroundColor(hasFocus ? Color.parseColor("#cc0094ff") : Color.parseColor("#2299ee"));
            }
        });
    }
}
