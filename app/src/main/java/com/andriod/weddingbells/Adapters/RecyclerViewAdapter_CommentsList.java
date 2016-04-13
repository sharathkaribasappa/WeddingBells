
package com.andriod.weddingbells.Adapters;

import java.util.ArrayList;

import com.andriod.weddingbells.R;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerViewAdapter_CommentsList extends RecyclerView
        .Adapter<RecyclerViewAdapter_CommentsList
        .MainViewHolder> {

    private Context mCtx;
    private static String TAG = "RecyclerViewAdapter_CommentsList";
    private static MyClickListener myClickListener;
    private ArrayList<EventDetailsCommentObject> mDataset;

    public RecyclerViewAdapter_CommentsList(ArrayList<EventDetailsCommentObject> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view1 = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_details_comments_row_item, parent, false);
        mCtx = parent.getContext();

        MainViewHolder dataObjectHolder = new MainViewHolder(view1);
        return dataObjectHolder;
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder implements View
            .OnClickListener {

        TextView comment;
        ImageView ImageView_profile_pic;
        Toolbar mToolBar;

        public MainViewHolder(View itemview) {
            super(itemview);

            comment = (TextView) itemView.findViewById(R.id.comment_textview);
            ImageView_profile_pic = (ImageView) itemView.findViewById(R.id.comment_profile_pic);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }

    }
    

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {

        MainViewHolder mHolder = (MainViewHolder) holder;
        // mHolder.comment.setText(mDataset.get(position).getComment());
        // mHolder.ImageView_profile_pic.setImageDrawable(Drawable.createFromPath(mDataset.get(position)
        // .getImage().toString()));
    }

    public void addItem(EventDetailsCommentObject dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
    
    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }


    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}
