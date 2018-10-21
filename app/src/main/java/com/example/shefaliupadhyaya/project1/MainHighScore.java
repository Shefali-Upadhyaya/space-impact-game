package com.example.shefaliupadhyaya.project1;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class MainHighScore extends Activity {
    ListView listView;
    ImageButton exit1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_high_score);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        listView=(ListView)findViewById(R.id.listView);
        exit1=(ImageButton)findViewById(R.id.imageButton2);
        DBHelper db = new DBHelper(getApplicationContext());
        List<Integer> list = db.getAllScores();
        if(list!=null) {
            ArrayAdapter<Integer> dataAdapter = new ArrayAdapter<>(this, R.layout.score_list, list);
            listView.setAdapter(dataAdapter);
        }
        else Toast.makeText(MainHighScore.this, "No scores enlisted.", Toast.LENGTH_SHORT).show();
        exit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity(); //works on Android 4.1 or higher
                System.exit(0);
                //android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_high_score, menu);
        return true;
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
