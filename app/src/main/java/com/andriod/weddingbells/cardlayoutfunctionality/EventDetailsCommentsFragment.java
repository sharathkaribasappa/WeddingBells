
package com.andriod.weddingbells.cardlayoutfunctionality;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.andriod.weddingbells.Adapters.EventDetailsCommentObject;
import com.andriod.weddingbells.Adapters.RecyclerViewAdapter_CommentsList;
import com.andriod.weddingbells.Adapters.RecyclerViewAdapter_UpdatesList;
import com.andriod.weddingbells.R;
import com.andriod.weddingbells.R.layout;
import com.andriod.weddingbells.common.BitmapScaler;
import com.andriod.weddingbells.common.CommonUtils;

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
import android.widget.LinearLayout;

public class EventDetailsCommentsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter_CommentsList mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "EventDetailsCommentsFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.comments_fragment_layout, container, false);
        mRecyclerView = (RecyclerView) rootview.findViewById(R.id.recycler_view_comments);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this.getActivity().getBaseContext(), RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerViewAdapter_CommentsList(getDataSet());
        return rootview;
    }

    @Override
    public void onResume() {
        super.onResume();

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
        
        mAdapter.setOnItemClickListener(new RecyclerViewAdapter_CommentsList.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i(LOG_TAG, " Clicked on Item " + position);
            }

        });
    }

    private ArrayList<EventDetailsCommentObject> getDataSet() {
        ArrayList<EventDetailsCommentObject> results = new ArrayList<EventDetailsCommentObject>();

        for (int index = 0; index < 20; index++) {
            EventDetailsCommentObject obj = new EventDetailsCommentObject("Comment " + index,
                    CommonUtils.getImage(index));
            results.add(index, obj);
        }
        return results;
    }
}
