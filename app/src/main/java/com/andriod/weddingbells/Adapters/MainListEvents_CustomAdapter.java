package com.andriod.weddingbells.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.andriod.weddingbells.R;
import com.andriod.weddingbells.cardlayoutfunctionality.EventDetailsActivityNew;

import java.util.ArrayList;

/**
 * Created by inksriniva on 4/5/2016.
 */
public class MainListEvents_CustomAdapter extends BaseAdapter{

    private LayoutInflater inflater;
    private ArrayList<MainEventListObjectNew> mDataSet;
    private Context mContext;

    public MainListEvents_CustomAdapter(Context context, int resource, ArrayList<MainEventListObjectNew> data) {
        mDataSet = data;
        mContext = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public class ViewHolder {
        TextView date;
        RecyclerView recycler_view_events;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder = null;
        Log.d("Karthik","Get View "+mDataSet.size());
        if (convertView == null) {
            vi = inflater.inflate(R.layout.list_item_main_screen, null);
            holder = new ViewHolder();
            holder.date = (TextView) vi.findViewById(R.id.eventList_date_textview);
            holder.recycler_view_events = (RecyclerView) vi.findViewById(R.id.recycler_view_trial);
            holder.recycler_view_events.setHasFixedSize(true);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayout.HORIZONTAL, false);
            holder.recycler_view_events.setLayoutManager(mLayoutManager);
            vi.setTag(holder);
        } else {
            holder = (ViewHolder) vi.getTag();
        }
        Log.d("Karthik","Mdataset Size "+mDataSet.size() );
        if(mDataSet.size()<=0){
            Toast.makeText(mContext,"No Data",Toast.LENGTH_SHORT).show();
        }else{
            holder.date.setText(mDataSet.get(position).getDate());
            RecyclerViewAdapter_MainEventList mAdapter = new RecyclerViewAdapter_MainEventList(getDataSet());
            holder.recycler_view_events.setAdapter(mAdapter);
            ((RecyclerViewAdapter_MainEventList) mAdapter).setOnItemClickListener(new RecyclerViewAdapter_MainEventList
                    .MyClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                    Log.i("Karthik", " Clicked on Item " + position);
                    Intent i = new Intent(mContext, EventDetailsActivityNew.class);
                    mContext.startActivity(i);
                }

            });
        }

        return vi;
    }

    private ArrayList<EventListDataObject> getDataSet() {
        ArrayList<EventListDataObject> results = new ArrayList<EventListDataObject>();
        for (int index = 0; index < 20; index++) {
            EventListDataObject obj = new EventListDataObject("Event Title " + index, "Path");
            results.add(index, obj);
        }
        return results;
    }


    @Override
    public int getCount() {
        return mDataSet.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
