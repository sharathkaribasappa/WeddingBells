package com.andriod.weddingbells.cardlayoutfunctionality;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.Toast;


import com.andriod.weddingbells.Adapters.MainEventListObjectNew;
import com.andriod.weddingbells.Adapters.MainListEvents_CustomAdapter;
import com.andriod.weddingbells.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ListEventsActivityRedone extends AppCompatActivity implements View.OnClickListener{

    private ListView mListView;
    private MainListEvents_CustomAdapter mAdapter;
    private FloatingActionButton fab;
    private FloatingActionButton mainEventFabSelector;
    private FloatingActionButton subEventFabSelector;
    private boolean isFabOpen = false;

    private Animation fab_open,fab_close,rotate_forward,rotate_backward;

    private final String TAG = "ListEventsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_event_main_screen);
        fab = (FloatingActionButton) findViewById(R.id.addNewEvent);
        mainEventFabSelector = (FloatingActionButton)findViewById(R.id.fabMainEvent);
        subEventFabSelector  = (FloatingActionButton)findViewById(R.id.fabSubEvent);
        mListView = (ListView) findViewById(R.id.main_event_listview);
        mAdapter = new MainListEvents_CustomAdapter(this, R.layout.list_item_main_screen, getData());
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_backward);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fab.setOnClickListener(this);
        mainEventFabSelector.setOnClickListener(this);
        subEventFabSelector.setOnClickListener(this);
    }


    private ArrayList<MainEventListObjectNew> getData() {
        ArrayList<MainEventListObjectNew> list = new ArrayList<MainEventListObjectNew>();
        for (int i = 1; i < 21; i++) {
            Date today = new Date();
            SimpleDateFormat formattedDate = new SimpleDateFormat("EEE, MMM d, ''yy");
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, i);
            String finalDate = (String) (formattedDate.format(c.getTime()));
            list.add(new MainEventListObjectNew(finalDate));
        }
        return list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addNewEvent:
                animateFAB();
                break;
            case R.id.fabMainEvent:
                Intent intent = new Intent(getApplicationContext(), CreateEvent.class);
                intent.putExtra("EventType","MainEvent");
                startActivity(intent);
                break;
            case  R.id.fabSubEvent:
                Intent i = new Intent(getApplicationContext(), CreateEvent.class);
                i.putExtra("EventType","MainEvent");
                startActivity(i);
                break;
        }
    }


    public void animateFAB() {

        if (isFabOpen) {

            fab.startAnimation(rotate_backward);
            mainEventFabSelector.startAnimation(fab_close);
            subEventFabSelector.startAnimation(fab_close);
            mainEventFabSelector.setClickable(false);
            subEventFabSelector.setClickable(false);
            isFabOpen = false;
            Log.d(TAG, "close");

        } else {

            fab.startAnimation(rotate_forward);
            mainEventFabSelector.startAnimation(fab_open);
            subEventFabSelector.startAnimation(fab_open);
            mainEventFabSelector.setClickable(true);
            subEventFabSelector.setClickable(true);
            isFabOpen = true;
            Log.d(TAG, "open");

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        animateFAB();
    }
}
