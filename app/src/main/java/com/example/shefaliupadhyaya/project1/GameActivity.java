package com.example.shefaliupadhyaya.project1;

import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;


public class GameActivity extends AppCompatActivity {
    //declaring gameview
    public GameView gameView;
    private MediaPlayer bckgrndmusic;
    private int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        bckgrndmusic=MediaPlayer.create(GameActivity.this,R.raw.skywanderer);
        bckgrndmusic.setLooping(true);
        bckgrndmusic.start();
            //Getting display object
            Display display = getWindowManager().getDefaultDisplay();
            //Getting the screen resolution into point object
            Point size = new Point();
            display.getSize(size);
            //Initializing game view object
            //this time we are also passing the screen size to the GameView constructor
        FrameLayout game = new FrameLayout(this);
        LinearLayout gameWidgets = new LinearLayout(this);
        final ImageButton pause= new ImageButton(this);
        pause.setBackgroundResource(R.drawable.ic_pause_white_24dp);
        gameView = new GameView(this, size.x, size.y);
        gameWidgets.addView(pause);
        game.addView(gameView);
        game.addView(gameWidgets);
        //adding it to content view
        setContentView(game);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag == 0) {
                    gameView.pause1();
                    pause.setBackgroundResource(R.drawable.ic_play_arrow_white_24dp);
                    flag = 1;
                } else if (flag == 1) {
                    gameView.resume();
                    pause.setBackgroundResource(R.drawable.ic_pause_white_24dp);
                    flag = 0;
                }
            }
        });

    }
    //pausing the game when activity is paused
    @Override
    protected void onPause() {
        super.onPause();
        bckgrndmusic.release();
        gameView.pause1();
    }
    //running the game when activity is resumed
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }
}
