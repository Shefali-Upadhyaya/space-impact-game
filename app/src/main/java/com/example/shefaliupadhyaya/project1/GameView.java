package com.example.shefaliupadhyaya.project1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Shefali Upadhyaya on 11-07-2016.
 */
public class GameView extends SurfaceView implements Runnable {
    //boolean variable to track if the game is playing or not
    volatile boolean playing;
     private int k=0;
    //the game thread
    private Thread gameThread = null;
    //adding the player to this class
    private Player player;
    //These objects will be used for drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    //Class constructor
    //Adding an stars list
    private ArrayList<Star> stars = new
            ArrayList<Star>();
    //Adding enemies object array
    private Enemy[] enemies;
    //Adding 3 enemies you may increase the size
    private static int enemyCount = 3;
    //defining a boom object to display blast
    private Boom boom;
    private static int score=0;
    /*The SoundPool class manages and plays audio resources for applications.
    A SoundPool is a collection of samples that can be loaded into memory from a resource inside the APK
    or from a file in the file system.*/
    private SoundPool sound;
    private int whoop;
    public GameView(Context context, int screenX, int screenY) {
        super(context);
        //initializing player object
        //this time also passing screen size to player constructor
        player = new Player(context, screenX, screenY);

        //initializing drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();

        //adding 100 stars you may increase the number
        int starNums = 100;
        for (int i = 0; i < starNums; i++) {
            Star s  = new Star(screenX, screenY);
            stars.add(s);
        }
        //initializing enemy object array
        enemies = new Enemy[enemyCount];
        for(int i=0; i<enemyCount; i++){
            enemies[i] = new Enemy(context, screenX, screenY);
        }
        //initializing boom object
        boom = new Boom(context);
        /*SoundPool (int maxStreams, int streamType, int srcQuality)
            maxStreams	int: the maximum number of simultaneous streams for this SoundPool object
             streamType	int: the audio stream type as described in AudioManager For example, game applications will normally use STREAM_MUSIC.
             srcQuality	int: the sample-rate converter quality. Currently has no effect. Use 0 for the default.
            int	STREAM_MUSIC-->The audio stream for music playback*/
        sound=new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        //int	load(Context context, int resId, int priority (1 for no effect),Load the sound from the specified APK resource.
        whoop=sound.load(context,R.raw.whoosh,1);
    }
    @Override
    public void run() {
        while (playing) {
            //to update the frame
            update();
            //to draw the frame
            draw();
            //to control the frames drawn per second
            control();

        }
    }

    private void update() {
        Random generator=new Random();
        //updating player position
        player.update();
        //Updating the stars with player speed
        //setting boom outside the screen
        boom.setX(-250);
        boom.setY(-250);
        for (Star s : stars) {
            s.update(player.getSpeed());
        }
        //updating the enemy coordinate with respect to player speed
        for(int i=0; i<enemyCount; i++) {
            enemies[i].update(player.getSpeed());
            k=generator.nextInt(300);
            if (Rect.intersects(player.getDetectCollision(), enemies[i].getDetectCollision())) {
                //moving enemy outside the left edge
                //displaying boom at that location
                boom.setX(enemies[i].getX());
                boom.setY(enemies[i].getY());
                /*int play (int soundID,float leftVolume,float rightVolume,int priority,int loop,float rate)
                soundID	int: a soundID returned by the load() function
                leftVolume	float: left volume value (range = 0.0 to 1.0)
                rightVolume	float: right volume value (range = 0.0 to 1.0)
                priority	int: stream priority (0 = lowest priority)
                loop	int: loop mode (0 = no loop, -1 = loop forever)
                rate	float: playback rate (1.0 = normal playback, range 0.5 to 2.0)*/
                sound.play(whoop,1f,1f,0,0,1.5f);
                enemies[i].setXY(1000,k);
                score+=5;
            }
            else if(!Rect.intersects(player.getDetectCollision(), enemies[i].getDetectCollision()) & Enemy.ecount==5) {

                        Intent intent = new Intent().setClass(getContext(), Highscore.class);
                                ((Activity) getContext()).startActivityForResult(intent, 0);

            }
        }

    }

    public static int getScore() {
        return score;
    }

    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            canvas.drawColor(Color.BLACK);
            //setting the paint color to white to draw the stars
            paint.setColor(Color.WHITE);
            //drawing all stars
            for (Star s : stars) {
                paint.setStrokeWidth(s.getStarWidth());
                canvas.drawPoint(s.getX(), s.getY(), paint);
            }

            canvas.drawBitmap(
                    player.getBitmap(),
                    player.getX(),
                    player.getY(),
                    paint);
            //drawing the enemies
            for (int i = 0; i < enemyCount; i++) {
                canvas.drawBitmap(enemies[i].getBitmap(), enemies[i].getX(), enemies[i].getY(), paint);
            }
            //drawing boom image
            canvas.drawBitmap(
                    boom.getBitmap(),
                    boom.getX(),
                    boom.getY(),
                    paint
            );
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause1() {
        //when the game is paused
        //setting the variable to false
        playing = false;
        try {
            //stopping the thread
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        //when the game is resumed
        //starting the thread again
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                //stopping the boosting when screen is released
                player.stopBoosting();
                break;
            case MotionEvent.ACTION_DOWN:
                //boosting the highscorewallpaper jet when screen is pressed
                player.setBoosting();
                break;
        }
        return true;
    }
}

