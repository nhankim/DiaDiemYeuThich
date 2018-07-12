package com.example.administrator.diadiemyeuthich.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.administrator.diadiemyeuthich.R;
import com.example.administrator.diadiemyeuthich.Utils.DBUtils;

public class SplashActivity extends AppCompatActivity implements Animation.AnimationListener{

    LinearLayout layoutSplash;
    ImageView imgSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();


    }

    private void addControls() {
        imgSplash = findViewById(R.id.imgSplash);
        layoutSplash = findViewById(R.id.layoutSplash);

        Animation alpha = AnimationUtils.loadAnimation(this,R.anim.alpha_background);
        Animation transition = AnimationUtils.loadAnimation(this,R.anim.transition_icon);
        imgSplash.setAnimation(transition);
        layoutSplash.setAnimation(alpha);
        alpha.setAnimationListener(this);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,CategoryActivity.class));
                finish();
            }
        }, 2000);

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
