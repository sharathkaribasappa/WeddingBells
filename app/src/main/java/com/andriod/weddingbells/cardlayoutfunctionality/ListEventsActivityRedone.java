package com.andriod.weddingbells.cardlayoutfunctionality;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;


import com.andriod.weddingbells.Adapters.MainEventListObjectNew;
import com.andriod.weddingbells.Adapters.MainListEvents_CustomAdapter;
import com.andriod.weddingbells.R;
import com.andriod.weddingbells.calendarDialog.CalendarDialogFragment;
import com.andriod.weddingbells.common.ImagePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ListEventsActivityRedone extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private ListView mListView;
    private MainListEvents_CustomAdapter mAdapter;
    private FloatingActionButton fab;
    private FloatingActionButton mainEventFabSelector;
    private FloatingActionButton subEventFabSelector;
    private boolean isFabOpen = false;
    private Toolbar mToolbar;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;
    private DialogFragment mCalenderDialog;
    private ImageView mUserProfilePicView;
    DrawerLayout mDrawer;
    ActionBarDrawerToggle mToggleDrawerListener;
    private static final int PICK_IMAGE_ID = 254;

    private final String TAG = "ListEventsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_event_list);
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
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerNav = navigationView.getHeaderView(0);
        mUserProfilePicView = (ImageView)headerNav.findViewById(R.id.nav_bar_user_pic);
        addHeaderViewForListView();
    }

    private void addHeaderViewForListView() {
        View headerview = ((LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_event_intro, null, false);
        mListView.addHeaderView(headerview);
    }


    @Override
    protected void onResume() {
        super.onResume();
        fab.setOnClickListener(this);
        mainEventFabSelector.setOnClickListener(this);
        subEventFabSelector.setOnClickListener(this);
        mToggleDrawerListener = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(mToggleDrawerListener);
        mToggleDrawerListener.syncState();
        mUserProfilePicView.setOnClickListener(this);
    }

    private ArrayList<MainEventListObjectNew> getData() {
        ArrayList<MainEventListObjectNew> list = new ArrayList<MainEventListObjectNew>();
        for (int i = 0; i < 21; i++) {
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
            case R.id.nav_bar_user_pic:
                Intent chooseImageIntent = ImagePicker.getPickImageIntent(this);
                startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
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
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {
            Log.d(TAG,"Send");

        } else if (id == R.id.nav_bar_user_pic) {
            Log.d(TAG,"nav_bar_user_pic");

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
        mDrawer.removeDrawerListener(mToggleDrawerListener);
        animateFAB();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICK_IMAGE_ID:
                Bitmap bitmap = ImagePicker.getImageFromResult(this, resultCode, data);
                mUserProfilePicView.setImageBitmap(bitmap);
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }


}
