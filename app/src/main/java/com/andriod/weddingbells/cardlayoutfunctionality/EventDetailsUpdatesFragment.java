
package com.andriod.weddingbells.cardlayoutfunctionality;


import java.util.ArrayList;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.andriod.weddingbells.Adapters.EventDetailsUpdatesObject;
import com.andriod.weddingbells.Adapters.RecyclerViewAdapter_UpdatesList;
import com.andriod.weddingbells.R;
import com.andriod.weddingbells.Utils.VolleySingleton;
import com.andriod.weddingbells.common.CommonUtils;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

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
        for (int index = 0; index < 10; index++) {
            String[] comments = {
                    "Wow", "Looking Good", "Hey WTH"
            };
            EventDetailsUpdatesObject obj = new EventDetailsUpdatesObject("User 1", CommonUtils.getImage(index), 5, comments.length,
                    comments);
            results.add(index, obj);
        }
        return results;
    }

    //method to get the updates from cloud in JSON
    public void PostJSONRequest(String url) {
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
            (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //mTxtDisplay.setText("Response: " + response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO Auto-generated method stub
                }
            });

        VolleySingleton mVolleyInstance = VolleySingleton.getInstance(this.getContext());
        mVolleyInstance.addToRequestQueue(jsObjRequest);
    }
}