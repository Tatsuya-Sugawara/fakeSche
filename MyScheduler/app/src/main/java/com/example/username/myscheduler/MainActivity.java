package com.example.username.myscheduler;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private Realm mRealm;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 19) {
            View decor = this.getWindow().getDecorView();
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        } else {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_main);

        FloatingActionButton fab =
                (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,
                        ScheduleEditActivity.class));
            }
        });
        mRealm = Realm.getDefaultInstance();

        mListView = (ListView) findViewById(R.id.listView);
        RealmResults<Schedule> schedules =
                mRealm.where(Schedule.class).findAll();
        ScheduleAdapter adapter = new ScheduleAdapter(schedules);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Schedule schedule =
                                (Schedule) parent.getItemAtPosition(position);
                        startActivity(new Intent(MainActivity.this,
                                ScheduleEditActivity.class)
                                .putExtra("schedule_id", schedule.getId()));
                    }
                });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}
