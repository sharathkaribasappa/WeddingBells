
package com.andriod.weddingbells.Adapters;

import java.util.ArrayList;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andriod.weddingbells.R;

public class RecyclerViewAdapter_UserList extends
        RecyclerView.Adapter<RecyclerViewAdapter_UserList.MainViewHolder> {

    private ArrayList<EventDetailsUserListObject> mDataSet;
    private Context mContext;

    private static MyClickListener myClickListener;
    private String TAG = "RecyclerViewAdapter_UpdatesList";
    
    public RecyclerViewAdapter_UserList(ArrayList<EventDetailsUserListObject> myDataset) {
        mDataSet = myDataset;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_details_user_list_item, parent, false);
        mContext = parent.getContext();

        MainViewHolder objectHolder = new MainViewHolder(view);
        return objectHolder;
    }

    public class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mImageView;
        TextView mUserName;
        TextView mStatus;

        public MainViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.user_list_profile_pic);
            mUserName = (TextView) itemView.findViewById(R.id.user_list_name);
            mStatus = (TextView) itemView.findViewById(R.id.user_list_status);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d(TAG + "", "onClick of itemView");
            myClickListener.onItemClick(getAdapterPosition(), v);
        }

    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        MainViewHolder mHolder = (MainViewHolder) holder;
        mHolder.mUserName.setText(mDataSet.get(position).getName());
        // mHolder.mImageView.setImageDrawable(Drawable.createFromPath(mDataset.get(position)
        // .getImage().toString()));
        mHolder.mStatus.setText(mDataSet.get(position).getStatus());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
    
    public void addItem(EventDetailsUserListObject dataObj, int index) {
        mDataSet.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataSet.remove(index);
        notifyItemRemoved(index);
    }

    
    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }


    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

}
