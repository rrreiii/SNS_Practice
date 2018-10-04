package com.example.hirasawarei.sns_beta.account;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.hirasawarei.sns_beta.R;

public class SplashActivity extends AppCompatActivity {

    private static int TIME_CHANGE = 2500;
    TextView textView;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_splash);

        textView = (TextView) findViewById(R.id.splashTextView);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "helvetica-normal.ttf");
        textView.setTypeface(typeface);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, TIME_CHANGE);
    }
}
