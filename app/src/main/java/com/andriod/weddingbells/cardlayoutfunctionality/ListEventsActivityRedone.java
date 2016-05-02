package com.andriod.weddingbells.cardlayoutfunctionality;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;


import com.andriod.weddingbells.Adapters.MainEventListObjectNew;
import com.andriod.weddingbells.Adapters.MainListEvents_CustomAdapter;
import com.andriod.weddingbells.R;
import com.andriod.weddingbells.calendarDialog.CalendarDialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ListEventsActivityRedone extends AppCompatActivity implements View.OnClickListener {

    private ListView mListView;
    private MainListEvents_CustomAdapter mAdapter;
    private FloatingActionButton fab;
    private FloatingActionButton mainEventFabSelector;
    private FloatingActionButton subEventFabSelector;
    private boolean isFabOpen = false;
    private Toolbar mToolbar;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;
    DialogFragment mCalenderDialog;

    private final String TAG = "ListEventsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_event_main_screen);
        fab = (FloatingActionButton) findViewById(R.id.addNewEvent);
        mainEventFabSelector = (FloatingActionButton) findViewById(R.id.fabMainEvent);
        subEventFabSelector = (FloatingActionButton) findViewById(R.id.fabSubEvent);
        mListView = (ListView) findViewById(R.id.main_event_listview);
        mAdapter = new MainListEvents_CustomAdapter(this, R.layout.list_item_main_screen, getData());
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);
        mListView.setAdapter(mAdapter);
        mToolbar = (Toolbar) findViewById(R.id.MainEventScreenToolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        mCalenderDialog = new CalendarDialogFragment();
        addHeaderView();
    }

    private void addHeaderView() {

        View headerview = ((LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_event_intro, null, false);
        mListView.addHeaderView(headerview);
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
        for (int i = 0; i < 21; i++) {
//            if (i == 0) {
//                String introText = getResources().getString(R.string.mainEventScreenIntro_line1) + "\n" + getResources().getString(R.string.mainEventScreenIntro_line2);
//                list.add(new MainEventListObjectNew(introText));
//            } else {
            Date today = new Date();
            SimpleDateFormat formattedDate = new SimpleDateFormat("EEE, MMM d, ''yy");
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, i);
            String finalDate = (String) (formattedDate.format(c.getTime()));
            list.add(new MainEventListObjectNew(finalDate));
//            }
        }
        return list;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addNewEvent:
                animateFAB();
                break;
            case R.id.fabMainEvent:
                Intent intent = new Intent(getApplicationContext(), CreateEvent.class);
                intent.putExtra("EventType", "MainEvent");
                startActivity(intent);
                break;
            case R.id.fabSubEvent:
                Intent i = new Intent(getApplicationContext(), CreateEvent.class);
                i.putExtra("EventType", "MainEvent");
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_events_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.list_events_menu_dateView:
                mCalenderDialog.show(getSupportFragmentManager(), "CalenderFragment");
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPause() {
        super.onPause();
        animateFAB();
    }
}
