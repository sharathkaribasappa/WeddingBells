
package com.andriod.weddingbells.Adapters;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.andriod.weddingbells.R;

public class RecyclerViewAdapter_UpdatesList extends
        RecyclerView.Adapter<RecyclerViewAdapter_UpdatesList.MainViewHolder> {

    private ArrayList<EventDetailsUpdatesObject> mDataSet;
    private Context mContext;
    private ImageView mImageView;
    private static MyClickListener myClickListener;
    private CardView mCardView;
    private String TAG = "RecyclerViewAdapter_UpdatesList";
    private ImageView mUpdatesThumnbnailView;
    private ImageButton mUpdatesThumnbnailButton;

    public RecyclerViewAdapter_UpdatesList(ArrayList<EventDetailsUpdatesObject> DataSet) {
        mDataSet = DataSet;
    }

    public class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public MainViewHolder(View itemView) {
            super(itemView);

            mImageView = (ImageView) itemView.findViewById(R.id.UpdatesImageView);
            mCardView = (CardView) itemView.findViewById(R.id.updatesCardview);
            mUpdatesThumnbnailView = (ImageView) itemView.findViewById(R.id.UpdatesThumnbnailView);
            mUpdatesThumnbnailButton = (ImageButton) itemView.findViewById(R.id.UpdatesThumnbnailButton);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG+"", "onClick of itemView");
            myClickListener.onItemClick(getAdapterPosition(), v);
        }

    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.updates_item, parent, false);
        mContext = parent.getContext();

        MainViewHolder objectHolder = new MainViewHolder(view);
        return objectHolder;
    }

    @Override
    public void onBindViewHolder(MainViewHolder viewHolder, int position) {
        //add logic to extract data from
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void addItem(EventDetailsUpdatesObject dataObj, int index) {
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
