
package com.andriod.weddingbells.cardlayoutfunctionality;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andriod.weddingbells.Adapters.EventDetailsCommentObject;
import com.andriod.weddingbells.Adapters.EventDetailsUserListObject;
import com.andriod.weddingbells.Adapters.RecyclerViewAdapter_CommentsList;
import com.andriod.weddingbells.Adapters.RecyclerViewAdapter_UserList;
import com.andriod.weddingbells.common.BitmapScaler;
import com.andriod.weddingbells.common.CommonUtils;

import com.andriod.weddingbells.R;

public class EventDetailsUserListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter_UserList mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "EventDetailsCommentsFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.event_details_user_list_fragment, container,
                false);
        mRecyclerView = (RecyclerView) rootview.findViewById(R.id.recycler_view_user_list);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this.getActivity().getBaseContext(),
                RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerViewAdapter_UserList(getDataSet());
        return rootview;
    }

    @Override
    public void onResume() {
        super.onResume();

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);

        mAdapter.setOnItemClickListener(new RecyclerViewAdapter_UserList.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(LOG_TAG, " Clicked on Item " + position);
            }

        });
    }

    private ArrayList<EventDetailsUserListObject> getDataSet() {
        ArrayList<EventDetailsUserListObject> results = new ArrayList<EventDetailsUserListObject>();

        for (int index = 0; index < 20; index++) {
            String status = "IN";
            if (index % 2 == 0) {
                status = "OUT";
            }
            EventDetailsUserListObject obj = new EventDetailsUserListObject(CommonUtils.getImage(index),
                    "User " + index, status);
            results.add(index, obj);
        }
        return results;
    }


}
