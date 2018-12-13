package com.syb.splityourbill;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                nextActivity();
            }
        }, 400);

    }

    void nextActivity(){
        SharedPreferences sharedpreferences = getBaseContext().getSharedPreferences("UserDetail", Context.MODE_PRIVATE);
        if(sharedpreferences.contains("email")){
            Intent intent = new Intent(this,HomeActivity.class);
            intent.putExtra("email",sharedpreferences.getString("email","no_email"));
            startActivity(intent);
            finish();
        }
        else{
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
        }
    }
}
