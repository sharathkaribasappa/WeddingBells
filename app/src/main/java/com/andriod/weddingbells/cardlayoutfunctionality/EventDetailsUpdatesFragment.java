
package com.andriod.weddingbells.cardlayoutfunctionality;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.andriod.weddingbells.Adapters.EventDetailsUpdatesObject;
import com.andriod.weddingbells.Adapters.RecyclerViewAdapter_UpdatesList;
import com.andriod.weddingbells.Adapters.RecyclerViewAdapter_UpdatesList.MyClickListener;
import com.andriod.weddingbells.R;
import com.andriod.weddingbells.common.BitmapScaler;
import com.andriod.weddingbells.common.CommonUtils;

public class EventDetailsUpdatesFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter_UpdatesList mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "UpdatesFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.updates_fragment_layout, container, false);
        mRecyclerView = (RecyclerView) rootview.findViewById(R.id.recycler_view_updates);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this.getActivity(), LinearLayout.VERTICAL, false);//true for reverse layout
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerViewAdapter_UpdatesList(getDataSet());
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.scrollToPosition(mAdapter.getItemCount()-1);
        return rootview;
    }

    @Override
    public void onResume() {
        super.onResume();

        ((RecyclerViewAdapter_UpdatesList) mAdapter)
                .setOnItemClickListener(new RecyclerViewAdapter_UpdatesList.MyClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {
                        Log.i(LOG_TAG, " Clicked on Item " + position);
                        // Intent i = new Intent(UpdatesFragment.this.getContext(),
                        // DetailsActivity.class);
                        // startActivity(i);
                    }

                });
    }

    private ArrayList<EventDetailsUpdatesObject> getDataSet() {
        ArrayList<EventDetailsUpdatesObject> results = new ArrayList<EventDetailsUpdatesObject>();
        for (int index = 0; index < 20; index++) {
            String[] comments = {
                    "Wow", "Looking Good", "Hey WTH"
            };
            EventDetailsUpdatesObject obj = new EventDetailsUpdatesObject("User 1", CommonUtils.getImage(index), 5, comments.length,
                    comments);
            results.add(index, obj);
        }
        return results;
    }

}
