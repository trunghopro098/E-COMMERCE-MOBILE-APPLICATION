package com.sict.udn.myshoes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {
    private static int SPALASH_SCREEEN = 4000;
    //variable
    Animation topAnim, bottomAnim;
    ImageView image;
    TextView welcome,bran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        //ANimotion
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animotion);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animotion);

        image = findViewById(R.id.img_splash);
        welcome = findViewById(R.id.textView);
        bran = findViewById(R.id.textView2);

        image.setAnimation(topAnim);
        welcome.setAnimation(bottomAnim);
        bran.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },SPALASH_SCREEEN);
    }
}
