package com.andriod.weddingbells.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.andriod.weddingbells.R;

import java.util.ArrayList;

/**
 * Created by inksriniva on 4/4/2016.
 */
public class RecyclerViewAdapter_MainEventList extends
        RecyclerView.Adapter<RecyclerViewAdapter_MainEventList.MainViewHolder> {


    private ArrayList<EventListDataObject> mDataSet;
    private Context mContext;
    private static MyClickListener myClickListener;
    private String TAG = "RecyclerViewAdapter_EventsListReImagined";

    public RecyclerViewAdapter_MainEventList(ArrayList<EventListDataObject> list) {
        mDataSet = list;
    }

    public class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Toolbar mToolBar;
        private ImageView mImageView;

        public MainViewHolder(View itemView) {
            super(itemView);

            mImageView = (ImageView) itemView.findViewById(R.id.eventListReDone_imageview);
            mToolBar = (Toolbar) itemView.findViewById(R.id.card_view_toolbar_reimagined);
            mToolBar.inflateMenu(R.menu.main);
            mToolBar.setTitleTextColor(mContext.getResources().getColor(android.R.color.white));
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    @Override
    public RecyclerViewAdapter_MainEventList.MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list_card_view_row_reimagined, parent, false);
        mContext = parent.getContext();

        MainViewHolder objectHolder = new MainViewHolder(view);
        return objectHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter_MainEventList.MainViewHolder holder, int position) {
        holder.mToolBar.setTitle(mDataSet.get(position).getTitle());

    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void addItem(EventListDataObject dataObj, int index) {
        mDataSet.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataSet.remove(index);
        notifyItemRemoved(index);
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}
