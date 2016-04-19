package com.andriod.weddingbells.cardlayoutfunctionality;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;


import com.andriod.weddingbells.Adapters.MainEventListObjectNew;
import com.andriod.weddingbells.Adapters.MainListEvents_CustomAdapter;
import com.andriod.weddingbells.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by inksriniva on 4/5/2016.
 */
public class ListEventsActivityRedone extends AppCompatActivity {

    private ListView mListView;
    private MainListEvents_CustomAdapter mAdapter;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.list_event_main_screen);
        fab = (FloatingActionButton) findViewById(R.id.addNewEvent);
        mListView = (ListView) findViewById(R.id.main_event_listview);
        mAdapter = new MainListEvents_CustomAdapter(this, R.layout.list_item_main_screen, getData());
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Add Event",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),CreateEvent.class);
                startActivity(intent);
            }
        });
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
        Log.d("YOOOOO", "Size: " + list.size());
        return list;
    }
}
