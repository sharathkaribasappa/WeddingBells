package com.andriod.weddingbells.cardlayoutfunctionality;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.andriod.weddingbells.Adapters.RecyclerViewAdapter_EventsList;
import com.andriod.weddingbells.R;
import com.andriod.weddingbells.cardlayoutfunctionality.EventListDataObject;

import java.io.File;
import java.util.ArrayList;

public class EventList extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static String LOG_TAG = "EventList";

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter_EventsList mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);//true for reverse layout
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerViewAdapter_EventsList(getDataSet());
        mRecyclerView.setAdapter(mAdapter);

        createFolder();
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

    @Override
    protected void onResume() {
        super.onResume();
        ((RecyclerViewAdapter_EventsList) mAdapter).setOnItemClickListener(new RecyclerViewAdapter_EventsList
                .MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(LOG_TAG, " Clicked on Item " + position);
                Intent i = new Intent(getApplicationContext(), EventDetailsActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.event_list, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private ArrayList<EventListDataObject> getDataSet() {
        ArrayList<EventListDataObject> results = new ArrayList<EventListDataObject>();
        for (int index = 0; index < 20; index++) {
            EventListDataObject obj = new EventListDataObject(getImage(),"Title" + index, "Some Primary Text " + index,
                    "Secondary " + index);
            results.add(index, obj);
        }
        return results;
    }

    private File getImage() {
        File filePath = new File(getDirectory()+"images.png");
        return filePath;
    }

    private String getDirectory() {
        String mImagesFolder = null;
        if (Environment.getExternalStorageState() == null) {
            mImagesFolder = Environment.getDataDirectory().getAbsolutePath()
                    + "/MyAppImagesFolder/";
        } else if (Environment.getExternalStorageState() != null) {
            mImagesFolder = Environment.getExternalStorageDirectory().getAbsolutePath()
                    + "/MyAppImagesFolder/";
        }

        return mImagesFolder;
    }

    private void createFolder() {
        File photoDirectory;
        // if (Environment.getExternalStorageState() == null) {
        // create new file directory object

        photoDirectory = new File(getDirectory());
        /*
         * this checks to see if there are any previous test photo files if there are any photos,
         * they are deleted for the sake of memory
         */
//        if (photoDirectory.exists()) {
//            File[] dirFiles = photoDirectory.listFiles();
//            if (dirFiles.length != 0) {
//                for (int ii = 0; ii <= dirFiles.length; ii++) {
//                    dirFiles[ii].delete();
//                }
//            }
//        }
        // if no directory exists, create new directory
        if (!photoDirectory.exists()) {
            Log.d(LOG_TAG, "Making File in SDCard");
            photoDirectory.mkdir();
        }

        // if phone DOES have sd card
        // } else if (Environment.getExternalStorageState() != null) {
        // // search for directory on SD card
        // photoDirectory = new File(Environment.getExternalStorageDirectory()
        // + "/MyAppImagesFolder/");
        //
        // if (photoDirectory.exists()) {
        // File[] dirFiles = photoDirectory.listFiles();
        // if (dirFiles.length > 0) {
        // for (int ii = 0; ii < dirFiles.length; ii++) {
        // dirFiles[ii].delete();
        // }
        // dirFiles = null;
        // }
        // }
        // // if no directory exists, create new directory to store test
        // // results
        // if (!photoDirectory.exists()) {
        // Log.d(LOG_TAG, "Making File in Storage");
        // System.out.println(""+photoDirectory.mkdir());
        // }
        // }// end of SD card checking
    }
}
