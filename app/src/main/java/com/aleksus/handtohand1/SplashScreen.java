package com.aleksus.handtohand1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

public class SplashScreen extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        LinearLayout linearAnim = (LinearLayout) findViewById(R.id.linearAnim);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.combo);
        linearAnim.startAnimation(anim);
        linearAnim.setOnClickListener(this);
    }

    public void onClick(View view) {
        Intent intent;
        intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}