package com.example.memeshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Thread thread;
        {
            thread = new Thread() {
                public void run() {
                    try {
                        sleep(2500);
                    } catch (Exception e) {
                        e.printStackTrace();  //Mother of all errors
                    } finally {
                        Intent intent1 = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(intent1);
                    }

                }
            };
            thread.start();
        }
    }
    }
