package com.example.shefaliupadhyaya.project1;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageButton playnow,exit,sound,info;
    MediaPlayer bckgrnd;
    TextView tv;
    private int flag1=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bckgrnd = MediaPlayer.create(MainActivity.this, R.raw.dualdragon);
        bckgrnd.setLooping(true);
        bckgrnd.start();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);
        tv = (TextView)findViewById(R.id.textView3);
        try {
            tv.setVisibility(View.GONE);
        }
        catch(Exception e){}
        info1();
        sound = (ImageButton)findViewById(R.id.sound);
        sound.setBackgroundResource(R.drawable.ic_volume_up_white_24dp);
        //final ImageButton finalSound = sound;
        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag1 == 0) {
                    bckgrnd.pause();
                    sound.setBackgroundResource(R.drawable.ic_volume_off_white_24dp);
                    flag1 = 1;
                } else if (flag1 == 1) {
                    bckgrnd.start();
                    sound.setBackgroundResource(R.drawable.ic_volume_up_white_24dp);
                    flag1 = 0;
                }
            }
        });
        playnow=(ImageButton)findViewById(R.id.buttonPlay);
        exit=(ImageButton)findViewById(R.id.buttonScore);
        playnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent("com.example.shefaliupadhyaya.project1.GameActivity");
                startActivity(intent);
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity(); //works on Android 4.1 or higher
                System.exit(0);
                //android.os.Process.killProcess(android.os.Process.myPid());
            }
        });

    }
    /*to make it invisible by changing its opacity
  msg.setalpha(0.0f);

  and to make it visible by changing its opacity
  msg.setalpha(1.0f);*/
    void info1(){
        info=(ImageButton)findViewById(R.id.info);
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv.getVisibility() == TextView.GONE)
                    tv.setVisibility(View.VISIBLE);
                else
                    tv.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    protected void onPause() {
        super.onPause();
        bckgrnd.release();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
