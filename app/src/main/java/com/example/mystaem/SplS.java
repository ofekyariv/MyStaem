package com.example.mystaem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

public class SplS extends AppCompatActivity {

        private ImageView view;
        private final int SPLASH_DISPLAY_LENGTH = 2000;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_spl_s);
            view=findViewById(R.id.imageView);

            MediaPlayer mediaPlayer = MediaPlayer.create(SplS.this, R.raw.bass);
            mediaPlayer.start();


            Animation animation = AnimationUtils.loadAnimation(this, R.anim.blink_anim);
            view.startAnimation(animation);

            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    /* Create an Intent that will start the Menu-Activity. */
                    Intent mainIntent = new Intent(SplS.this,Login.class);
                    SplS.this.startActivity(mainIntent);
                    SplS.this.finish();
                }
            }, SPLASH_DISPLAY_LENGTH);

        }
    }
